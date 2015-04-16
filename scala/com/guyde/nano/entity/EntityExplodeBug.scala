package com.guyde.nano.entity

import net.minecraft.entity.EntityLiving
import net.minecraft.world.World
import net.minecraft.util.DamageSource
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.entity.Entity
import net.minecraft.entity.IEntityOwnable
import net.minecraft.util.ResourceLocation


class EntityExplodeBug(world : World , Owner : String) extends EntityLiving(world){
  var owner = Owner
  def this(world : World ){
    this(world,"")
    this.setSize(0.2f, 0.1f)
  }
  
 // override def isMovementBlocked() : Boolean = {
    

 // }
  
  override def onLivingUpdate(){
    super.onLivingUpdate()
    if ((this.lastTickPosX-1.5>this.posX && this.lastTickPosX<this.posX) || (this.lastTickPosX-1.5<this.posX && this.posX<this.lastTickPosX)){
      this.setPosition(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ)
    }
  }
  
  
  override def collideWithEntity(ent : Entity){
    if(ent.isInstanceOf[EntityLiving]){
      val live = ent.asInstanceOf[EntityLiving]
      if (live.isInstanceOf[IEntityOwnable]){
        val ent_own = live.asInstanceOf[IEntityOwnable].getOwner
        if (ent_own!=null && ent_own.isInstanceOf[EntityPlayer]){
          this.onCollideWithPlayer(ent_own.asInstanceOf[EntityPlayer])
        } else {
          Explode
        }
      } else {
        Explode
      }
    }
    if (ent.isInstanceOf[EntityPlayer]){
      onCollideWithPlayer(ent.asInstanceOf[EntityPlayer])
    }
  }
  override def attackEntityFrom(src : DamageSource , amount : Float) : Boolean =
  {
    Explode
    return true
  }
  

  override def onCollideWithPlayer(player : EntityPlayer){
    if ( !player.getDisplayName().equals(owner)){
      Explode
    }
  }
  
  def Explode(){
    if (!worldObj.isRemote && !this.isDead){
      this.setDead()
      world.createExplosion(this, this.posX, posY, posZ, 2f, true)
    }
  }
  
  override def writeEntityToNBT(tag : NBTTagCompound){
    super.writeEntityToNBT(tag)
    tag.setString("owner", this.owner)
  }
  
  override def readEntityFromNBT(tag : NBTTagCompound){
    super.readEntityFromNBT(tag)
    owner = tag.getString("owner")
  }
  

  


}