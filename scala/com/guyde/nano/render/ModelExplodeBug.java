package com.guyde.nano.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * ModelExplodeBug - Guyde2011
 * Created using Tabula 4.1.1
 */
public class ModelExplodeBug extends ModelBase {
    public double[] modelScale = new double[] { 0.5D, 0.5D, 0.5D };
    public ModelRenderer Bug;
    public ModelRenderer Tail;

    public ModelExplodeBug() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.Tail = new ModelRenderer(this, 0, 6);
        this.Tail.setRotationPoint(-1.9F, 11.0F, -0.8F);
        this.Tail.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.Bug = new ModelRenderer(this, 0, 0);
        this.Bug.setRotationPoint(-1.4F, 11.0F, -1.3F);
        this.Bug.addBox(0.0F, 0.0F, 0.0F, 5, 2, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GL11.glPushMatrix();
        GL11.glScaled(1D / modelScale[0], 1D / modelScale[1], 1D / modelScale[2]);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Tail.offsetX, this.Tail.offsetY, this.Tail.offsetZ);
        GL11.glTranslatef(this.Tail.rotationPointX * f5, this.Tail.rotationPointY * f5, this.Tail.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Tail.offsetX, -this.Tail.offsetY, -this.Tail.offsetZ);
        GL11.glTranslatef(-this.Tail.rotationPointX * f5, -this.Tail.rotationPointY * f5, -this.Tail.rotationPointZ * f5);
        this.Tail.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Bug.offsetX, this.Bug.offsetY, this.Bug.offsetZ);
        GL11.glTranslatef(this.Bug.rotationPointX * f5, this.Bug.rotationPointY * f5, this.Bug.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Bug.offsetX, -this.Bug.offsetY, -this.Bug.offsetZ);
        GL11.glTranslatef(-this.Bug.rotationPointX * f5, -this.Bug.rotationPointY * f5, -this.Bug.rotationPointZ * f5);
        this.Bug.render(f5);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
