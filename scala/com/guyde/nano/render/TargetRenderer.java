package com.guyde.nano.render;


import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3d;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;


public class TargetRenderer extends TileEntitySpecialRenderer 
	{
	public ModelTarget target = new ModelTarget();
    int a = 0;
    private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
            int meta = world.getBlockMetadata(x, y, z);
            GL11.glPushMatrix();
            GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
            GL11.glPopMatrix();
    }
    public static final void drawLine(double e , double x, double y , double z , double x2 , double y2 , double z2){
      	//glLineWidth((float)e);
    	
      	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
    	glBegin(GL_LINES);
    	glVertex3d(x,y,z);
    	glVertex3d(x2,y2,z2);
    	glEnd();
    }
    
    public static void drawBox(double lenX , double lenY , double lenZ) {
	    RenderHelper.disableStandardItemLighting();
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160F, 160F);
	    GL11.glDisable(GL11.GL_ALPHA_TEST);
	    GL11.glDepthMask(false);
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(0, 0, 0);
	    tessellator.addVertex(0, lenY, 0);
	    tessellator.addVertex(lenX, 0, 0);
	    tessellator.addVertex(lenX, lenY, 0);
	    tessellator.addVertex(lenX, 0, lenZ);
	    tessellator.addVertex(lenX, lenY, lenZ);
	    tessellator.addVertex(0, 0, lenZ);
	    tessellator.addVertex(0, lenY, lenZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(lenX, lenY, 0);
	    tessellator.addVertex(lenX, 0, 0);
	    tessellator.addVertex(0, lenY, 0);
	    tessellator.addVertex(0, 0, 0);
	    tessellator.addVertex(0, lenY, lenZ);
	    tessellator.addVertex(0, 0, lenZ);
	    tessellator.addVertex(lenX, lenY, lenZ);
	    tessellator.addVertex(lenX, 0, lenZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(0, lenY, 0);
	    tessellator.addVertex(lenX, lenY, 0);
	    tessellator.addVertex(lenX, lenY, lenZ);
	    tessellator.addVertex(0, lenY, lenZ);
	    tessellator.addVertex(0, lenY, 0);
	    tessellator.addVertex(0, lenY, lenZ);
	    tessellator.addVertex(lenX, lenY, lenZ);
	    tessellator.addVertex(lenX, lenY, 0);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(0, 0, 0);
	    tessellator.addVertex(lenX, 0, 0);
	    tessellator.addVertex(lenX, 0, lenZ);
	    tessellator.addVertex(0, 0, lenZ);
	    tessellator.addVertex(0, 0, 0);
	    tessellator.addVertex(0, 0, lenZ);
	    tessellator.addVertex(lenX, 0, lenZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(0, 0, 0);
	    tessellator.addVertex(0, lenY, 0);
	    tessellator.addVertex(0, 0, lenZ);
	    tessellator.addVertex(0, lenY, lenZ);
	    tessellator.addVertex(lenX, 0, lenZ);
	    tessellator.addVertex(lenX, lenY, lenZ);
	    tessellator.addVertex(lenX, 0, 0);
	    tessellator.addVertex(lenX, lenY, 0);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(0, lenY, lenZ);
	    tessellator.addVertex(0, 0, lenZ);
	    tessellator.addVertex(0, lenY, 0);
	    tessellator.addVertex(0, 0, 0);
	    tessellator.addVertex(lenX, lenY, 0);
	    tessellator.addVertex(lenX, 0, 0);
	    tessellator.addVertex(lenX, lenY, lenZ);
	    tessellator.addVertex(lenX, 0, lenZ);
	    tessellator.draw();
	    RenderHelper.enableStandardItemLighting();
	    GL11.glEnable(GL11.GL_ALPHA_TEST);
	    GL11.glDepthMask(true);
	    GL11.glDisable(GL11.GL_BLEND);
	}
	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y,
			double z, float var8) {

		GL11.glPushMatrix();
        ResourceLocation textures = new ResourceLocation("advtech" ,"textures/misc/laser.png");
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        //target.render(null,  0, 0, -0.1F, 0, 0, 0.0625f);
		GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.375F, (float) y + 1F, (float) z + 0.375F);
        GL11.glColor4f(0.9f, 0.1f,0.1f,0.5f);
        drawBox(0.25,5,0.25);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 0,0,1f);
        GL11.glTranslatef((float) x + 0.4375F, (float) y + 1F, (float) z + 0.4375F);
        drawBox(0.125,5,0.125);
		GL11.glPopMatrix();

	}
	
	private static void lightColor(int r , int g , int b){
		Tessellator tess = Tessellator.instance;
        tess.setColorRGBA(r,g,b,255);
	}
    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
        Tessellator tess = Tessellator.instance;
        //float brightness = block.getBlockBrightness(world, i, j, k);
        //As of MC 1.7+ block.getBlockBrightness() has become block.getLightValue():
        float brightness = block.getLightValue(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }
	
}
