package com.guyde.nano.block;
import java.util.List
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.block.BlockChest
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.init.Blocks


abstract class BlockLinkDema extends BlockContainer(Material.iron){
	

  this.setHarvestLevel("pickaxe", 3)
  setBlockName("Block_Link")
	def isValid( x : Int , y : Int , z : Int , world : IBlockAccess ) : Boolean = {
		return world.getTileEntity(x,y,z)!=null && world.getTileEntity(x, y, z).asInstanceOf[TileEntityLink].BindedBlock!=null && world.getBlock(x,y,z)!=Blocks.air && world.getBlock(x,y,z)!=null && world.getTileEntity(x, y, z).asInstanceOf[TileEntityLink].BindedBlock.hasBlock()
	}
	GameRegistry.registerBlock(this,"block_link")

	def isTexValid( x : Int , y : Int , z : Int , world : IBlockAccess ) : Boolean = {
		return world.getTileEntity(x, y, z).asInstanceOf[TileEntityLink].BindedBlock!=null;
	}
  
  def getTileEntity( x : Int , y : Int , z : Int , world : IBlockAccess ) : TileEntityLink = {
    return world.getTileEntity(x, y, z).asInstanceOf[TileEntityLink]
  }
}
