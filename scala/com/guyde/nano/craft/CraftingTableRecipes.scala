package com.guyde.nano.craft

import net.minecraft.item.crafting._
import net.minecraft.inventory._
import net.minecraft.world.World
import net.minecraft.item.ItemStack
import com.guyde.nano.main.AdvTechMod
import com.guyde.nano.craft.ToolParts._
import com.guyde.nano.item.EnergyHelper
import cpw.mods.fml.common.registry.GameRegistry
import com.guyde.nano.item.AdvTool
import cofh.api.energy.IEnergyContainerItem
import com.guyde.nano.item.IModifierItem
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
class ToolPart(id : Int){
  val ID = id
}

class ToolTrait( damage : Float , mine : Int , speed : Float){
  var Mine = mine
  var Damage = damage
  var Speed = speed
  final def writeToNBT(stack : ItemStack) : ItemStack = {
    val copy = PartableToolHelper.NBT_INIT(stack)
    var comp = copy.getTagCompound
    var data = PartableToolHelper.getData(stack)
    data.setInteger("mine_level",mine)
    data.setFloat("speed", speed)
    data.setFloat("damage" , damage)
    comp.setTag("adv_tool_data", data)
    copy.setTagCompound(comp)
    return copy
  }
}

class StickTrait(damage : Float , speed : Float){
  var Damage = damage
  var Speed = speed
  final def writeToNBT(stack : ItemStack) : ItemStack = {
    val copy = PartableToolHelper.NBT_INIT(stack)
    var comp = copy.getTagCompound
    var data = PartableToolHelper.getData(stack)
    data.setFloat("speed", data.getFloat("speed")+speed)
    data.setFloat("damage" , data.getFloat("damage")+damage)
    comp.setTag("adv_tool_data", data)
    copy.setTagCompound(comp)
    return copy
  }
}



object ToolParts{
  val COIL = new ToolPart(0)
  val MATERIAL = new ToolPart(1)
  val STICK = new ToolPart(2)
  val NOTHING = new ToolPart(3)
}

class ToolPattern(){
  var slots : Array[ToolPart] = Array[ToolPart](NOTHING,NOTHING,NOTHING,NOTHING,NOTHING,NOTHING,STICK,MATERIAL,COIL)
  def isValid() : Boolean = {
    return slots.contains(COIL) && slots.contains(STICK) && slots.contains(MATERIAL)
  }
}



class PartableTool(tp : ToolPattern  , tool_name : String , cap : Int){
  private val pattern : ToolPattern = tp
  private var mats : Array[ItemStack] = Array[ItemStack]()
  private var sticks : Array[ItemStack] = Array[ItemStack]()
  private var mats_n : Array[String] = Array[String]()
  private var sticks_n : Array[String] = Array[String]()
  private var mats_t : Array[ToolTrait] = Array[ToolTrait]()
  private var sticks_t : Array[StickTrait] = Array[StickTrait]()
  private var base_stack : ItemStack = null
  
  def setStack(stack : ItemStack){
    base_stack = stack;
  }
  
  final def getStack(mat_name : String , stick_name : String) : ItemStack = {
    var t_trait = mats_t(mats_n.indexOf(mat_name))
    var s_trait = sticks_t(sticks_n.indexOf(stick_name))
    var copy = PartableToolHelper.NBT_INIT(base_stack)

    copy = PartableToolHelper.addToNBT(mat_name, stick_name, base_stack)
    copy = s_trait.writeToNBT(t_trait.writeToNBT(copy))
    
    var comp = copy.getTagCompound
    var data = PartableToolHelper.getData(copy)
    data.setString("name" , tool_name)
    data.setInteger("capacity",cap)
    comp.setTag("adv_tool_data", data)
    copy.setTagCompound(comp)
    return copy
  } 
  protected def getArray(part : ToolPart) : Array[ItemStack] = {
    part match{
      case(STICK) => return sticks
      case(MATERIAL) => return mats
    }
    return null
  }
  
  def getTrait(str : String) : ToolTrait = {
    return mats_t(mats_n.indexOf(str))
  }
  
    def getStickTrait(str : String) : StickTrait = {
    return sticks_t(sticks_n.indexOf(str))
  }
  
  final def matches(inv : InventoryCrafting) : Boolean = {
    if (inv.getSizeInventory==9){
      var cur = 0
      var mat : ItemStack = null
      var stick : ItemStack = null
      tp.slots.foreach { x =>{
        x match{
          case(NOTHING) =>{
            if (inv.getStackInSlot(cur)!=null){
              return false
            }
          }
          case(COIL) => {
            if (inv.getStackInSlot(cur)==null || inv.getStackInSlot(cur).getItem()!=AdvTechMod.enth_coil){
              return false
            }
          }
          case(STICK) =>{
            if (inv.getStackInSlot(cur)==null){
              return false
            }
            val array = getArray(x)
            var bool = false
            array.foreach { y => if (equalStacks(inv.getStackInSlot(cur), y)) bool = true }
            if (!bool) { return false }
            if (stick==null){
              stick = inv.getStackInSlot(cur)
            } 
            if(!equalStacks(inv.getStackInSlot(cur),stick)) {return false}
          } case(MATERIAL) =>{
            if (inv.getStackInSlot(cur)==null){
              return false
            }
            val array = getArray(x)
            var bool = false
            array.foreach { y => if (equalStacks(inv.getStackInSlot(cur), y)) bool = true }
            if (!bool) { return false }
           if (mat==null){
              mat = inv.getStackInSlot(cur)
            } 
            if(!equalStacks(inv.getStackInSlot(cur),mat)) {return false}
              
          }
        }
        
        
        
        cur = cur+1
      }}
      return true
    }
    return false
  }
  
  def equalStacks(stack1 : ItemStack , stack2 : ItemStack ) : Boolean ={
    var copy1 = stack1.copy()
    var copy2 = stack2.copy()
    copy1.stackSize = 1
    copy2.stackSize = 1
    return ItemStack.areItemStacksEqual(copy1,copy2)
  }  
  
  final def addMaterial(stack : ItemStack ,  name : String , tr : ToolTrait){
    mats = mats ++ Array(stack)
    mats_n = mats_n ++ Array(name)
    mats_t = mats_t ++ Array(tr)
  }
  
  final def addStickMaterial(stack : ItemStack, name:String , tr : StickTrait){
    sticks = sticks ++ Array(stack)
    sticks_n = sticks_n ++ Array(name)
    sticks_t = sticks_t ++ Array(tr)
  }
  
  final def getMaterial(inv : InventoryCrafting) : String = {
    if (inv.getSizeInventory==9){
      var cur = 0
      tp.slots.foreach { x =>{
        var a = 0
        x match{
          
          case(MATERIAL) => mats.foreach { y =>if (equalStacks(inv.getStackInSlot(cur), y)){
              return mats_n(a)
            } 
          a=a+1
          }
          case(_) =>
        }
        
        cur = cur+1
      }}
    }
    return "null"
  }
  
  final def getMaterials() : Array[String] = {
    return mats_n
  }
  
  final def getStickMaterials() : Array[String] = {
    return sticks_n
  }
  
  final def getStickMaterial(inv : InventoryCrafting) : String = {
    if (inv.getSizeInventory==9){
      var cur = 0
      tp.slots.foreach { x =>{
       var a = 0
        x match{
          
          case(STICK) => sticks.foreach { y =>if (equalStacks(inv.getStackInSlot(cur), y)){
              return sticks_n(a)
            } 
          a=a+1
          }
          case(_) =>
        }
        cur= cur+1
      }}
    }
    return "null"
  }
  
}



class ToolRecipe(tool : PartableTool) extends IRecipe{
  

  override def matches(inv : InventoryCrafting, world : World) : Boolean = {
    return tool.matches(inv)
  }

  override def getCraftingResult(inv : InventoryCrafting) : ItemStack = {
    var stack = tool.getStack(tool.getMaterial(inv), tool.getStickMaterial(inv))
    stack.setStackDisplayName(PartableToolHelper.NameOf(stack))
    return stack
  }
  
  override def getRecipeSize() : Int = {
    return 10;
  }
  
  override def getRecipeOutput() : ItemStack = {
    return null
  }

}

class ModifierToolRecipe() extends IRecipe{
  

  override def matches(inv : InventoryCrafting, world : World) : Boolean = {
    var cur = 0
    var hasTool = false
    var hasMod = false
    while(cur<inv.getSizeInventory){
      var stack = inv.getStackInSlot(cur)
      if (stack!=null){
        if(stack.getItem().isInstanceOf[IModifierItem]){
          if (hasMod){
            return false
          }
          hasMod = true
        } else if (stack.getItem.isInstanceOf[AdvTool]){
          if (hasTool){
            return false
          }
          if (PartableToolHelper.getData(stack).getInteger("mods")==PartableToolHelper.getData(stack).getInteger("max_mods")){ return false}
          hasTool=true
        }
      }
      
      cur = cur + 1
    }
    return hasTool && hasMod
  }

  override def getCraftingResult(inv : InventoryCrafting) : ItemStack = {
    var cur = 0
    var tool : ItemStack = null
    var En : ItemStack = null
    while(cur<inv.getSizeInventory){
      var stack = inv.getStackInSlot(cur)
      if (stack!=null){
        if(stack.getItem().isInstanceOf[IModifierItem]){
          En = stack
        } else if (stack.getItem.isInstanceOf[AdvTool]){
          tool = stack
        }
      }
      
      cur = cur + 1
    }
    var copy = tool.copy()
    var comp = copy.getTagCompound()
    var data = PartableToolHelper.getData(copy)

    var modifier = En.getItem().asInstanceOf[IModifierItem]
    if (!data.hasKey("modifiers")){
      data.setTag("modifiers", new NBTTagCompound())
    }
    var mods = data.getCompoundTag("modifiers")
    var mod = new NBTTagCompound()
    var ret = comp
    if(mods.hasKey(modifier.name)){
      mod = mods.getCompoundTag(modifier.name)
      ret = modifier.addToNBT(copy,mod.getInteger("level"))
      mod.setInteger("level", mod.getInteger("level")+1)
      if (mod.getInteger("level")>modifier.maxLevel){
        return null
      }
    } else {
      mod.setInteger("level", 1)
      mod.setString("name" , modifier.name)
      ret = modifier.addToNBT(copy)
    }
    comp = ret
    data = ret.getCompoundTag("adv_tool_data")
    data.setInteger("mods", data.getInteger("mods")+1)
    mods.setTag(modifier.name,mod)
    data.setTag("modifiers",mods)
    comp.setTag("adv_tool_data", data)
    copy.stackTagCompound = comp
    return copy

  }
  
  override def getRecipeSize() : Int = {
    return 10;
  }
  
  override def getRecipeOutput() : ItemStack = {
    return null
  }

}


class UpgradeToolRecipe() extends IRecipe{
  

  override def matches(inv : InventoryCrafting, world : World) : Boolean = {
    var cur = 0
    var hasTool = false
    var hasEn = false
    while(cur<inv.getSizeInventory){
      var stack = inv.getStackInSlot(cur)
      if (stack!=null){
        if(stack.getItem()==GameRegistry.findItem("ThermalExpansion", "capacitor")){
          if (hasEn){
            return false
          }
          hasEn = true
        } else if (stack.getItem.isInstanceOf[AdvTool]){
          if (hasTool){
            return false
          }
          hasTool=true
        }
      }
      
      cur = cur + 1
    }
    return hasTool && hasEn
  }

  override def getCraftingResult(inv : InventoryCrafting) : ItemStack = {
    var cur = 0
    var tool : ItemStack = null
    var En : ItemStack = null
    while(cur<inv.getSizeInventory){
      var stack = inv.getStackInSlot(cur)
      if (stack!=null){
        if(stack.getItem()==GameRegistry.findItem("ThermalExpansion", "capacitor")){
          En = stack
        } else if (stack.getItem.isInstanceOf[AdvTool]){
          tool = stack
        }
      }
      
      cur = cur + 1
    }
    var copy = tool.copy()
    var comp = copy.getTagCompound()
    var data = PartableToolHelper.getData(copy)

    val max_en = En.getItem().asInstanceOf[IEnergyContainerItem].getMaxEnergyStored(En)
    data.setInteger("capacity", max_en + data.getInteger("capacity"))
    comp.setInteger("Energy", comp.getInteger("Energy") + En.getItem().asInstanceOf[IEnergyContainerItem].getEnergyStored(En))
    if(!data.hasKey("upgrades")){data.setInteger("upgrades",0)}
    data.setInteger("upgrades",data.getInteger("upgrades")+1)
    comp.setTag("adv_tool_data",data)
    copy.stackTagCompound = comp
    copy.setItemDamage(copy.getMaxDamage()-copy.getItem.getDamage(copy))
    return copy
  }
  
  override def getRecipeSize() : Int = {
    return 10;
  }
  
  override def getRecipeOutput() : ItemStack = {
    return null
  }

}