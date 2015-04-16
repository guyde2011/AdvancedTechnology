package com.guyde.nano.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;



public class ClientToServerMessage implements IMessage {
	public MessageCommand command;
	public ClientToServerMessage() {}
	

	
	public ClientToServerMessage(MessageCommand cmd) {
		command = cmd;
	}
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(PacketCommandsRegistry.instance.getMessageID(command));
		command.write(buf);
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		int ID = buf.readInt();
		try {
			command = PacketCommandsRegistry.instance.getMessageFromID(ID).read(buf);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

