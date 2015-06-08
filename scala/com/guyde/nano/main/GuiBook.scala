package com.guyde.nano.main

import org.lwjgl.opengl.GL11
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.item.ItemStack
import net.minecraft.init.Items
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.RenderHelper
import org.lwjgl.opengl.GL12

class GuiBook extends GuiScreen{
    val pageLeft = new ResourceLocation("advtech:textures/gui/Book_left.png")
  var objects = List[ScreenObject](new RenderedStack(new ItemStack(Items.bow),30,40),new Text(0xFFFFFF,3,"Hello world, this is my test book, what's up")); 
  val pageX = 124
  val pageY = 158

  override def drawScreen(mouseX : Int , mouseY : Int , partialTicks : Float){
    this.drawDefaultBackground();
    pageBackground
    objects.foreach { x => renderScreenObject(x , mouseX , mouseY) }
  }
  
  override def mouseClicked(x : Int , y : Int , click : Int){
    println(x + " , " + y)
    if (click==0){
      val c = objects.filter({ o => o.isInstanceOf[RightClickable] }).asInstanceOf[List[RightClickable]].filter {
          p => val obj = p.asInstanceOf[ScreenObject]; 
          (getX+obj.X<=x && getX+obj.X+p.sizeX>=x && getY+obj.Y<=y && getY+obj.Y+p.sizeY>=y)
        }
      c.foreach { a => a.renderRightClicked(this); a.onRightClick(this)}
    } else if (click==1){
      val c = objects.filter({ o => o.isInstanceOf[LeftClickable] }).asInstanceOf[List[LeftClickable]].filter {
                  p => val obj = p.asInstanceOf[ScreenObject]; 
          (getX+obj.X<=x && getX+obj.X+p.sizeX>=x && getY+obj.Y<=y && getY+obj.Y+p.sizeY>=y)
      };
      c.foreach { a => a.renderLeftClicked(this); a.onLeftClick(this)}
    }

    
  }
  final def getX() : Int = width  / 2 - (pageX*2+4);
  final def getY() : Int = height  / 2 - (pageY*2-60)/2;
  final def pageBackground(){
    val x = width  / 2 - (pageX*2+4);
    val y = height  / 2 - (pageY*2-60)/2;
    val pageL = new ResourceLocation("advtech:textures/gui/Book_left.png")
    val pageR = new ResourceLocation("advtech:textures/gui/Book_right.png")
    Minecraft.getMinecraft.getTextureManager.bindTexture(pageL)
    this.drawTexturedModalRect( x,y,0,0,pageX*2+4,pageY*2-60)

    Minecraft.getMinecraft.getTextureManager.bindTexture(pageR)
    this.drawTexturedModalRect( x+pageX*2+4,y,0,0,pageX*2+6,pageY*2-60)
  }
  
  final def getFontObj() : FontRenderer = {
    return this.fontRendererObj
  }
  
  final def drawItem(it : ItemStack , x : Int , y : Int){
    val ir = RenderItem.getInstance;
    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    RenderHelper.disableStandardItemLighting();
    GL11.glDisable(GL11.GL_LIGHTING);
    GL11.glDisable(GL11.GL_DEPTH_TEST);
    ir.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, it, x, y);
    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    RenderHelper.enableStandardItemLighting();
    GL11.glEnable(GL11.GL_LIGHTING);
    GL11.glEnable(GL11.GL_DEPTH_TEST);


  } 
  def drawHoverText(stack : ItemStack , x : Int , y : Int) : Unit = {

    this.renderToolTip(stack , x , y)
  }
  final def renderScreenObject(obj : ScreenObject , x : Int , y : Int){
    obj.renderNormally(this);
    if (obj.isInstanceOf[Hoverable]){
      var hover = obj.asInstanceOf[Hoverable] 
      var p = obj.asInstanceOf[Sized]
      if (getX+obj.X<=x && getX+obj.X+p.sizeX>=x && getY+obj.Y<=y && getY+obj.Y+p.sizeY>=y){
        hover.renderHovered(this)
      }
    }
  }
}

object PageVars{
  val pageX = 124
  val pageY = 158
}
abstract class ScreenObject(x : Int , y : Int){
  final def X() : Int =x
  final def Y=y
  def renderNormally(gui : GuiBook) : Boolean;
}

trait Sized{
  def sizeX : Int
  def sizeY : Int
}

trait Hoverable extends Sized{

  def renderHovered(gui : GuiBook) : Boolean;
}

trait RightClickable extends Sized{
  def onRightClick(gui : GuiBook) : Boolean;
  def renderRightClicked(gui : GuiBook) : Boolean;
}

trait LeftClickable extends Sized{
  def onLeftClick(gui : GuiBook) : Boolean;
  def renderLeftClicked(gui : GuiBook) : Boolean;
}

class RenderedStack(stack : ItemStack , x : Int , y : Int) extends ScreenObject(x,y) with Hoverable{
  
  def renderNormally(gui : GuiBook) : Boolean = {
    gui.drawItem(stack, gui.getX+x, gui.getY+y);
    return true;
  }
  val sizeX : Int = 16
  val sizeY : Int = 16
  
  def renderHovered(gui : GuiBook) : Boolean = {
    gui.drawHoverText(stack,  gui.getX+x+8, gui.getY+y+20)
    return true;
    
  }
  
}

class Image(loc : ResourceLocation , x : Int , y : Int , x_ : Int , y_ : Int) extends ScreenObject(x,y){
    def renderNormally(gui : GuiBook) : Boolean = {
    Minecraft.getMinecraft.getTextureManager.bindTexture(loc)
    gui.drawTexturedModalRect(gui.getX()+x,gui.getY()+y,0,0,x_,y_);
    return true;
  }
  val sizeX : Int = x_
  val sizeY : Int = y_
}

class Text(color : Int , y : Int, text : String) extends ScreenObject(32,y*12){
    def renderNormally(gui : GuiBook) : Boolean = {
      GL11.glColor3f(1,1,1)
      val words = text.split(" ");
      val sp = gui.getFontObj().getStringWidth(" ");
      val height = 12
      var lines = Array[String]();
      var curLine = ""
      val lineLength = 210
      val pageHeight = 220
      words.foreach { p =>
        if (gui.getFontObj().getStringWidth(curLine + " " + p)<=lineLength){
          curLine = curLine + " "+ p
        } else {
          lines = lines ++ Array[String](curLine)
          curLine = p
        }
      }
      lines = lines ++ Array[String](curLine)
      var i = 0
      lines.foreach { q =>
        if (pageHeight > height*y + height*i){
          gui.getFontObj().drawString( q, gui.getX()+32,gui.getY()+(y+i)*height+20,color); 
        }
        i = i + 1;
      }
 //     gui.getFontObj().drawString( text, gui.getX()+x,gui.getY()+y,size);
      return true
    }
  
}
