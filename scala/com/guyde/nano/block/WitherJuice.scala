package com.guyde.nano.block

import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry

class WitherJuice extends Fluid("wither_juice") {
   setUnlocalizedName("wither_juice")
   FluidRegistry.registerFluid(this);
}