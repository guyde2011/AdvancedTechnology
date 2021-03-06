package com.guyde.nano.render

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.Minecraft
import net.minecraft.tileentity.TileEntity
import org.lwjgl.opengl.GL11
import net.minecraft.util.ResourceLocation
import com.guyde.nano.block.TileEntityLaserGrav
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.OpenGlHelper

class RenderLaserGrav extends TileEntitySpecialRenderer{
  var model = new ModelLaserGrav()
  def drawBox(lenX : Double , lenY : Double , lenZ : Double) {
      
      RenderHelper.disableStandardItemLighting()
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160F, 160F)
      GL11.glDisable(GL11.GL_ALPHA_TEST)
      GL11.glPushMatrix()
      GL11.glDepthMask(false)
      GL11.glEnable(GL11.GL_BLEND)
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      val tessellator = Tessellator.instance
      tessellator.startDrawingQuads()
      tessellator.addVertex(0, 0, 0)
      tessellator.addVertex(0, lenY, 0)
      tessellator.addVertex(lenX, 0, 0)
      tessellator.addVertex(lenX, lenY, 0)
      tessellator.addVertex(lenX, 0, lenZ)
      tessellator.addVertex(lenX, lenY, lenZ)
      tessellator.addVertex(0, 0, lenZ)
      tessellator.addVertex(0, lenY, lenZ)
      tessellator.draw()
      tessellator.startDrawingQuads()
      tessellator.addVertex(lenX, lenY, 0)
      tessellator.addVertex(lenX, 0, 0)
      tessellator.addVertex(0, lenY, 0)
      tessellator.addVertex(0, 0, 0)
      tessellator.addVertex(0, lenY, lenZ)
      tessellator.addVertex(0, 0, lenZ)
      tessellator.addVertex(lenX, lenY, lenZ)
      tessellator.addVertex(lenX, 0, lenZ)
      tessellator.draw()
      tessellator.startDrawingQuads()
      tessellator.addVertex(0, lenY, 0)
      tessellator.addVertex(lenX, lenY, 0)
      tessellator.addVertex(lenX, lenY, lenZ)
      tessellator.addVertex(0, lenY, lenZ)
      tessellator.addVertex(0, lenY, 0)
      tessellator.addVertex(0, lenY, lenZ)
      tessellator.addVertex(lenX, lenY, lenZ)
      tessellator.addVertex(lenX, lenY, 0)
      tessellator.draw()
      tessellator.startDrawingQuads()
      tessellator.addVertex(0, 0, 0)
      tessellator.addVertex(lenX, 0, 0)
      tessellator.addVertex(lenX, 0, lenZ)
      tessellator.addVertex(0, 0, lenZ)
      tessellator.addVertex(0, 0, 0)
      tessellator.addVertex(0, 0, lenZ)
      tessellator.addVertex(lenX, 0, lenZ)
      tessellator.draw()
      tessellator.startDrawingQuads()
      tessellator.addVertex(0, 0, 0)
      tessellator.addVertex(0, lenY, 0)
      tessellator.addVertex(0, 0, lenZ)
      tessellator.addVertex(0, lenY, lenZ)
      tessellator.addVertex(lenX, 0, lenZ)
      tessellator.addVertex(lenX, lenY, lenZ)
      tessellator.addVertex(lenX, 0, 0)
      tessellator.addVertex(lenX, lenY, 0)
      tessellator.draw()
      tessellator.startDrawingQuads()
      tessellator.addVertex(0, lenY, lenZ)
      tessellator.addVertex(0, 0, lenZ)
      tessellator.addVertex(0, lenY, 0)
      tessellator.addVertex(0, 0, 0)
      tessellator.addVertex(lenX, lenY, 0)
      tessellator.addVertex(lenX, 0, 0)
      tessellator.addVertex(lenX, lenY, lenZ)
      tessellator.addVertex(lenX, 0, lenZ)
      tessellator.draw()
      RenderHelper.enableStandardItemLighting()
      GL11.glEnable(GL11.GL_ALPHA_TEST)
      GL11.glDepthMask(true)
      GL11.glDisable(GL11.GL_BLEND)
      GL11.glPopMatrix()
  }
  override def renderTileEntityAt(ent : TileEntity , x : Double ,  y : Double, z : Double, f : Float) {
    GL11.glPushMatrix()
    val textureOff = new ResourceLocation("advtech" ,"textures/models/LaserLine.png")
    val textureOn = new ResourceLocation("advtech" ,"textures/models/LaserLineOn.png")
    var texture = textureOff
    if (ent.asInstanceOf[TileEntityLaserGrav].on){
      texture = textureOn
    }
    Minecraft.getMinecraft().renderEngine.bindTexture(texture)
    GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5)
    GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F)
    model.render(null,  0, 0, -0.1F, 0, 0, 0.0625f)
    GL11.glPopMatrix()
    if (ent.asInstanceOf[TileEntityLaserGrav].on){
    val texture2 = new ResourceLocation("advtech" ,"textures/misc/laser.png")
    Minecraft.getMinecraft().renderEngine.bindTexture(texture2)
    GL11.glPushMatrix()
    GL11.glTranslated(x + 0.375F, y + 0.25F, z + 0.375F)
    GL11.glColor4f(0f, 0.7f,0.9f,0.5f)
    drawBox(0.25,ent.asInstanceOf[TileEntityLaserGrav].power+0.75,0.25)
    GL11.glPopMatrix()
    GL11.glPushMatrix()
    GL11.glColor4f(0f, 0.8f,1,1f)
    GL11.glTranslated( x + 0.4375F,  y + 0.25F,  z + 0.4375F)
    drawBox(0.125,ent.asInstanceOf[TileEntityLaserGrav].power+0.75,0.125)
    GL11.glPopMatrix()
    }


  }
}