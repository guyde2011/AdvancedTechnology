package com.guyde.nano.block

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.util.AxisAlignedBB
import net.minecraft.entity.Entity
import com.guyde.nano.item.ListConverter
import net.minecraft.nbt.NBTTagCompound
import com.guyde.nano.block.BindedBlock

class BlockLaserGrav() extends BlockContainer(Material.iron){
  def createNewTileEntity(world : World , int : Int) : TileEntity = {
    return new TileEntityLaserGrav()
  }
  GameRegistry.registerBlock(this, "laser_grav")
     override def isOpaqueCube() : Boolean =  {
             return false;
     }
    
     //It's not a normal block, so you need this too.
     override def renderAsNormalBlock() : Boolean = {
             return false;
     }
      override def getRenderType() : Int ={
    return -1;
    
  }

      
}

class TileEntityLaserGrav() extends TileEntity(){
  
  private var _on : Boolean = false
  
  def on = _on
  
  def on_=(bool : Boolean){
    _on = bool
  }
  
  override def readFromNBT(tag : NBTTagCompound ) {
    super.readFromNBT(tag)
    on = tag.getBoolean("on")
  }  


  override def writeToNBT(tag : NBTTagCompound ) { 
    super.writeToNBT(tag)
    tag.setBoolean("on", on)
  }
  
}