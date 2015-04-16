package com.guyde.nano.item

import net.minecraft.item._
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemTool
import net.minecraft.client.renderer.texture.IIconRegister
import com.guyde.nano.main.AdvTechTab
/**
 * @author Guyde
 */

class NanoBasicItem(name : String , tex_name : String) extends Item with INanoItem{

  val UnlocalName = name;
  val TextureName = tex_name;
  this.setUnlocalizedName(UnlocalName);
  this.setCreativeTab(AdvTechTab)
  def this(name : String) = {this(name , name) }
  override def registerIcons(ir : IIconRegister) {
    // TODO Auto-generated method stub
    itemIcon = ir.registerIcon("advtech:" + TextureName);
  }
  def reg(){
    ItemRegistry.regItem(this)
  }
  
  def getItemRef() : Item = {
    return this;
  }

}


trait INanoItem{
  def UnlocalName : String 
  def TextureName : String

  def getItemRef : Item
  
}
object ItemRegistry {
  private var items : Map[String,INanoItem] = Map()
  private var energy_items : Map[String,INanoEnergyItem] = Map()
  
  def regItem(item : INanoItem) {
    items = items + (item.UnlocalName -> item);
  } 
  
  def regEnergyItem(item : INanoEnergyItem) {
    energy_items = energy_items + (item.UnlocalName -> item);
  } 
  
  def getItemByName(name : String) : Item = {
    items.get(name).foreach { x => return x.getItemRef}
    return null
    
  }
  
  def load(){
    items.foreach((e : ( String , INanoItem))=>{GameRegistry.registerItem(e._2.getItemRef,e._1);})
  }
  
  def foreachEn[U](f: (((String,INanoEnergyItem)) => U)){
    energy_items.foreach(f)
  }
  
}
