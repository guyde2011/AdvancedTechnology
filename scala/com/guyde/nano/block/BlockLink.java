package com.guyde.nano.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
public class BlockLink extends BlockLinkDema{
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileEntityLink();
	}

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float a, float b, float c)
    {
		
		if (this.getTileEntity(x, y, z, world)!=null && isValid(x,y,z,world)){
			

			world.func_147453_f(x, y, z, this);
			
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			return block.getBlock().onBlockActivated(world, block.getX() , block.getY() , block.getZ() ,player ,  i , a , b ,c );
		}
		return false;

    }
	
	

	


	
	@SideOnly(Side.CLIENT)
	public IIcon BlockIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		this.BlockIcon = icon.registerIcon("advtech:block_link");
		this.blockIcon = this.BlockIcon;
	}
	


	@Override
	public void onBlockClicked(World world, int x,
			int y, int z, EntityPlayer player) {
if (this.getTileEntity(x, y, z, world)!=null && isValid(x,y,z,world)){
			

			world.func_147453_f(x, y, z, this);
			
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			block.getBlock().onBlockClicked(world, block.getX() , block.getY() , block.getZ() ,player );
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x,
			int y, int z, Block block1) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			super.onNeighborBlockChange(world,x,y,z,block1);
		} else {
			
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			block.getBlock().onNeighborBlockChange(world, block.getX() , block.getY() , block.getZ() ,block.getBlock() );
			
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_,
			int p_149707_3_, int p_149707_4_, int p_149707_5_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEntityWalking(World world, int x,
			int y, int z, Entity ent) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
		super.onEntityWalking(world , x , y , z , ent);
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			block.getBlock().onEntityWalking(world, block.getX() , block.getY() , block.getZ() ,ent );
		}
	}


	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x,
			int y, int z, int side) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			return super.isProvidingWeakPower(world , x , y , z , side);
			} else {
				BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
				return block.getBlock().isProvidingWeakPower(world, block.getX() , block.getY() , block.getZ() ,side );
			}
	}



	@Override
	public void onEntityCollidedWithBlock(World world, int x,
			int y, int z, Entity ent) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			super.onEntityCollidedWithBlock(world , x , y , z , ent);
			} else {
				BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
				block.getBlock().onEntityCollidedWithBlock(world, block.getX() , block.getY() , block.getZ() ,ent );
			}
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x,
			int y, int z, int side) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			return super.isProvidingStrongPower(world , x , y , z , side);
			} else {
				BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
				return block.getBlock().isProvidingStrongPower(world, block.getX() , block.getY() , block.getZ() ,side );
			}
	}

	@Override
	public void onFallenUpon(World world, int x,
			int y, int z, Entity ent , float f) {
		// TODO Auto-generated method stub
				if (!isValid(x,y,z,world)){
					super.onFallenUpon(world , x , y , z , ent,f);
					} else {
						BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
						block.getBlock().onFallenUpon(world, block.getX() , block.getY() , block.getZ() ,ent ,f );
					}

	}

	@Override
	public int getComparatorInputOverride(World world, int x,
			int y, int z, int side) {
		// TODO Auto-generated method stub

		if (!isValid(x,y,z,world)){
			return super.getComparatorInputOverride(world , x , y , z , side);
			} else {
				BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
				return block.getBlock().getComparatorInputOverride(world, block.getX() , block.getY() , block.getZ() ,side );
			}
		
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			return 0;
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			return block.getBlock().getLightValue(world, block.getX() , block.getY() , block.getZ() );
		}

		
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			return 0;
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			return block.getBlock().getLightOpacity(world, block.getX() , block.getY() , block.getZ() );
		}
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z,
			ForgeDirection axis) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			return super.rotateBlock(world , x , y , z,axis);
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			return block.getBlock().rotateBlock(world, block.getX() , block.getY() , block.getZ() ,axis);
		}
	}

	@Override
	public float getEnchantPowerBonus(World world, int x, int y, int z) {
		if (!isValid(x,y,z,world)){
			return super.getEnchantPowerBonus(world , x , y , z);
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			return block.getBlock().getEnchantPowerBonus(world, block.getX() , block.getY() , block.getZ());
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			super.onNeighborChange(world , x , y , z,tileX,tileY,tileZ);
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			block.getBlock().onNeighborChange(world, block.getX() , block.getY() , block.getZ() ,tileX , tileY , tileZ);
		}
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y,
			int z, int side) {
		// TODO Auto-generated method stub
		if (!isValid(x,y,z,world)){
			return super.shouldCheckWeakPower(world , x , y , z,side);
		} else {
			BindedBlock block = this.getTileEntity(x, y, z, world).BindedBlock();
			return block.getBlock().shouldCheckWeakPower(world, block.getX() , block.getY() , block.getZ() ,side);
		}
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		// TODO Auto-generated method stub
		return blockIcon;
	}
}