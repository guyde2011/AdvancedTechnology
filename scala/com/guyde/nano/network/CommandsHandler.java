package com.guyde.nano.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommandsHandler implements IMessageHandler<ClientToServerMessage, IMessage> {
		@Override
		public IMessage onMessage(ClientToServerMessage message, MessageContext ctx) {
			message.command.runCommand(ctx);
			return null;
		}
}
