package com.guyde.nano.network

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.network.simpleimpl.MessageContext
import cpw.mods.fml.common.network.ByteBufUtils
import net.minecraftforge.common.util.FakePlayer
import net.minecraft.world.WorldServer
import net.minecraft.client.Minecraft

class PerspectiveSummonCommand() extends MessageCommand() {
  var pName : String = null
  def read(buf : ByteBuf): MessageCommand = {
    pName = ByteBufUtils.readUTF8String(buf)
    return this
  }
  def runCommand(context : MessageContext): Unit = {
    var player = getPlayer(pName)
    var fake = new FakePlayer(player.worldObj.asInstanceOf[WorldServer],player.getGameProfile)
    fake.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch)
    player.worldObj.spawnEntityInWorld(fake)
    println("meow")
  }
  def write(buf : ByteBuf): Unit = {
    ByteBufUtils.writeUTF8String(buf, Minecraft.getMinecraft.thePlayer.getDisplayName)
  }

}