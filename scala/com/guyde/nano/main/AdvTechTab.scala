package com.guyde.nano.main

import net.minecraft.creativetab.CreativeTabs
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.item.Item
import cpw.mods.fml.relauncher.Side

object AdvTechTab extends CreativeTabs("AdvTech"){

 


  @SideOnly(Side.CLIENT)
  override def getTabIconItem() : Item = {
      return AdvTechMod.enth
  }
}