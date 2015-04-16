package com.guyde.nano.item

import scala.collection.JavaConversions._
import net.minecraft.item.ItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.world.World
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import com.guyde.nano.craft.PartableToolHelper
import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.util.EnumChatFormatting._
import net.minecraft.util.EnumChatFormatting
import net.minecraft.item.Item
import cpw.mods.fml.relauncher.Side
import com.google.common.collect.Multimap
import com.guyde.nano.item.ToolData
import com.guyde.nano.item.ListConverter._
import net.minecraftforge.common.ForgeHooks
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.creativetab.CreativeTabs
import scala.Int._
import net.minecraft.client.renderer.texture.IIconRegister
import com.guyde.nano.craft.PartableTool
import net.minecraft.util.IIcon
import net.minecraft.init.Items
import net.minecraft.client.settings.KeyBinding
import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard
import com.guyde.nano.main.AdvTechTab
import cofh.api.item.IEmpowerableItem
import net.minecraft.entity.player.EntityPlayerMP

class AdvToolData(EnergyUsage : Int , AttackEnergyUsage: Int , ench : Int) extends ToolData{
  def getName() : String = {
    return ""
  }
  

  
  val attack_use = AttackEnergyUsage
  val use = EnergyUsage
  def attackEnergy(stack : ItemStack) : Boolean = {
    return EnergyHelper.getEnergy(stack)>=AttackEnergyUsage
  }
  
  def mineEnergy(stack : ItemStack) : Boolean = {
    return EnergyHelper.getEnergy(stack)>=EnergyUsage
  }
  
  def getSpeed(stack : ItemStack) : Float = { return PartableToolHelper.getData(stack).getFloat("speed") }
  protected def mineLevelFor(stack : ItemStack , t_type : ToolType) : Int = { 
    if(mineEnergy(stack)) {return PartableToolHelper.getData(stack).getInteger("mine_level")}
    return 0
  }
  def getEnchantability() : Int = {return ench}
  def getDamage(stack : ItemStack) : Float = {
    if (attackEnergy(stack)){ return PartableToolHelper.getData(stack).getFloat("damage") }
    return 0
  }
  
  def onBlockDestroyed(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean = {
    if (mineEnergy(stack)){
      var multi = 1d
      if (stack.getItem.asInstanceOf[AdvTool].isEmpowered(stack)){
        multi = 1.25d
      }
      if (!world.isRemote && stack.getItem.asInstanceOf[AdvTool].isEmpowered(stack) && (!PartableToolHelper.getData(stack).hasKey("finish") || PartableToolHelper.getData(stack).getBoolean("finish"))){
      var des : EntityPlayerMP = destroyedBy.asInstanceOf[EntityPlayerMP]
      var xDiff : Double = (des.posX - x).abs
      var yDiff : Double = (des.posY+1 - y).abs
      var zDiff : Double = (des.posZ - z).abs
      var data = stack.stackTagCompound.getCompoundTag("adv_tool_data")
      data.setBoolean("finish",false)
      stack.stackTagCompound.setTag("adv_tool_data",data)
      if (xDiff>Math.max(yDiff,zDiff)){
         for( a <- 1 to 3){
           for( b <- 1 to 3){
             if (mineEnergy(stack) && ForgeHooks.isToolEffective(stack, world.getBlock(x, y-2+a, z-2+b), world.getBlockMetadata(x, y, z))){
             des.theItemInWorldManager.tryHarvestBlock(x, y-2+a, z-2+b)
             }
           }
         }
      }
      if (yDiff>Math.max(xDiff,zDiff)){
         for( a <- 1 to 3){
           for( b <- 1 to 3){
             if (mineEnergy(stack) && ForgeHooks.isToolEffective(stack, world.getBlock(x-2+a, y, z-2+b), world.getBlockMetadata(x, y, z))){
             des.theItemInWorldManager.tryHarvestBlock(x-2+a, y, z-2+b)
             }
           }
         }
      }
      if (zDiff>Math.max(yDiff,xDiff)){
        for( a <- 1 to 3){
          for( b <- 1 to 3){
            if (mineEnergy(stack) && ForgeHooks.isToolEffective(stack, world.getBlock(x-2+b, y-2+a, z), world.getBlockMetadata(x, y, z))){
              des.theItemInWorldManager.tryHarvestBlock(x-2+b, y-2+a, z)
               
            }
           }
          }
      }
      data = stack.stackTagCompound.getCompoundTag("adv_tool_data")
      data.setBoolean("finish",true)
      stack.stackTagCompound.setTag("adv_tool_data",data)
    } else if (!world.isRemote){
                  EnergyHelper.reduceEnergy(stack, (EnergyUsage).toInt)
    }

      return true
    }
    return false
  }
  def hitEntity(stack : ItemStack , attacker : EntityLivingBase , attacked : EntityLivingBase) : Boolean = {    
    if (attackEnergy(stack)){
      var multi = 1d
      if (stack.getItem.asInstanceOf[AdvTool].isEmpowered(stack)){
        multi = 1.25d
      }
      EnergyHelper.reduceEnergy(stack, (multi * AttackEnergyUsage).toInt)
      return true
    }
    return false
  }
  def isRepairableWith(stack : ItemStack , repairWith : ItemStack) : Boolean = {
    return false
  }
  private var types = Array[ToolType]()
  def +(tt : ToolType) : AdvToolData = {
    types = types ++ Array[ToolType](tt)
    return this
  }
  implicit private def string2ToolType(str : String) : ToolType = { ToolTypes.types.get(str).foreach { x => return x }; return null }
  protected def getToolClasses(stack : ItemStack) : java.util.Set[ToolType] = {
    val set = new java.util.HashSet[ToolType]()
    if (mineEnergy(stack)){
      types.foreach { x => set.add(x) }
    }
    return set;
    
  }
}


object EnergyHelper{
  def addEnergy(container : ItemStack, receive : Int) {
    if (container.stackTagCompound == null) {
      container.stackTagCompound = new NBTTagCompound()
    }
    container.stackTagCompound.setInteger("Energy",getEnergy(container)+receive)
  }

  def reduceEnergy(container : ItemStack, extract : Int) {

    if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
      container.stackTagCompound = new NBTTagCompound();
      container.stackTagCompound.setInteger("Energy",0)
    }
    container.stackTagCompound.setInteger("Energy",getEnergy(container)-extract)
  }

  def getEnergy(container : ItemStack) : Int = {
    if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
      if (container.stackTagCompound==null){
      container.stackTagCompound = new NBTTagCompound()
      }
      container.stackTagCompound.setInteger("Energy",0)
    }
    return container.stackTagCompound.getInteger("Energy")
  }

  def getCapacity(stack : ItemStack) : Int = {
    return com.guyde.nano.craft.PartableToolHelper.getData(stack).getInteger("capacity")
  }
  

} 

class AdvTool(data : AdvToolData ,tool : PartableTool , name : String , tex_name : String ) extends ItemHelp() with INanoItem with IEmpowerableItem {
    val toolData = data
    val partable = tool
    setUnlocalizedName(name)
    this.setCreativeTab(AdvTechTab)
    
  override def setEmpoweredState(stack : ItemStack, state : Boolean) : Boolean = {
      if (!state){
        var data = stack.stackTagCompound.getCompoundTag("adv_tool_data")
        data.setBoolean("empowered", false)
        stack.stackTagCompound.setTag("adv_tool_data",data)
        return true
      }
      
        var data1 = stack.stackTagCompound.getCompoundTag("adv_tool_data")
        data1.setBoolean("empowered", true)
      stack.stackTagCompound.setTag("adv_tool_data",data1)
      return true

    }
    
    override def onStateChange(player : EntityPlayer, stack : ItemStack){
      
    }
  
  override def isEmpowered(stack : ItemStack) : Boolean = {
    return PartableToolHelper.getData(stack).getBoolean("empowered")
  }
    
    override def func_150893_a(p_150893_1_ : ItemStack , p_150893_2_ : Block ) : Float = 
    {
        return 1.0F
    }
    var UnlocalName = name
    var TextureName = tex_name
    
    
    
    
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    override def hitEntity(a : ItemStack , b : EntityLivingBase , c : EntityLivingBase ) : Boolean = 
    {
        return toolData.hitEntity(a,b,c)
    }

    override def onBlockDestroyed(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean =
    {
        if (block.getBlockHardness(world,x,y,z) != 0)
        {
            if (toolData.onBlockDestroyed(stack,world,block,x,y,z,destroyedBy)){

              return true

            }
        }

        return true
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    override def isFull3D() : Boolean =
    {
        return true
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    override def getItemEnchantability() : Int = {
        return toolData.getEnchantability()
    }

    /**
     * Return the name for this tool's material.
     */
    def getToolMaterialName() : String =
    {
        return toolData.getName();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    override def getIsRepairable(a : ItemStack , b : ItemStack ) : Boolean =
    {
      return toolData.isRepairableWith(a,b);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */

    @SideOnly(Side.CLIENT)
  override def requiresMultipleRenderPasses() : Boolean = 
  {
    return true
  }
    override def getAttributeModifiers(stack : ItemStack) : Multimap[_,_] = 
    {
        val multimap = super.getAttributeModifiers(stack)
        com.guyde.nano.item.ListConverter.put(multimap,SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(getUUID(), "Tool modifier", toolData.getDamage(stack), 0));
        return multimap;
    }


    override def getHarvestLevel(stack : ItemStack , toolClass : String ) : Int = {
      if (getToolClasses(stack).contains(toolClass)){
            return toolData.getMineLevel(stack , toolClass);
      }
      return 0;

    }
    override def getToolClasses(stack : ItemStack ) : java.util.Set[String] =
    {
        return toolData.asToolClasses(stack);
    }

    override def getDigSpeed(stack : ItemStack, block : Block, meta : Int) : Float = 
    {
        if (ForgeHooks.isToolEffective(stack, block, meta))
        {
            return toolData.getSpeed(stack);
        }
        return 0;
    }
    
  override def showDurabilityBar(stack : ItemStack) : Boolean = {
    if (!stack.hasTagCompound()) { val a : NBTTagCompound = new NBTTagCompound(); a.setInteger("Energy" ,0); stack.setTagCompound(a)}
    return true
  }
  

  def reg(){
    ItemRegistry.regItem(this)
    
  }
    override def getMaxDamage (stack : ItemStack) : Int = {
      return 1000
    }
    
    override def getDisplayDamage(stack : ItemStack) : Int = {
      return getDamage(stack)
    }

    override def getDamage(stack : ItemStack) : Int = {
      
      if (stack.getTagCompound() == null){
        stack.setTagCompound(PartableToolHelper.NBT_INIT(stack).getTagCompound)
      }
      if (!stack.getTagCompound().hasKey("Energy")){var a = stack.getTagCompound(); a.setInteger("Energy" , 0); stack.setTagCompound(a); return 1000}
      var capacity : Double = EnergyHelper.getCapacity(stack)
      
      if (capacity.equals(0)) {capacity=1}
            var ratio : Double = EnergyHelper.getEnergy(stack).toDouble / capacity
      return (1000d-Math.floor(1000d*ratio)).toInt


    }
    
    @SideOnly(Side.CLIENT)
    override def addInformation (stack : ItemStack, player : EntityPlayer , list : java.util.List[_] , boolean : Boolean) 
    {   
      val green : String = EnumChatFormatting.GREEN.toString()
      val red : String = EnumChatFormatting.RED.toString()
      var color : String = red
      
      if (EnergyHelper.getEnergy(stack)*2>=EnergyHelper.getCapacity(stack)){
        color = green
      }
      val energy : String = EnergyHelper.getEnergy(stack).toString()
      val maxEnergy : String = EnergyHelper.getCapacity(stack).toString()
      var upg = 0
      if (PartableToolHelper.getData(stack).hasKey("upgrades")){
        upg = PartableToolHelper.getData(stack).getInteger("upgrades")
      }
      
       
      var color2 : String = green
      if (PartableToolHelper.getData(stack).getInteger("mods")==PartableToolHelper.getData(stack).getInteger("max_mods")){
        color2 = red
      }
      var multi = 1D
      var emp = "empower"
      if (this.isEmpowered(stack)){
        multi = 1.25d
        emp = "unempower"
      }
      
      ListConverter.addTo(list,color + "Energy: " + energy + "RF / " + maxEnergy + "RF");
      ListConverter.addTo(list,GRAY + "Press " + YELLOW + "V" + GRAY + " to " + emp) 
      if(Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode)){
        ListConverter.addTo(list,GRAY + "  Energy Upgrades: " + GREEN + upg + GRAY + "  Applied Modifiers: " + color2 + PartableToolHelper.getData(stack).getInteger("mods") + "/" + PartableToolHelper.getData(stack).getInteger("max_mods"))
        ListConverter.addTo(list,GRAY + "  Basic Capacity: " + YELLOW + 50000)
        ListConverter.addTo(list,GRAY + "  Mining Speed: " + YELLOW + PartableToolHelper.getData(stack).getFloat("speed") + "  " + GRAY + "  Attack Damage: " + YELLOW + PartableToolHelper.getData(stack).getFloat("damage"))
        ListConverter.addTo(list,AQUA + "    Tool:  " + GREEN + tool.getTrait(PartableToolHelper.getData(stack).getString("material")).Speed + "              " + AQUA + " Tool:  " + GREEN +  tool.getTrait(PartableToolHelper.getData(stack).getString("material")).Damage)
        ListConverter.addTo(list,AQUA + "    Handle:  " + GREEN + tool.getStickTrait(PartableToolHelper.getData(stack).getString("stick")).Speed + "             " + AQUA + "Handle:  " + GREEN + tool.getStickTrait(PartableToolHelper.getData(stack).getString("stick")).Damage)
        ListConverter.addTo(list,GRAY + "  Mining Level: " + YELLOW +  PartableToolHelper.getData(stack).getFloat("mine_level"))
        ListConverter.addTo(list,GRAY + "  Handle Material: " + YELLOW + PartableToolHelper.getData(stack).getString("stick"))
        ListConverter.addTo(list,GRAY + "  Tool Material: " + YELLOW + PartableToolHelper.getData(stack).getString("material"))
        ListConverter.addTo(list,GRAY + "  Energy Usage:")
        ListConverter.addTo(list,AQUA + "    Attack:  " + RED + multi * toolData.attack_use + "RF" + AQUA + "    Mining:  " + EnumChatFormatting.RED + multi * toolData.use + "RF")
        ListConverter.addTo(list," ")
        if (PartableToolHelper.getData(stack).hasKey("modifiers")){
          var a = PartableToolHelper.getData(stack).getCompoundTag("modifiers").func_150296_c().toArray()
          ListConverter.addTo(list,GOLD + "  Modifiers:")
          a.foreach { x =>  var k = PartableToolHelper.getData(stack).getCompoundTag("modifiers").getCompoundTag(x.asInstanceOf[String]);  ListConverter.addTo(list,BLUE + "     " + k.getString("name") + ": " + YELLOW + k.getInteger("level")); 
          }
        }
      } else {
        ListConverter.addTo(list,"Hold " + EnumChatFormatting.YELLOW+ Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode) + EnumChatFormatting.GRAY + " for more details");
      }

    }
    @SideOnly(Side.CLIENT)
  override def getIcon(stack : ItemStack, pass : Int) : IIcon = {
    if (pass.equals(0)){
      sticks_map.get(com.guyde.nano.craft.PartableToolHelper.getData(stack).getString("stick")).foreach(x => return x)
    } else if (pass.equals(1)) {
      mats_map.get(com.guyde.nano.craft.PartableToolHelper.getData(stack).getString("material")).foreach(x => return x)
    } else if (pass.equals(2)){
    return base_icon
    } 
    return nothing

  }
  
  override def getItemRef() : Item = {
    partable.setStack(new ItemStack(this))
    return this.abc();
  }
  @SideOnly(Side.CLIENT)
  override def getRenderPasses(a : Int) : Int = {
    return 3;
  }
  
  override def getSubItems(item : Item, tab : CreativeTabs , list : java.util.List[_]) {
 //   ListConverter.addTo(list, new ItemStack(Items.iron_ingot))

  }
   @SideOnly(Side.CLIENT)
   var base_icon : IIcon = null
   var nothing : IIcon = null
   var mats = partable.getMaterials()
   var mats_map = Map[String , IIcon]()
   var sticks = partable.getStickMaterials()
   var sticks_map = Map[String , IIcon]()
   @SideOnly(Side.CLIENT)
   override def registerIcons(ir : IIconRegister) {
     mats.foreach { x => mats_map = mats_map + (x -> ir.registerIcon("advtech:" + TextureName + "/materials/" + x)) }
     sticks.foreach { x => sticks_map = sticks_map + (x -> ir.registerIcon("advtech:" + TextureName + "/sticks/" + x))}
     base_icon = ir.registerIcon("advtech:" + TextureName + "/basic")
     nothing = ir.registerIcon("advtech:/nothing")
  }
}