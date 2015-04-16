package com.guyde.nano.item

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.ItemStack
import com.guyde.nano.craft.PartableToolHelper
import net.minecraft.enchantment.Enchantment

object Modifiers {
  object speedUpgrade extends NanoBasicItem("rs_emerald" , "stablized_gem") with IModifierItem{
      def addToNBT(stack : ItemStack) : NBTTagCompound = {
        var copy = stack.copy()
        var data = PartableToolHelper.getData(copy)
        data.setFloat("speed", data.getFloat("speed")+3.0f)
        copy.stackTagCompound.setTag("adv_tool_data", data)
        return copy.stackTagCompound
      }
  
    val name : String = "Speed"
  
    val maxLevel : Int = 10
  
    def addToNBT(stack : ItemStack,level : Int) : NBTTagCompound = {return addToNBT(stack)}
  }

  object damageUpgrade extends NanoBasicItem("energized_quartz" , "dmg_up") with IModifierItem{
      def addToNBT(stack : ItemStack) : NBTTagCompound = {
        var copy = stack.copy()
        var data = PartableToolHelper.getData(copy)
        data.setFloat("damage", data.getFloat("damage")+3.0f)
        copy.stackTagCompound.setTag("adv_tool_data", data)
        return copy.stackTagCompound
      }
  
    val name : String = "Damage"
  
    val maxLevel : Int = 10
  
    def addToNBT(stack : ItemStack,level : Int) : NBTTagCompound = {return addToNBT(stack)}
  }
  
  object SilkTouchUpgrade extends NanoBasicItem("enriched_enderium") with IModifierItem{
    val name : String = "Silk Touch"
  
    val maxLevel : Int = 1
        def addToNBT(stack : ItemStack) : NBTTagCompound = {
          stack.addEnchantment(Enchantment.silkTouch, 1)
          return stack.stackTagCompound
        }
    def addToNBT(stack : ItemStack,level : Int) : NBTTagCompound = {return addToNBT(stack)}
  }

}