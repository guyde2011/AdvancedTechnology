package com.guyde.nano.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ServerCommandsHandler implements IMessageHandler<ServerToClientMessage, IMessage> {
		@Override
		public IMessage onMessage(ServerToClientMessage message, MessageContext ctx) {
			message.command.runCommand(ctx);
			return null;
		}
}
