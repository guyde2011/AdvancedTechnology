package com.guyde.nano.item

import net.minecraft.item.ItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.block.Block
import net.minecraft.world.World
import scala.collection.JavaConversions._
import net.minecraftforge.common.ForgeHooks

trait ToolData {
  def getName() : String
  def getSpeed(stack : ItemStack) : Float
  protected def mineLevelFor(stack : ItemStack , t_type : ToolType) : Int
  def getEnchantability() : Int
  def getDamage(stack : ItemStack) : Float
  def onBlockDestroyed(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean
  def hitEntity(stack : ItemStack , attacker : EntityLivingBase , attacked : EntityLivingBase) : Boolean
  def isRepairableWith(stack : ItemStack , repairWith : ItemStack) : Boolean
  protected def getToolClasses(stack : ItemStack) : java.util.Set[ToolType]
  implicit private def string2ToolType(str : String) : ToolType = { ToolTypes.types.get(str).foreach { x => return x }; return null }
  final def getMineLevel(stack : ItemStack , t_type : String) : Int = {
    return mineLevelFor(stack , t_type)
  }
  
  final def asToolClasses(stack : ItemStack) : java.util.Set[java.lang.String] = {
    val set_type = getToolClasses(stack)
    val set_end = new java.util.HashSet[java.lang.String]()
    set_type.foreach { x => if (x!=null){set_end.add(x.toString())} }
    return set_end
  }
  
  

}

class BasicDurToolData(name : String , damage : Float , mine : Int , speed : Float , ench : Int , repWith : Array[ItemStack]) extends ToolData{
  
  def getName() : String = {
    return name
  }
  
  val types = new java.util.HashSet[ToolType];
  
  implicit def +(t_type : ToolType) : BasicDurToolData = {
    types.add(t_type)
    return this
  }
  
  def getSpeed(stack : ItemStack) : Float = { return speed }
  protected def mineLevelFor(stack : ItemStack , t_type : ToolType) : Int = { return mine}
  def getEnchantability() : Int = {return ench}
  def getDamage(stack : ItemStack) : Float = {return damage }
  def onBlockDestroyed(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean = {stack.damageItem(1, destroyedBy); return true}
  def hitEntity(stack : ItemStack , attacker : EntityLivingBase , attacked : EntityLivingBase) : Boolean = {stack.damageItem(1, attacker); return true}
  def isRepairableWith(stack : ItemStack , repairWith : ItemStack) : Boolean = {
    val copy1 = stack.copy()
    val copy2 = repairWith.copy()
    copy1.stackSize = 1
    copy2.stackSize = 1
    return ItemStack.areItemStacksEqual(copy1, copy2)
  }
  protected def getToolClasses(stack : ItemStack) : java.util.Set[ToolType] = {return types}
  
}

class BasicToolData(name : String , damage : Float , mine : Int , speed : Float , ench : Int ) extends ToolData{
  
  def getName() : String = {
    return name
  }
  
  val types = new java.util.HashSet[ToolType];
  
  implicit def +(t_type : ToolType) : BasicToolData = {
    types.add(t_type)
    return this
  }
  
  def getSpeed(stack : ItemStack) : Float = { return speed }
  protected def mineLevelFor(stack : ItemStack , t_type : ToolType) : Int = { return mine}
  def getEnchantability() : Int = {return ench}
  def getDamage(stack : ItemStack) : Float = {return damage }
  def onBlockDestroyed(stack : ItemStack , world : World , block : Block , x : Int , y : Int , z : Int , destroyedBy : EntityLivingBase) : Boolean = {return ForgeHooks.isToolEffective(stack, block, world.getBlockMetadata(x, y, z))}
  def hitEntity(stack : ItemStack , attacker : EntityLivingBase , attacked : EntityLivingBase) : Boolean = {return true}
  def isRepairableWith(stack : ItemStack , repairWith : ItemStack) : Boolean = {
    return false
  }
  protected def getToolClasses(stack : ItemStack) : java.util.Set[ToolType] = {return types}
  
}

object ToolTypes{
  var types : Map[String,ToolType] = Map()
  val Axe = new ToolType("axe")
  val Pickaxe = new ToolType("pickaxe")
  val Shovel = new ToolType("shovel")
}

class ToolType(name : String) {

  final var Name : String = name
  ToolTypes.types = ToolTypes.types + (Name -> this)
  override def toString() : String = {
    return Name
  }
  
}