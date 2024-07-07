package com.nikotokiller.enigmamagica;

import com.mojang.logging.LogUtils;
import com.nikotokiller.enigmamagica.block.ModBlocks;
import com.nikotokiller.enigmamagica.block.entity.ModBlockEntities;
import com.nikotokiller.enigmamagica.effect.ModEffects;
import com.nikotokiller.enigmamagica.entity.ModEntities;
import com.nikotokiller.enigmamagica.entity.client.AcidSpitRenderer;
import com.nikotokiller.enigmamagica.entity.client.DesertCentipedeRenderer;
import com.nikotokiller.enigmamagica.item.ModCreativeModTabs;
import com.nikotokiller.enigmamagica.item.ModItems;
import com.nikotokiller.enigmamagica.recipe.ModRecipes;
import com.nikotokiller.enigmamagica.screen.GemCutterScreen;
import com.nikotokiller.enigmamagica.screen.GemFusionScreen;
import com.nikotokiller.enigmamagica.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnigmaMagica.MODID)
public class EnigmaMagica
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "enigmamagica";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public EnigmaMagica()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.DESERT_CENTIPEDE.get(), DesertCentipedeRenderer::new);
            EntityRenderers.register(ModEntities.ACID_SPIT_PROJECTILE.get(), AcidSpitRenderer::new);
            MenuScreens.register(ModMenuTypes.GEM_CUTTER_MENU.get(), GemCutterScreen::new);
            MenuScreens.register(ModMenuTypes.GEM_FUSION_MENU.get(), GemFusionScreen::new);
        }
    }
}
