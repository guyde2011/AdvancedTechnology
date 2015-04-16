package com.guyde.nano.render

import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.client.model.ModelBase
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.client.Minecraft

class RenderExplodeBug(model : ModelBase , shadowSize : Float) extends RenderLiving(model , shadowSize){
  override def getEntityTexture(ent : Entity) : ResourceLocation = {
    return new ResourceLocation("advtech:textures/entity/bug.png")
  }
  override def preRenderCallback(ent : EntityLivingBase ,f  : Float )
    {

     //  Minecraft.getMinecraft().renderViewEntity = ent

    }
}