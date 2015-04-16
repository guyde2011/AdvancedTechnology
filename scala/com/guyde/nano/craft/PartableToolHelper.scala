package com.guyde.nano.craft

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

object PartableToolHelper {
   def NBT_INIT(stack : ItemStack) : ItemStack = {
    var copy = stack.copy()
    var comp : NBTTagCompound = new NBTTagCompound()
    if (copy.hasTagCompound()){
      comp = copy.getTagCompound
    }
    var data = new NBTTagCompound()
    if (comp.hasKey("adv_tool_data")){
      data = copy.getTagCompound.getCompoundTag("adv_tool_data")
      
    }
    data.setInteger("max_mods", 5)
    data.setInteger("mods", 0)
    comp.setTag("adv_tool_data",data)
    copy.setTagCompound(comp)
    return copy
    
  }
  
  def getData(stack : ItemStack) : NBTTagCompound = {
    return stack.getTagCompound.getCompoundTag("adv_tool_data")
  }
  
  
  def addToNBT(mat_name : String , stick_name : String , stack : ItemStack) : ItemStack = {
    var copy = NBT_INIT(stack)
    var comp = copy.getTagCompound
    var data = comp.getCompoundTag("adv_tool_data")
    data.setString("material", mat_name)
    data.setString("stick", stick_name)
    comp.setTag("adv_tool_data",data)
    copy.setTagCompound(comp)
    return copy
  }
  
  def NameOf(stack : ItemStack) : String = {
    var copy = stack.copy()
    var comp = copy.getTagCompound
    var data = comp.getCompoundTag("adv_tool_data")
    return EnumChatFormatting.RESET + "" + EnumChatFormatting.LIGHT_PURPLE +data.getString("stick") + " Handled " + data.getString("material") + " " + data.getString("name") 
  } 
}