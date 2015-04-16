package com.guyde.nano.item

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.guyde.nano.block.BlockLink
import com.guyde.nano.block.TileEntityLink
import com.guyde.nano.block.BindedBlock
import net.minecraft.util.ChatComponentText
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

class Binder extends NanoBasicItem("link_binder" ,"binder"){
  this.setMaxStackSize(1)
  
  override def onItemUse(stack : ItemStack, player : EntityPlayer, world : World, x : Int, y : Int, z : Int, side : Int, hitX : Float, hitY : Float, hitZ : Float) : Boolean = {
    if (stack.stackTagCompound==null){
      stack.stackTagCompound = new NBTTagCompound()
    }
    if (player.isSneaking()){
      if (!world.isRemote && world.getBlock(x, y, z).isInstanceOf[BlockLink]){
        stack.stackTagCompound.setInteger("bindedX",x)
        stack.stackTagCompound.setInteger("bindedY",y)
        stack.stackTagCompound.setInteger("bindedZ",z)
        stack.stackTagCompound.setInteger("bindedWorld",world.provider.dimensionId)

          player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "<Started Binding>"))
        
        
      } else if (!world.isRemote && world.provider.dimensionId==stack.stackTagCompound.getInteger("bindedWorld")){
        var a = stack.stackTagCompound;
        var bx = a.getInteger("bindedX") 
        var by = a.getInteger("bindedY") 
        var bz = a.getInteger("bindedZ")
        var te = world.getTileEntity(bx, by, bz).asInstanceOf[TileEntityLink]
        if (!world.getBlock(x, y, z).isInstanceOf[BlockLink] && world.getBlock(bx, by, bz).isInstanceOf[BlockLink]){
          te.BindedBlock = new BindedBlock(x,y,z,world)

            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "<Binding Complete>"))
          
        }
        
      }
    } else {
      if (!world.isRemote && world.getBlock(x, y, z).isInstanceOf[BlockLink]){
        stack.stackTagCompound.setInteger("bindedX",x)
        stack.stackTagCompound.setInteger("bindedY",y)
        stack.stackTagCompound.setInteger("bindedZ",z)
        stack.stackTagCompound.setInteger("bindedWorld",world.provider.dimensionId)
 
          player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "<Started Binding>"))
        
      } else if (!world.isRemote && world.provider.dimensionId==stack.stackTagCompound.getInteger("bindedWorld")){
        var a = stack.stackTagCompound;
        var bx = a.getInteger("bindedX") 
        var by = a.getInteger("bindedY") 
        var bz = a.getInteger("bindedZ")
        var te = world.getTileEntity(bx, by, bz).asInstanceOf[TileEntityLink]
        if (!world.getBlock(x, y, z).isInstanceOf[BlockLink] && world.getBlock(bx, by, bz).isInstanceOf[BlockLink]){
          te.BindedBlock = new BindedBlock(x,y,z,world)
          
            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "<Binding Complete>"))
          
        }
      }
    }

    return true;
  }
}