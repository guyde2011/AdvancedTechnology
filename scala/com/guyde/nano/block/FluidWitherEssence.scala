package com.guyde.nano.block

import net.minecraftforge.fluids.BlockFluidClassic
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.util.IIcon
import net.minecraftforge.fluids.Fluid
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.material.Material
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fluids.FluidRegistry

class FluidWitherEssence(fluid : Fluid , material : Material) extends BlockFluidClassic(fluid , material){

        @SideOnly(Side.CLIENT)
        var stillIcon : IIcon = null;
        @SideOnly(Side.CLIENT)
         var flowingIcon : IIcon = null;
       
        

        override def getIcon(side : Int, meta : Int) : IIcon = {
                return if(side.equals(0) || side.equals(1)) stillIcon else flowingIcon;
        }
       
        @SideOnly(Side.CLIENT)
        override def registerBlockIcons(register : IIconRegister) {
                stillIcon = register.registerIcon("advtech:WitherStill");
                flowingIcon = register.registerIcon("advtech:WitherFlowing");
                FluidRegistry.getFluid("wither_juice").setIcons(stillIcon, flowingIcon)
        }
       
        override def canDisplace( world : IBlockAccess, x : Int, y : Int , z : Int ) : Boolean = {
                if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
                return super.canDisplace(world, x, y, z);
        }
       

        override def displaceIfPossible(world : World, x : Int, y : Int , z : Int ) : Boolean = {
                if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
                return super.displaceIfPossible(world, x, y, z);
        }
       
}