package com.guyde.nano.block

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTankInfo
import net.minecraftforge.fluids.IFluidHandler

class BindedBlock(x : Int , y : Int , z : Int , world : World){
  def getBlock() : Block = {
    return world.getBlock(x, y, z)
  }
  
  def hasBlock() : Boolean = {
    return world.getBlock(x, y, z)!=null && world.getBlock(x, y, z)!=Blocks.air
  }
  
  def getTileEntity() : TileEntity = {
    return world.getTileEntity(x, y, z)
  }
  
  def hasTileEntity() : Boolean = {
    return world.getTileEntity(x, y, z)!=null
  }
  
  def isValid() : Boolean = {
    return !world.getTileEntity(x, y, z).isInstanceOf[TileEntityLink] && hasBlock()
  }
  
  def isInventory() : Boolean = {
    return hasTileEntity && world.getTileEntity(x, y, z).isInstanceOf[IInventory]
  }
  
  def isSidedInventory() : Boolean = {
    return hasTileEntity && world.getTileEntity(x, y, z).isInstanceOf[ISidedInventory]
  }
  
  def isFluidTank() : Boolean = {
    return hasTileEntity && world.getTileEntity(x, y, z).isInstanceOf[IFluidHandler]
  }
  
  def getX = x
  def getY = y
  def getZ = z


}
class TileEntityLink() extends TileEntity() with ISidedInventory with IFluidHandler{

	
	private var delay = 0;
	private var last = false;
	var upd = false;
	private var slots = Array[Int]();
	private var block : BindedBlock = null
  private var tex_block : BindedBlock = null
	def BindedBlock = block
  def TextureBlock = tex_block
  def BindedBlock_= (b : BindedBlock){
    if (b.isValid()) block=b
  }
  def TextureBlock_= (b : BindedBlock){
    if (b.isValid()) tex_block=b
  }
  
  def canDrain(dir : ForgeDirection,fluid : Fluid): Boolean = {
    if (block!=null && block.isFluidTank()) {
      return getLinkedTank.canDrain(dir, fluid)
    }
    return false
  } 
  def canFill(dir : ForgeDirection, fluid : Fluid): Boolean = {
      if (block!=null && block.isFluidTank()) {
        return getLinkedTank.canFill(dir, fluid)
      }
      return false
    }
  def drain( from : ForgeDirection, stack : FluidStack,bool : Boolean): FluidStack = {
      if (block!=null && block.isFluidTank()) {
        return getLinkedTank.drain(from, stack , bool)
      }
      return null
  }
  
    def drain( from : ForgeDirection, a : Int,bool : Boolean): FluidStack = {
      if (block!=null && block.isFluidTank()) {
        return getLinkedTank.drain(from, a , bool)
      }
      return null
  }

  def fill(from : ForgeDirection, stack : FluidStack,bool : Boolean): Int = {      
    if (block!=null && block.isFluidTank()) {
        return getLinkedTank.fill(from, stack , bool)
      }
      return 0
  }
  
  def getTankInfo(dir : ForgeDirection): Array[FluidTankInfo] = {
      if (block!=null && block.isFluidTank()) {
        return getLinkedTank.getTankInfo(dir)
      }
      return Array[FluidTankInfo]()
  }

  
  def getLinkedTank() : IFluidHandler = {
    if (block!=null && block.isFluidTank()) return block.getTileEntity().asInstanceOf[IFluidHandler]
    return null
  }
	
	 def getLinkedInv() : IInventory = {
		 if (block !=null && block.hasTileEntity() && block.isInventory()) return block.getTileEntity().asInstanceOf[IInventory] else return null;
   }
	
	def getLinkedSidedInv() : ISidedInventory = {
		if (block !=null && block.hasTileEntity() &&block.isSidedInventory()) block.getTileEntity().asInstanceOf[ISidedInventory] else null;
	}
	
   override def readFromNBT(tag : NBTTagCompound ) {
                super.readFromNBT(tag); 
                if (tag.hasKey("bind") && tag.getBoolean("bind")){
                  var x = tag.getInteger("xbind")
                  var y = tag.getInteger("ybind")
                  var z = tag.getInteger("zbind")
                  BindedBlock = new BindedBlock(x,y,z,worldObj)
                }
        }  


  override def writeToNBT(tag : NBTTagCompound ) { 
                super.writeToNBT(tag);
                tag.setBoolean("bind", false)
                if (BindedBlock!=null){
                  tag.setBoolean("true", false)
                   tag.setInteger("xbind",BindedBlock.getX)
                   tag.setInteger("ybind",BindedBlock.getY)
                   tag.setInteger("zbind",BindedBlock.getZ)
                   
                }
        }
	

        override def updateEntity(){
          worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
          worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType);
        }
        

	override def getSizeInventory() : Int = {
		// TODO Auto-generated method stub
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.getSizeInventory();
		}
		return 0;
	}

	override
	def  getStackInSlot( p_70301_1_ : Int) : ItemStack = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.getStackInSlot(p_70301_1_);
		}
		return null;
	}

	override def decrStackSize(p_70298_1_ : Int ,p_70298_2_ : Int) : ItemStack = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.decrStackSize(p_70298_1_, p_70298_2_);
		}
		return null;
	}

	override def getStackInSlotOnClosing(p_70304_1_ : Int) : ItemStack = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.getStackInSlotOnClosing(p_70304_1_);
		}
		return null;
	}

	override
	def setInventorySlotContents(p_70299_1_ : Int,  p_70299_2_ : ItemStack) {
		// TODO Auto-generated method stub
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			inv.setInventorySlotContents(p_70299_1_, p_70299_2_);
		}
	}

	override def  getInventoryName() : String = {
		// TODO Auto-generated method stub
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.getInventoryName();
		}
		return "intralinking_terminal";
	}

	override def hasCustomInventoryName() : Boolean = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.hasCustomInventoryName();
		}
		return false;
	}

	override def getInventoryStackLimit() : Int = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.getInventoryStackLimit();
		}
		return 0;
	}

	override
	def  markDirty() {
		// TODO Auto-generated method stub
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			inv.markDirty();
		}

	}

	override def isUseableByPlayer(p : EntityPlayer) : Boolean = {
		// TODO Auto-generated method stub
		return p.getDistanceSq(xCoord , yCoord , zCoord)<64;
	}

	override
	def  openInventory() {
		// TODO Auto-generated method stub
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			inv.openInventory();
		}
	}

	override def closeInventory() {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			inv.closeInventory();
		}
		
	}

	override def isItemValidForSlot( p_94041_1_ : Int, p_94041_2_ : ItemStack) : Boolean =  {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (inv!=null){
			return inv.isItemValidForSlot(p_94041_1_, p_94041_2_);
		}
		return false;
	}

	override def  getAccessibleSlotsFromSide(p_94128_1_ : Int) : Array[Int] = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (sided!=null){
			return sided.getAccessibleSlotsFromSide(p_94128_1_);
		} else if (inv!=null){
			        var abc = (0 to inv.getSizeInventory-1)
        return abc.toArray
		}
		return Array[Int]()
	}
	


	override def canInsertItem( p_102007_1_ : Int, p_102007_2_ :  ItemStack,
			 p_102007_3_ : Int) : Boolean = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (sided!=null){
			return sided.canInsertItem(p_102007_1_, p_102007_2_, p_102007_3_);
		} 
		if( inv!=null ) return  true 
    return false;

	}

	override def canExtractItem(p_102008_1_ : Int, p_102008_2_ : ItemStack, p_102008_3_ : Int) : Boolean = {
		var inv = this.getLinkedInv();
		var sided = this.getLinkedSidedInv();
		if (sided!=null){
			return sided.canExtractItem(p_102008_1_, p_102008_2_, p_102008_3_);
		}
		if(inv!=null ) return true 
    return false;
	}


	
}
