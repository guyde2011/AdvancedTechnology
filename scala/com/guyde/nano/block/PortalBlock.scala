package com.guyde.nano.block

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.Minecraft

class PortalTileEntity() extends TileEntity(){
  @SideOnly(Side.CLIENT)
  private var xCamera = this.xCoord + 0.5d
  @SideOnly(Side.CLIENT)
  private var yCamera = this.yCoord + 1.8d
  @SideOnly(Side.CLIENT)
  private var zCamera = this.zCoord + 0.5d
  
  @SideOnly(Side.CLIENT)
  def setCameraPosition(x : Double , y : Double , z : Double){
    xCamera = x
    yCamera = y
    zCamera = z
  }
  
  @SideOnly(Side.CLIENT)
  private def orientCamera(){
    var mc = Minecraft.getMinecraft
  }
  
}


class PortalBlock(name : String) extends BlockContainer(Material.iron){
  this.setBlockName(name)
  GameRegistry.registerBlock(this, "advtech:" + name)
  
  def createNewTileEntity(world : World , int : Int) : TileEntity = {
    return new PortalTileEntity()
  }
}