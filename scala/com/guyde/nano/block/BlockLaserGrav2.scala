package com.guyde.nano.block

import scala.collection.JavaConversions._
import com.guyde.nano.block.BindedBlock
import com.guyde.nano.block.TileEntityLaserGrav
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ChatComponentText
import net.minecraft.world.World
import net.minecraft.util.EnumChatFormatting
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.Packet
import net.minecraft.util.IIcon
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side

class BlockLaserGrav2() extends BlockContainer(Material.iron){
  def createNewTileEntity(world : World , int : Int) : TileEntity = {
    return new TileEntityLaserGrav2()
  }
  this.setBlockName("lasergrav2")
  this.setHardness(2.0f)
  this.setHarvestLevel("pickaxe",1)
  @SideOnly(Side.CLIENT)
  override def registerBlockIcons(register : IIconRegister) {
    blockIcon = register.registerIcon("advtech:laserdummy2")
  }
  override def getIcon(i : Int , j : Int) : IIcon = {
    // TODO Auto-generated method stub
    return blockIcon;
  }
  GameRegistry.registerBlock(this, "laser_grav2")
     override def isOpaqueCube() : Boolean =  {
             return false;
             
     }
    
     //It's not a normal block, so you need this too.
     override def renderAsNormalBlock() : Boolean = {
             return false;
     }
     override def onBlockActivated(world : World, x : Int, y : Int, z : Int, player : EntityPlayer, meta : Int, tX : Float , tY : Float , tZ : Float) : Boolean = {
       if (player.getHeldItem!=null && player.getHeldItem.getItem==Items.stick ){
         world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].power = world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].power+1
         if (world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].power==16) world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].power = 0
         if (!world.isRemote)
         player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA+"The Laser's power set to "+EnumChatFormatting.GREEN+world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].power))
       }
       return true
     }
      override def getRenderType() : Int ={
    return -1;
    
  }
  override def onNeighborBlockChange(world : World, x : Int, y : Int,  z : Int, block : Block) {
     
    if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
        world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].on=true
        

      }else{
     
        world.getTileEntity(x, y, z).asInstanceOf[TileEntityLaserGrav2].on=false

      }
    
  }

      
}

class TileEntityLaserGrav2() extends TileEntity(){
  
  private var _on : Boolean = false
  
  def on = _on
  
  var power = 0
  def on_=(bool : Boolean){
    _on = bool
  }
  
  override def readFromNBT(tag : NBTTagCompound ) {
    super.readFromNBT(tag)
    if (tag.hasKey("powerGrav")){
      power = tag.getInteger("powerGrav")
    }
    if (tag.hasKey("onGrav")){
      on = tag.getBoolean("onGrav")
    }
    
  }
  override def getRenderBoundingBox() : AxisAlignedBB = 
  { 
    return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 16, zCoord + 1);
  }

  override def getDescriptionPacket() : Packet = {
    
    var tag = new NBTTagCompound();
    writeToNBT(tag)
    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
  }
   override def onDataPacket(net : NetworkManager , pkt : S35PacketUpdateTileEntity)
   {
       readFromNBT(pkt.func_148857_g());
   }


  override def writeToNBT(tag : NBTTagCompound ) { 
    super.writeToNBT(tag)
    tag.setInteger("powerGrav", power)
    tag.setBoolean("onGrav",on)
  }
  
  override def updateEntity(){
  if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
    on = true
    var ents = this.worldObj.getEntitiesWithinAABB(classOf[Entity], AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord+1, this.yCoord+power+1, this.zCoord+1))
      ents.foreach{e => e.asInstanceOf[Entity].fallDistance=0; 
      if (!e.isInstanceOf[EntityPlayer] || !e.asInstanceOf[EntityPlayer].isSneaking()){
        e.asInstanceOf[Entity].moveEntity(0, -0.1, 0); 
        e.asInstanceOf[Entity].motionY= -0.1d
        } else {
          e.asInstanceOf[Entity].moveEntity(0, 0.1, 0); 
          e.asInstanceOf[Entity].motionY= 0.1d
        }
    }
    } else {
      on = false
    }
  
      
      
    
  }
}