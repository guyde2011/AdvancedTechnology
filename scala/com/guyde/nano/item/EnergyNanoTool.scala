package com.guyde.nano.item

import net.minecraft.util.EnumChatFormatting
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item._
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.EntityLivingBase
import net.minecraft.world.World
import net.minecraft.block.Block
import scala.collection.JavaConversions._
import cofh.api.energy.IEnergyContainerItem

class EnergyToolData(energyUse : Int , energyAttack : Int , data : ToolData , energy : IEnergyContainerItem) extends ToolData{
  def getName() : String = {
    return data.getName()
  }
  
  def attackEnergy(stack : ItemStack) : Boolean = {
    return energy.getEnergyStored(stack)>=energyAttack;
  }
  
  def mineEnergy(stack : ItemStack) : Boolean = {
    return energy.getEnergyStored(stack)>=energyUse;
  }
  
  def getSpeed(stack : ItemStack) : Float = { return data.getSpeed(stack) }
  protected def mineLevelFor(stack : ItemStack , t_type : ToolType) : Int = { 
    if(mineEnergy(stack)) {return data.getMineLevel(stack, t_type.Name)}
    return 0
  }
  def getEnchantability() : Int = {return data.getEnchantability()}
  def getDamage(stack : ItemStack) : Float = {
    if (attackEnergy(stack)){ return data.getDamage(stack)}
    return 0;
  }
  def onBlockDestroyed(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean = {
    if (mineEnergy(stack)){
      energy.extractEnergy(stack, energyUse, false)
      return data.onBlockDestroyed(stack , world ,block ,x ,y, z, destroyedBy)
    }
    return false
  }
  def hitEntity(stack : ItemStack , attacker : EntityLivingBase , attacked : EntityLivingBase) : Boolean = {    
    if (attackEnergy(stack)){
      energy.extractEnergy(stack, energyAttack, false)
      return data.hitEntity(stack , attacker , attacked)
    }
    return false
  }
  def isRepairableWith(stack : ItemStack , repairWith : ItemStack) : Boolean = {
    return data.isRepairableWith(stack, repairWith)
  }
  implicit private def string2ToolType(str : String) : ToolType = { ToolTypes.types.get(str).foreach { x => println(x + " Yaay");return x }; return null }
  protected def getToolClasses(stack : ItemStack) : java.util.Set[ToolType] = {
    val set = new java.util.HashSet[ToolType]()
    if (mineEnergy(stack)){
      data.asToolClasses(stack).foreach(x => set.add(x))
    }
    println(set)
    return set;
    
  }
}

object genericEnItem extends IEnergyContainerItem{
  override def receiveEnergy(container : ItemStack, maxReceive : Int , simulate : Boolean) : Int = {
    if (container.stackTagCompound == null) {
      container.stackTagCompound = new NBTTagCompound()
    }
    var energy = container.stackTagCompound.getInteger("Energy")
    var energyReceived = maxReceive

    if (!simulate) {
      energy += energyReceived
      container.stackTagCompound.setInteger("Energy", energy)
    }
    return energyReceived
  }
  override def extractEnergy(container : ItemStack, maxExtract : Int , simulate : Boolean) : Int = {

    if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
      container.stackTagCompound = new NBTTagCompound();
      container.stackTagCompound.setInteger("Energy",0)
    }
    var energy = container.stackTagCompound.getInteger("Energy")
    var energyExtracted = Math.min(energy, maxExtract)

    if (!simulate) {
      energy -= energyExtracted
      container.stackTagCompound.setInteger("Energy", energy)
    }
    return energyExtracted
  }

  override def getEnergyStored(container : ItemStack) : Int = {
    if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
      container.stackTagCompound = new NBTTagCompound()
      container.stackTagCompound.setInteger("Energy",0)
    }
    return container.stackTagCompound.getInteger("Energy")
  }

  override def getMaxEnergyStored(container : ItemStack) : Int = {
    return 100000
  }
}

class EnergyNanoTool(name : String , tex_name : String = null , capacity : Int , maxTransfer : Int = -1 , energyUse : Int , energyUseAttack : Int  , td : ToolData) extends NanoItemTool(new EnergyToolData(energyUse,energyUseAttack,td , genericEnItem) , name , tex_name) with INanoEnergyItem{
  
  var Capacity = capacity
  var MaxReceive = capacity
  var MaxExtract = capacity
  if (!maxTransfer.equals(-1)){
    MaxReceive = maxTransfer
    MaxExtract = maxTransfer
   } else {
    Capacity = capacity
    MaxReceive = capacity
    MaxExtract = capacity
  }

  
  override def getSubItems(item : Item, tab : CreativeTabs , list : java.util.List[_]) {
    val stack = new ItemStack(item); 
    val stack2 = stack.copy();
    stack.stackTagCompound = new NBTTagCompound();
    stack.stackTagCompound.setInteger("Energy",capacity)
    ListConverter.addTo(list, stack2)
    ListConverter.addTo(list, stack)
  }
  

  override def showDurabilityBar(stack : ItemStack) : Boolean = {
    if (!stack.hasTagCompound()) { val a : NBTTagCompound = new NBTTagCompound(); a.setInteger("Energy" ,0); stack.setTagCompound(a)}
    return true
  }

  override def reg(){
    ItemRegistry.regItem(this)
    ItemRegistry.regEnergyItem(this)
    
  }
    override def getMaxDamage (stack : ItemStack) : Int = {
      return 1000
    }
    
    override def getDisplayDamage(stack : ItemStack) : Int = {
      return getDamage(stack)
    }

    override def getDamage(stack : ItemStack) : Int = {
      if (stack.getTagCompound() == null){
        return 1000
      }
      return 1000-Math.floor((Int.int2double(getEnergyStored(stack)) * Int.int2double(getMaxDamage(stack))) / Int.int2double(capacity)).intValue()
    }
    
    @SideOnly(Side.CLIENT)
    override def addInformation (stack : ItemStack, player : EntityPlayer , list : java.util.List[_] , boolean : Boolean) 
    {   
      val green : String = EnumChatFormatting.GREEN.toString()
      val red : String = EnumChatFormatting.RED.toString()
      var color : String = red
      
      if (getEnergyStored(stack)*2>=getMaxEnergyStored(stack)){
        color = green
      }
      val energy : String = getEnergyStored(stack).toString()
      val maxEnergy : String = getMaxEnergyStored(stack).toString()
      ListConverter.addTo(list,color + "Energy: " + energy + "RF / " + maxEnergy + "RF");


    }
}