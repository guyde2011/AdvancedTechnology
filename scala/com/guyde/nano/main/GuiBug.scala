package com.guyde.nano.main

import net.minecraftforge.client.event.RenderGameOverlayEvent
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.eventhandler.EventPriority
import net.minecraft.client.gui.Gui
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import org.lwjgl.opengl.GL11

class GuiBug extends Gui{
  
  @SubscribeEvent(priority = EventPriority.NORMAL)
  def onRenderExperienceBar(event : RenderGameOverlayEvent){
          GL11.glColor3f(1f,0,0)   
  if (KeyBinds.RenderExit.isPressed()){
      ExtendedProps.getRenderFor(Minecraft.getMinecraft().thePlayer).perspective = Minecraft.getMinecraft().thePlayer
    }
    if(event.isCancelable() || event.`type` != ElementType.EXPERIENCE){
      return
    }
    if (ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective!=Minecraft.getMinecraft.renderViewEntity){
      Minecraft.getMinecraft.renderViewEntity=ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective
    }
    if(ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective!=Minecraft.getMinecraft.thePlayer){
      ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective.motionX = Minecraft.getMinecraft.thePlayer.motionX 
      ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective.motionY = Minecraft.getMinecraft.thePlayer.motionY 
      ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective.motionZ = Minecraft.getMinecraft.thePlayer.motionZ 
      Minecraft.getMinecraft.thePlayer.motionX = 0
      Minecraft.getMinecraft.thePlayer.motionY = 0
      Minecraft.getMinecraft.thePlayer.motionZ = 0 
      ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective.rotationPitch = Minecraft.getMinecraft.thePlayer.rotationPitch
      ExtendedProps.getRenderFor(Minecraft.getMinecraft.thePlayer).perspective.rotationYaw = Minecraft.getMinecraft.thePlayer.rotationYaw

    }
    
  }
  
}