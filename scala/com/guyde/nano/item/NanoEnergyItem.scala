package com.guyde.nano.item

import cofh.api.energy.IEnergyContainerItem
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item._
import net.minecraft.nbt._
import cpw.mods.fml.relauncher.Side
import net.minecraft.util.EnumChatFormatting
import scala.collection.JavaConversions._
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.client.renderer.texture.IIconRegister

trait INanoEnergyItem extends INanoItem with IEnergyContainerItem{
  
  def MaxReceive : Int 
  def MaxExtract : Int  
  def Capacity : Int 
  
  


  override def receiveEnergy(container : ItemStack, maxReceive : Int , simulate : Boolean) : Int = {
    if (container.stackTagCompound == null) {
      container.stackTagCompound = new NBTTagCompound()
    }
    var energy = container.stackTagCompound.getInteger("Energy")
    var energyReceived = Math.min(Capacity - energy, Math.min(this.MaxReceive, maxReceive))

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
    var energyExtracted = Math.min(energy, Math.min(MaxExtract, maxExtract))

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
    return Capacity
  }
  
}

class NanoBasicEnergyItem(name : String = "error_name" , tex_name : String = null , capacity : Int , maxTransfer : Int = -1 , maxExtract : Int = -1) extends Item with INanoEnergyItem{
  var TextureName : String = name 
  var UnlocalName : String = name
  var Capacity = capacity
  var MaxReceive = capacity
  var MaxExtract = capacity
  if (tex_name!=null){
    TextureName = tex_name
  }
  def reg(){
    ItemRegistry.regItem(this)
    ItemRegistry.regEnergyItem(this)
    
  }
  override def registerIcons(ir : IIconRegister) {
    // TODO Auto-generated method stub
    itemIcon = ir.registerIcon("advtech:" + TextureName);
  }

  if (!maxTransfer.equals(-1)){
    MaxReceive = maxTransfer
    if (maxExtract.equals(-1)){
      MaxExtract = maxTransfer
    } else {
      MaxExtract = maxExtract
    }
  } else {
    Capacity = capacity
    MaxReceive = capacity
    MaxExtract = capacity
  }
  
  override def getItemRef() : Item = {
    return this
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
/**
class NanoBasicEnergyItem(name : String = "error_name" , tex_name : String = null , capacity : Int , maxTransfer : Int = -1 , maxExtract : Int = -1) extends ToolItem with INanoEnergyItem{
  var TextureName : String = name 
  var UnlocalName : String = name
  var Capacity = capacity
  var MaxReceive = capacity
  var MaxExtract = capacity
  if (tex_name!=null){
    TextureName = tex_name
  }

  if (!maxTransfer.equals(-1)){
    MaxReceive = maxTransfer
    if (maxExtract.equals(-1)){
      MaxExtract = maxTransfer
    } else {
      MaxExtract = maxExtract
    }
  } else {
    Capacity = capacity
    MaxReceive = capacity
    MaxExtract = capacity
  }
  
  override def getItemRef() : Item = {
    return this
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
**/

