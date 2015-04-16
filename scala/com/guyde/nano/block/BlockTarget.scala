package com.guyde.nano.block

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.util.AxisAlignedBB
import net.minecraft.entity.Entity
import com.guyde.nano.item.ListConverter

class BlockTarget() extends BlockContainer(Material.iron){
  def createNewTileEntity(world : World , int : Int) : TileEntity = {
    return new EntityTarget()
  }
  GameRegistry.registerBlock(this, "target")
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
      override def addCollisionBoxesToList(world : World, x : Int,  y : Int,  z : Int, mask : AxisAlignedBB , list : java.util.List[_], collidingEntity : Entity )
     {
        var aabb = this.getCollisionBoundingBoxFromPool(world, x, y, z);

           ListConverter.addTo(list,aabb);
           var help = 0.0625;
           ListConverter.addTo(list,AxisAlignedBB.getBoundingBox(x - help ,y + 32 * help,z - help,x + help,y +16*help,z-help));

        
     }
      
}

class EntityTarget() extends TileEntity(){
  
}