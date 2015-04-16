package com.guyde.nano.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public abstract class MessageCommand {
	
	public abstract MessageCommand read(ByteBuf buf);
	public abstract void write(ByteBuf buf);
	public abstract void runCommand(MessageContext ctx);
	
	public final void init(){
		PacketCommandsRegistry.instance.registerMessageCommand(getClass());
	}
	public EntityPlayerMP getPlayer(String name){
		for (EntityPlayerMP p : (List<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList){
			if (p.getGameProfile().getName()==name){
				return p;
			}
		}
		return null;
	}
}
