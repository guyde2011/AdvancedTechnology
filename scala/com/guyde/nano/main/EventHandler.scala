package com.guyde.nano.main

import java.util.Arrays
import scala.collection.JavaConversions._
import com.guyde.nano.craft.PartableToolHelper
import com.guyde.nano.item.AdvTool
import com.guyde.nano.item.ItemRegistry
import com.guyde.nano.item.ListConverter
import com.guyde.nano.item.ModifierRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraftforge.event.entity.player.EntityInteractEvent
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing
import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLiving
import com.guyde.nano.network.PacketCommandsRegistry
import com.guyde.nano.network.PerspectiveSummonCommand
import net.minecraftforge.common.util.FakePlayer
import net.minecraft.world.WorldServer
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import org.lwjgl.input.Keyboard


class Handler {
  @SubscribeEvent
  def onItemCrafted(event : ItemCraftedEvent){
    if (Item.getIdFromItem(event.crafting.getItem())==Item.getIdFromItem(AdvTechMod.enth)){
      event.player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.getItemByName("safer")))
    }
  }
  @SubscribeEvent
  def blockDrops(event : HarvestDropsEvent){
    var evDrops = Array[ItemStack]() ++ event.drops
    if (event.harvester != null) {
     var item = event.harvester.getHeldItem()
      if (item.getItem.isInstanceOf[AdvTool]){
        var bool = true
        
       PartableToolHelper.getData(item).getCompoundTag("modifiers").func_150296_c().foreach {  a=>{
          var cur = ModifierRegistry.getModifier(a.asInstanceOf[String])
         bool = bool &&  cur.normalDrops()
        if (!cur.normalDrops()){
            evDrops = cur.customDrops(evDrops , Array[ItemStack]() ++ event.drops , item) 
         }
       
        event.drops.clear()
        ListConverter.addAll(event.drops, Arrays.asList(evDrops))
       }
       }
    }}
  }
  
  @SubscribeEvent
  def entityClick(event : EntityInteractEvent ){
      if(event.entityPlayer.getHeldItem()!=null && event.entityPlayer.getHeldItem().getItem()==Items.stick){
        if (event.target.isInstanceOf[com.guyde.nano.entity.EntityExplodeBug]){
          event.target.setDead()
          event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(AdvTechMod.bug))
        }
      }
      if (event.entityPlayer.getHeldItem()==null && event.target.isInstanceOf[com.guyde.nano.entity.EntityExplodeBug] && event.entityPlayer.isSneaking){
        ExtendedProps.getRenderFor(event.entityPlayer).perspective=event.target.asInstanceOf[EntityLiving]
    //    PacketCommandsRegistry.sendClientMessage(new PerspectiveSummonCommand())

      }

  }


  @SubscribeEvent
  def onKeyPressed(event : KeyInputEvent){
    if (KeyBinds.RenderExit.isPressed()){
      ExtendedProps.getRenderFor(Minecraft.getMinecraft().thePlayer).perspective = Minecraft.getMinecraft().thePlayer
    }
    else if(Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode)){
      event.setCanceled(true)
      ExtendedProps.getRenderFor(Minecraft.getMinecraft().thePlayer).perspective.moveEntityWithHeading(0.025f, 0.5f)
    }
        else{
      event.setCanceled(true)

    }
  }
  
  @SubscribeEvent
  def onEntityConstruct(event : EntityConstructing){
    if (event.entity.isInstanceOf[EntityPlayer]){
      ExtendedProps.watchRenderFor(event.entity.asInstanceOf[EntityPlayer])
    }
  }
}