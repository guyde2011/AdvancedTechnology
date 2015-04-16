package com.guyde.nano.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guyde.nano.main.AdvTechMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketCommandsRegistry {
	private List<Class> commands = new ArrayList<Class>();
	private Map<Class,Integer> commandsList = new HashMap<Class,Integer>();
	public final static PacketCommandsRegistry instance = new PacketCommandsRegistry();

    
	public void registerMessageCommand(Class cmd){
		commands.add(cmd);
		commandsList.put(cmd, commands.size()-1);
	}
	
	public int getMessageID(MessageCommand cmd){
		return commandsList.get(cmd.getClass());
	}
	
	public MessageCommand getMessageFromID(int id) throws InstantiationException, IllegalAccessException{
		return (MessageCommand) commands.get(id).newInstance();
	}
	
	public static void sendClientMessage(MessageCommand msg){
		AdvTechMod.instance().networkWrapper().sendToServer(new ClientToServerMessage(msg));
	}
	
	public static void sendServerMessage(MessageCommand msg , EntityPlayer player){
		AdvTechMod.instance().networkWrapper().sendTo(new ServerToClientMessage(msg), (EntityPlayerMP) player);
	}
	
	public static void sendPublicServerMessage(MessageCommand msg){
		AdvTechMod.instance().networkWrapper().sendToAll(new ServerToClientMessage(msg));
	}


	
}
