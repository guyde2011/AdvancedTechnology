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
import com.guyde.nano.entity.EntityExplodeBug

class BugItem extends NanoBasicItem("exp_bug"){
  this.setMaxStackSize(1)
  
  override def onItemUse(stack : ItemStack, player : EntityPlayer, world : World, x : Int, y : Int, z : Int, side : Int, hitX : Float, hitY : Float, hitZ : Float) : Boolean = {
    if (stack.stackTagCompound==null){
      stack.stackTagCompound = new NBTTagCompound()
    }
    if (player.isSneaking()){
      if (!world.isRemote && world.getBlock(x, y, z).isOpaqueCube()){
        val ent = new EntityExplodeBug(world , player.getDisplayName)
        ent.setLocationAndAngles(x+hitX, y+hitY, z+hitZ, 0, 0)
        world.spawnEntityInWorld(ent)
        player.setCurrentItemOrArmor(0, null)
      } 
    }     

    return true;
  }
}