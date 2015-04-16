package com.guyde.nano.item

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.ItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.world.World
import net.minecraft.block.Block

trait IModifierItem {
  def addToNBT(stack : ItemStack) : NBTTagCompound
  
  def name : String
  
  def maxLevel : Int
  
  def addToNBT(stack : ItemStack,level : Int) : NBTTagCompound
  
  def onBlockBroken(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean ={
    return true
  }
  
  def register(){
    ModifierRegistry.register(this)
  }
  
  def normalDrops() : Boolean = {
    return true
  }
  
  def customDrops(curDrops : Array[ItemStack] , baseDrops : Array[ItemStack] , tool : ItemStack) : Array[ItemStack] = {
    return curDrops
  }
  
}

object ModifierRegistry{
  private var mods = Map[String,IModifierItem]()
  
  def register(item : IModifierItem){
    mods = mods + (item.name -> item)
  }
  
  def getModifier(str : String) : IModifierItem = {
    mods.get(str).foreach(x=>return x)
    return null
  }

}