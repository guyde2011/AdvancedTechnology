package com.guyde.nano.main

import net.minecraftforge.common.IExtendedEntityProperties
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase

object ExtendedProps{ 
  
  class PlayerPropRender(player : EntityPlayer) extends IExtendedEntityProperties{
    
    var perspective : EntityLivingBase = player

    

    
    def init(ent : Entity,world : World): Unit = {
    
    }
    
    def loadNBTData(tag : NBTTagCompound): Unit = {
    
    }
    
    def saveNBTData(tag : NBTTagCompound): Unit = {
    
    }
    
  }
  
  val Render_Name = "AdvTechRenderProps"
  
  def watchRenderFor(player : EntityPlayer){
    player.registerExtendedProperties(Render_Name, new PlayerPropRender(player));
  }
  
  def getRenderFor(player : EntityPlayer) : PlayerPropRender = {
    return player.getExtendedProperties(Render_Name).asInstanceOf[PlayerPropRender]
  }

}