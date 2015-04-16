package com.guyde.nano.item

import com.guyde.nano.craft.PartableTool
import com.guyde.nano.craft.StickTrait
import com.guyde.nano.craft.ToolParts._
import com.guyde.nano.craft.ToolPattern
import com.guyde.nano.craft.ToolTrait
import com.guyde.nano.main.AdvTechMod
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Items
import net.minecraft.item.ItemStack



object Tools{
  var td_pick = new AdvToolData(1000,750,0) + ToolTypes.Pickaxe
  var td_axe = new AdvToolData(1000,750,0) + ToolTypes.Axe
  var td_shovel = new AdvToolData(1000,750,0) + ToolTypes.Shovel
  var td_sword = new AdvToolData(0,600,0)
  object ap_helmet extends ToolPattern() {
    this.slots = Array(MATERIAL , COIL , MATERIAL , MATERIAL , NOTHING , MATERIAL , NOTHING , NOTHING , NOTHING)
  }
  object tp_pick extends ToolPattern() {
    this.slots = Array(MATERIAL , COIL , MATERIAL , NOTHING , STICK , NOTHING , NOTHING , STICK , NOTHING)
  }
  
  object tp_sword extends ToolPattern() {
    this.slots = Array(NOTHING , MATERIAL , NOTHING , STICK , COIL , STICK , NOTHING , STICK , NOTHING)
  }
  
  object tp_axe extends ToolPattern() {
    this.slots = Array(MATERIAL , COIL , NOTHING , MATERIAL , STICK , NOTHING , NOTHING , STICK , NOTHING)
  }
  
  object tp_shovel extends ToolPattern() {
    this.slots = Array(NOTHING , MATERIAL , NOTHING , NOTHING , STICK , NOTHING , NOTHING , COIL , NOTHING)
  }

  val tf_mat = GameRegistry.findItem("ThermalFoundation", "material")
  var pt_pick = new PartableTool(tp_pick  , "Electro-Pickaxe",50000)
  pt_pick.addMaterial(new ItemStack(Items.iron_ingot), "Iron", new ToolTrait(6f , 2 , 6f))
  pt_pick.addMaterial(new ItemStack(Items.diamond), "Diamond", new ToolTrait(7.25f , 3 , 7.5f))
  pt_pick.addMaterial(new ItemStack(Items.gold_ingot), "Gold", new ToolTrait(3f , 2 , 9f))
  pt_pick.addStickMaterial(new ItemStack(Items.stick), "Wooden", new StickTrait(2f , 2f))
  pt_pick.addStickMaterial(new ItemStack(Items.blaze_rod), "Fiery", new StickTrait(2.5f , 2.5f))
  pt_pick.addStickMaterial(new ItemStack(AdvTechMod.stone_stick), "Stone", new StickTrait(2.25f , 2f))
  pt_pick.addMaterial(new ItemStack(tf_mat ,1 , 67 ), "Lead", new ToolTrait(5.5f , 2 , 6.5f))
  pt_pick.addMaterial(new ItemStack(tf_mat,1,67), "Invar", new ToolTrait(6.75f , 3 , 7f))
  pt_pick.addMaterial(new ItemStack(tf_mat,1,73), "Bronze", new ToolTrait(5.5f , 2 , 6f))
  pt_pick.addStickMaterial(new ItemStack(tf_mat,1,1024), "Blizzed", new StickTrait(3f  , 3.5f))
  pt_pick.addMaterial(new ItemStack(tf_mat,1,76), "Enderium", new ToolTrait(8.25f , 2 , 10f))
  pt_pick.addMaterial(new ItemStack(AdvTechMod.enriched_ender), "Enriched Enderium", new ToolTrait(9f , 5 , 11f))
  var pt_axe = new PartableTool(tp_axe  , "Electro-Axe",50000)
  pt_axe.addMaterial(new ItemStack(Items.iron_ingot), "Iron", new ToolTrait(6f , 2 , 6f))
  pt_axe.addMaterial(new ItemStack(Items.diamond), "Diamond", new ToolTrait(8.25f , 3 , 8.5f))
  pt_axe.addMaterial(new ItemStack(Items.gold_ingot), "Gold", new ToolTrait(3f , 2 , 9f))
  pt_axe.addStickMaterial(new ItemStack(Items.stick), "Wooden", new StickTrait(2.5f , 2f))
  pt_axe.addStickMaterial(new ItemStack(Items.blaze_rod), "Fiery", new StickTrait(3.5f , 2.5f))
  pt_axe.addStickMaterial(new ItemStack(AdvTechMod.stone_stick), "Stone", new StickTrait(3f , 2f))
  pt_axe.addMaterial(new ItemStack(tf_mat ,1 , 67 ), "Lead", new ToolTrait(6.5f , 2 , 6.5f))
  pt_axe.addMaterial(new ItemStack(tf_mat,1,67), "Invar", new ToolTrait(7.75f , 3 , 7f))
  pt_axe.addMaterial(new ItemStack(tf_mat,1,73), "Bronze", new ToolTrait(6.5f , 2 , 6f))
  pt_axe.addStickMaterial(new ItemStack(tf_mat,1,1024), "Blizzed", new StickTrait(4f  , 3.5f))
  pt_axe.addMaterial(new ItemStack(tf_mat,1,76), "Enderium", new ToolTrait(10f , 2 , 10f))
  pt_axe.addMaterial(new ItemStack(AdvTechMod.enriched_ender), "Enriched Enderium", new ToolTrait(11f , 5 , 11f))
  var pt_shovel = new PartableTool(tp_shovel  , "Electro-Shovel",50000)
  pt_shovel.addMaterial(new ItemStack(Items.iron_ingot), "Iron", new ToolTrait(4f , 2 , 6f))
  pt_shovel.addMaterial(new ItemStack(Items.diamond), "Diamond", new ToolTrait(6.25f , 3 , 8.5f))
  pt_shovel.addMaterial(new ItemStack(Items.gold_ingot), "Gold", new ToolTrait(3f , 2 , 9f))
  pt_shovel.addStickMaterial(new ItemStack(Items.stick), "Wooden", new StickTrait(1.5f , 2f))
  pt_shovel.addStickMaterial(new ItemStack(Items.blaze_rod), "Fiery", new StickTrait(2.25f , 2.5f))
  pt_shovel.addStickMaterial(new ItemStack(AdvTechMod.stone_stick), "Stone", new StickTrait(1.75f , 2f))
  pt_shovel.addMaterial(new ItemStack(tf_mat ,1 , 67 ), "Lead", new ToolTrait(4.5f , 2 , 6.5f))
  pt_shovel.addMaterial(new ItemStack(tf_mat,1,67), "Invar", new ToolTrait(5.75f , 3 , 7f))
  pt_shovel.addMaterial(new ItemStack(tf_mat,1,73), "Bronze", new ToolTrait(4.5f , 2 , 6f))
  pt_shovel.addMaterial(new ItemStack(tf_mat,1,76), "Enderium", new ToolTrait(8f , 4 , 10f))
  pt_shovel.addMaterial(new ItemStack(AdvTechMod.enriched_ender), "Enriched Enderium", new ToolTrait(8.25f , 5 , 11f))
  pt_shovel.addStickMaterial(new ItemStack(tf_mat,1,1024), "Blizzed", new StickTrait(4f  , 3.5f))
  var pt_sword = new PartableTool(tp_sword  , "Electro-Sword",50000)
  pt_sword.addMaterial(new ItemStack(Items.iron_ingot), "Iron", new ToolTrait(6.75f , 2 , 0f))
  pt_sword.addMaterial(new ItemStack(Items.diamond), "Diamond", new ToolTrait(9f , 3 , 0f))
  pt_sword.addMaterial(new ItemStack(Items.gold_ingot), "Gold", new ToolTrait(3.5f , 2 , 0f))
  pt_sword.addStickMaterial(new ItemStack(Items.stick), "Wooden", new StickTrait(2.75f , 0f))
  pt_sword.addStickMaterial(new ItemStack(Items.blaze_rod), "Fiery", new StickTrait(3.75f , 0f))
  pt_sword.addStickMaterial(new ItemStack(AdvTechMod.stone_stick), "Stone", new StickTrait(3.25f , 0f))
  pt_sword.addMaterial(new ItemStack(tf_mat ,1 , 67 ), "Lead", new ToolTrait(6.75f , 2 , 0f))
  pt_sword.addMaterial(new ItemStack(tf_mat,1,67), "Invar", new ToolTrait(8f , 3 , 0f))
  pt_sword.addMaterial(new ItemStack(tf_mat,1,73), "Bronze", new ToolTrait(7f , 2 , 0f))
  pt_sword.addStickMaterial(new ItemStack(tf_mat,1,1024), "Blizzed", new StickTrait(4.25f  , 0f))
  pt_sword.addMaterial(new ItemStack(tf_mat,1,76), "Enderium", new ToolTrait(10.5f , 2 , 0f))
  pt_sword.addMaterial(new ItemStack(AdvTechMod.enriched_ender), "Enriched Enderium", new ToolTrait(11.75f , 5 , 0f))
  object AdvancedPickaxe extends AdvTool(td_pick , pt_pick , "advpickaxe" , "pickaxe"){
  }
  object AdvancedAxe extends AdvTool(td_axe , pt_axe , "advaxe" , "axe"){
  }
  object AdvancedShovel extends AdvTool(td_shovel , pt_shovel , "advshovel" , "shovel"){
  }
  
  object AdvancedSword extends AdvTool(td_sword , pt_sword , "advsword" , "sword"){
  }
  

}
