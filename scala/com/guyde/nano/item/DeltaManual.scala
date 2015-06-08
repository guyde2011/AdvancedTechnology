package com.guyde.nano.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.guyde.nano.main.AdvTechMod
import com.guyde.nano.main.AdvTechTab
import net.minecraft.client.renderer.texture.IIconRegister

object NanoManual extends Item with INanoItem{

  val UnlocalName = "book"
  val TextureName = "tex_book"
  this.setCreativeTab(AdvTechTab)
 
  override def registerIcons(ir : IIconRegister) {
    // TODO Auto-generated method stub
    itemIcon = ir.registerIcon("advtech:" + TextureName);
  }
  def reg(){
    ItemRegistry.regItem(this)
  }
  this.setUnlocalizedName(UnlocalName)
  def getItemRef : Item = this;
  override def onItemUse(stack : ItemStack, player : EntityPlayer, world : World, x : Int, y : Int, z : Int, meta : Int, tileX : Float, tileY : Float, tileZ : Float) : Boolean = {
    player.openGui(AdvTechMod, 0, world, x, y, z)
    return true
  }
  
  

}