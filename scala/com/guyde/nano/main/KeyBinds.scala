package com.guyde.nano.main

import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard
import cpw.mods.fml.client.registry.ClientRegistry


object KeyBinds {
  val RenderExit = new KeyBinding("key.rend", Keyboard.KEY_P, "key.categories.advtech")
  ClientRegistry.registerKeyBinding(RenderExit)
}