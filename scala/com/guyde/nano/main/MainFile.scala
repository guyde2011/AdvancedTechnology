package com.guyde.nano.main 

import org.apache.logging.log4j.LogManager
import com.guyde.nano.block._
import com.guyde.nano.block.FluidWitherEssence
import com.guyde.nano.block.WitherJuice
import com.guyde.nano.item._
import com.guyde.nano.craft._
import cofh.api.modhelpers.ThermalExpansionHelper
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.client.model.ModelBiped
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidStack
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.Mod.Instance
import net.minecraft.block.material.Material
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.client.registry.ClientRegistry
import com.guyde.nano.block.TileEntityLink
import com.guyde.nano.block.EntityTarget
import com.guyde.nano.render.TargetRenderer
import cpw.mods.fml.relauncher.Side
import com.guyde.nano.entity.EntityExplodeBug
import cpw.mods.fml.common.registry.EntityRegistry
import com.guyde.nano.render.ModelExplodeBug
import com.guyde.nano.render.RenderExplodeBug
import net.minecraftforge.common.MinecraftForge
import com.guyde.nano.render.RenderLaserGrav
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.common.network.NetworkRegistry
import com.guyde.nano.network.CommandsHandler
import com.guyde.nano.network.ServerToClientMessage
import com.guyde.nano.network.ServerCommandsHandler
import com.guyde.nano.network.ClientToServerMessage
import com.guyde.nano.network.PacketCommandsRegistry
import com.guyde.nano.main.ClientProxy
import com.guyde.nano.network.MessageCommand
import com.guyde.nano.main.GuiBook
import com.guyde.nano.render.RenderLaserGrav2


  
  class CommonProxy{
    def getArmorModel(id : Int , glass : Boolean , Chest : Boolean) : ModelBiped = {
      return null
    }
    def registerRenderers(){}
    def registerEntities(){
      EntityRegistry.registerModEntity(classOf[EntityExplodeBug], "ExplodeBug", 0,  AdvTechMod.instance, 120, 3, true);
    }
    
  }

  class ClientProxy extends CommonProxy{
    KeyBinds
    override def registerRenderers(){
      ClientRegistry.bindTileEntitySpecialRenderer(classOf[EntityTarget], new TargetRenderer())
      ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityLaserGrav], new RenderLaserGrav())
      ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityLaserGrav2], new RenderLaserGrav2())
      RenderingRegistry.registerEntityRenderingHandler(classOf[EntityExplodeBug], new RenderExplodeBug(new ModelExplodeBug(),0.125f))
    }
  }

  class ServerProxy extends CommonProxy{
  
  }
  
@Mod(modid = "advtech", name = "TE4 Addon:Advanced Technology", version = "0.0.1 DEV" , dependencies = "required-after:ThermalExpansion;required-after:ThermalFoundation" , modLanguage="scala")
object AdvTechMod{
  @Instance("advtech")
  val instance = AdvTechMod
  
  val logger = LogManager.getLogger("Advanced Technology")
  var networkWrapper : SimpleNetworkWrapper = null
  @SidedProxy(clientSide = "com.guyde.nano.main.ClientProxy", serverSide = "com.guyde.nano.main.ServerProxy")
  object proxy extends CommonProxy;



  var Proxy : CommonProxy = null
  @Mod.EventHandler
  def preInit(e: FMLPreInitializationEvent) {
    
    logger.info("Successfully Pre-Initiallized")

  }
  
  class Safer() extends NanoBasicItem("safer");
  val enth = Modifiers.speedUpgrade 
  val Channel = "AdvTech_Msgs"
  val dim_ingot = new NanoBasicItem("dim_ingot","nether_enderium")
  val safe_enth = new NanoBasicItem("rs_emerald_safer" , "safer_stablized_gem")
  val enriched_ender = Modifiers.SilkTouchUpgrade
  val safe_emerald = new NanoBasicItem("safer_emerald")
  val enth_coil = new NanoBasicItem("gem_coil")
  val stone_stick = new NanoBasicItem("stone_stick")
  val safer = new Safer()
  val Link = new BlockLink()
  val binder = new Binder()
  val dmg_up = Modifiers.damageUpgrade
  val bug = new BugItem()
  var witherJuice : Fluid  = null
  var witherJuiceBlock : FluidWitherEssence = null
  var wl_signalium = new NanoBasicItem("wl_signalium")
  @Mod.EventHandler
  def init(e: FMLInitializationEvent) {
        proxy.registerRenderers()
    Tools.AdvancedPickaxe
    Tools.AdvancedPickaxe.reg
    Tools.AdvancedAxe
    Tools.AdvancedAxe.reg
    Tools.AdvancedShovel
    Tools.AdvancedShovel.reg
    Tools.AdvancedSword
    Tools.AdvancedSword.reg
    binder.reg()
    dim_ingot.reg
    enriched_ender.reg
    enth.reg
    safe_enth.reg
    safe_emerald.reg
    stone_stick.reg
    safer.reg
    enth_coil.reg
    new BlockTarget()
    dmg_up.reg
    bug.reg
    NetworkRegistry.INSTANCE.registerGuiHandler(instance ,new GuiHandler())
    NanoManual.reg
    new BlockLaserGrav()
    new BlockLaserGrav2()
    TileEntity.addMapping(classOf[TileEntityLink], "advtech:link_terminal")
    TileEntity.addMapping(classOf[TileEntityLaserGrav],"advtech:laser_grav")
    TileEntity.addMapping(classOf[TileEntityLaserGrav2],"advtech:laser_grav2")
        wl_signalium.reg
    ItemRegistry.load
    Proxy = proxy

    logger.info("Successfully Initiallized")
    GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.getItemByName("rs_emerald")), ItemRegistry.getItemByName("rs_emerald_safer"))
    GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.getItemByName("safer_emerald")), ItemRegistry.getItemByName("safer"),Items.emerald)
    GameRegistry.addRecipe(new ToolRecipe(Tools.pt_pick))
    GameRegistry.addRecipe(new ToolRecipe(Tools.pt_axe))
    GameRegistry.addRecipe(new ToolRecipe(Tools.pt_shovel))
    GameRegistry.addRecipe(new ToolRecipe(Tools.pt_sword))
 witherJuice = new WitherJuice()
new CommonProxy().registerEntities()
   if (FMLCommonHandler.instance().getSide==Side.CLIENT){
     new ClientProxy().registerRenderers() 
   }
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Channel);
      networkWrapper.registerMessage(classOf[CommandsHandler], classOf[ClientToServerMessage], 0, Side.SERVER);
      networkWrapper.registerMessage(classOf[ServerCommandsHandler], classOf[ServerToClientMessage], 0, Side.CLIENT);
   
          witherJuiceBlock = new FluidWitherEssence(witherJuice.asInstanceOf[Fluid], Material.water).setBlockName("witherJuice").asInstanceOf[FluidWitherEssence];
          GameRegistry.registerBlock(witherJuiceBlock, "wither_juice");
           




    GameRegistry.addRecipe(new UpgradeToolRecipe())
    GameRegistry.addRecipe(new ModifierToolRecipe())
    FMLCommonHandler.instance().bus().register(new Handler());
    MinecraftForge.EVENT_BUS.register(new Handler());
    //MinecraftForge.EVENT_BUS.register(new GuiBug);

  }
  
  def sendCommand(cmd : MessageCommand ){
    if (FMLCommonHandler.instance().getSide() == Side.CLIENT){
      networkWrapper.sendToServer(new ClientToServerMessage(cmd));
    } else {
      networkWrapper.sendToAll(new ServerToClientMessage(cmd));
    }
  }



  @Mod.EventHandler
  def postInit(e: FMLPostInitializationEvent) {
    if (Loader.isModLoaded("NotEnoughItems")){
      logger.info("Successfully Post-Initiallized NEI Intergration")
    }
    logger.info("Successfully Post-Initiallized")
    val fluid_redstone : Fluid = Class.forName("cofh.thermalfoundation.fluid.TFFluids").getField("fluidRedstone").get(null).asInstanceOf[Fluid]
    val fluid_ender : Fluid = Class.forName("cofh.thermalfoundation.fluid.TFFluids").getField("fluidEnder").get(null).asInstanceOf[Fluid]
    val tf_mat = GameRegistry.findItem("ThermalFoundation", "material")
    GameRegistry.addRecipe(new ItemStack(stone_stick,2),"a","a",new java.lang.Character('a') , new ItemStack(Blocks.stone))
        GameRegistry.addRecipe(new ItemStack(stone_stick,2),"a","a",new java.lang.Character('a') , new ItemStack(Blocks.cobblestone))
        ThermalExpansionHelper.addTransposerFill(10000, new ItemStack(ItemRegistry.getItemByName("safer_emerald")), new ItemStack(ItemRegistry.getItemByName("rs_emerald_safer")), new FluidStack(fluid_redstone,8000), false)
        ThermalExpansionHelper.addTransposerFill(10000, new ItemStack(Items.quartz), new ItemStack(dmg_up), new FluidStack(fluid_redstone,4000), false)
        ThermalExpansionHelper.addTransposerFill(10000, new ItemStack(tf_mat,1,75), new ItemStack(enriched_ender), new FluidStack(fluid_ender,1500), false)
            ThermalExpansionHelper.addTransposerFill(10000, new ItemStack(tf_mat,1,74), new ItemStack(wl_signalium), new FluidStack(fluid_ender , 250), false)
        ThermalExpansionHelper.addCrucibleRecipe(25000, new ItemStack(Items.nether_star), new FluidStack(witherJuice,1000))
        ThermalExpansionHelper.addTransposerFill(12500, new ItemStack(enriched_ender), new ItemStack(dim_ingot), new FluidStack(witherJuice,250),false)
        ThermalExpansionHelper.addCrucibleRecipe(100000, new ItemStack(Items.skull , 1 , 1), new FluidStack(witherJuice,150))
    GameRegistry.addRecipe(new ItemStack(safer),"aba","ada","aba",new java.lang.Character('d'), new ItemStack(tf_mat , 1 , 76), new java.lang.Character('a'),new ItemStack(tf_mat , 1 , 74),new java.lang.Character('b'),new ItemStack(tf_mat , 1 , 71));
    GameRegistry.addRecipe(new ItemStack(enth_coil),"a  "," b ","  a",new java.lang.Character('b'),new ItemStack(enth),new java.lang.Character('a'),new ItemStack(Items.redstone))
    GameRegistry.addRecipe(new ItemStack(binder)," a ","a a", " ab",new java.lang.Character('b'),new ItemStack(dim_ingot),new java.lang.Character('a'),new ItemStack(wl_signalium))
    GameRegistry.addRecipe(new ItemStack(Link),"cbc","bab", "cbc",new java.lang.Character('b'),new ItemStack(tf_mat , 1 , 76),new java.lang.Character('a'),new ItemStack(wl_signalium),new java.lang.Character('c'),new ItemStack(Blocks.obsidian))

  }
  
}
