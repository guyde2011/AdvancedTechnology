package com.guyde.nano.render

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.Minecraft
import net.minecraft.tileentity.TileEntity
import org.lwjgl.opengl.GL11
import net.minecraft.util.ResourceLocation
import com.guyde.nano.block.TileEntityLaserGrav

class RenderLaserGrav extends TileEntitySpecialRenderer{
  var model = new ModelLaserGrav()
  override def renderTileEntityAt(ent : TileEntity , x : Double ,  y : Double, z : Double, f : Float) {
    GL11.glPushMatrix()
    val textureOff = new ResourceLocation("advtech" ,"textures/models/LaserLine.png");
    val textureOn = new ResourceLocation("advtech" ,"textures/models/LaserLineOn.png");
    var texture = textureOff
    if (ent.asInstanceOf[TileEntityLaserGrav].on){
      texture = textureOn
    }
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
    GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    model.render(null,  0, 0, -0.1F, 0, 0, 0.0625f);

 
    GL11.glPopMatrix();

  }
}