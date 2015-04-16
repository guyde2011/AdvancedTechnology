package com.guyde.nano.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * ModelLaserGrav - Guyde2011
 * Created using Tabula 4.1.1
 */
public class ModelLaserGrav extends ModelBase {
    public double[] modelScale = new double[] { 0.5D, 0.5D, 0.5D };
    public ModelRenderer Pillar1;
    public ModelRenderer Pillar2;
    public ModelRenderer Pillar3;
    public ModelRenderer Pillar4;
    public ModelRenderer Plain;
    public ModelRenderer Laser;

    public ModelLaserGrav() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Pillar2 = new ModelRenderer(this, 0, 0);
        this.Pillar2.setRotationPoint(-2.5F, 6.5F, 2.0F);
        this.Pillar2.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Pillar4 = new ModelRenderer(this, 0, 0);
        this.Pillar4.setRotationPoint(-2.5F, 6.5F, -2.5F);
        this.Pillar4.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Pillar3 = new ModelRenderer(this, 0, 0);
        this.Pillar3.setRotationPoint(2.0F, 6.5F, -2.5F);
        this.Pillar3.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
        this.Plain = new ModelRenderer(this, 0, 0);
        this.Plain.setRotationPoint(-4.0F, 11.5F, -4.0F);
        this.Plain.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
        this.Laser = new ModelRenderer(this, 0, 17);
        this.Laser.setRotationPoint(-2.0F, 11.0F, -2.0F);
        this.Laser.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
        this.Pillar1 = new ModelRenderer(this, 0, 0);
        this.Pillar1.setRotationPoint(2.0F, 6.5F, 2.0F);
        this.Pillar1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GL11.glPushMatrix();
        GL11.glScaled(1D / modelScale[0], 1D / modelScale[1], 1D / modelScale[2]);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Pillar2.offsetX, this.Pillar2.offsetY, this.Pillar2.offsetZ);
        GL11.glTranslatef(this.Pillar2.rotationPointX * f5, this.Pillar2.rotationPointY * f5, this.Pillar2.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Pillar2.offsetX, -this.Pillar2.offsetY, -this.Pillar2.offsetZ);
        GL11.glTranslatef(-this.Pillar2.rotationPointX * f5, -this.Pillar2.rotationPointY * f5, -this.Pillar2.rotationPointZ * f5);
        this.Pillar2.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Pillar4.offsetX, this.Pillar4.offsetY, this.Pillar4.offsetZ);
        GL11.glTranslatef(this.Pillar4.rotationPointX * f5, this.Pillar4.rotationPointY * f5, this.Pillar4.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Pillar4.offsetX, -this.Pillar4.offsetY, -this.Pillar4.offsetZ);
        GL11.glTranslatef(-this.Pillar4.rotationPointX * f5, -this.Pillar4.rotationPointY * f5, -this.Pillar4.rotationPointZ * f5);
        this.Pillar4.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Pillar3.offsetX, this.Pillar3.offsetY, this.Pillar3.offsetZ);
        GL11.glTranslatef(this.Pillar3.rotationPointX * f5, this.Pillar3.rotationPointY * f5, this.Pillar3.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Pillar3.offsetX, -this.Pillar3.offsetY, -this.Pillar3.offsetZ);
        GL11.glTranslatef(-this.Pillar3.rotationPointX * f5, -this.Pillar3.rotationPointY * f5, -this.Pillar3.rotationPointZ * f5);
        this.Pillar3.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Plain.offsetX, this.Plain.offsetY, this.Plain.offsetZ);
        GL11.glTranslatef(this.Plain.rotationPointX * f5, this.Plain.rotationPointY * f5, this.Plain.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Plain.offsetX, -this.Plain.offsetY, -this.Plain.offsetZ);
        GL11.glTranslatef(-this.Plain.rotationPointX * f5, -this.Plain.rotationPointY * f5, -this.Plain.rotationPointZ * f5);
        this.Plain.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Laser.offsetX, this.Laser.offsetY, this.Laser.offsetZ);
        GL11.glTranslatef(this.Laser.rotationPointX * f5, this.Laser.rotationPointY * f5, this.Laser.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Laser.offsetX, -this.Laser.offsetY, -this.Laser.offsetZ);
        GL11.glTranslatef(-this.Laser.rotationPointX * f5, -this.Laser.rotationPointY * f5, -this.Laser.rotationPointZ * f5);
        this.Laser.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Pillar1.offsetX, this.Pillar1.offsetY, this.Pillar1.offsetZ);
        GL11.glTranslatef(this.Pillar1.rotationPointX * f5, this.Pillar1.rotationPointY * f5, this.Pillar1.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Pillar1.offsetX, -this.Pillar1.offsetY, -this.Pillar1.offsetZ);
        GL11.glTranslatef(-this.Pillar1.rotationPointX * f5, -this.Pillar1.rotationPointY * f5, -this.Pillar1.rotationPointZ * f5);
        this.Pillar1.render(f5);
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
