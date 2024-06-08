package com.nikotokiller.enigmamagica.event;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.entity.client.DesertCentipedeModel;
import com.nikotokiller.enigmamagica.entity.client.ModModelLayers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EnigmaMagica.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusClientEvents {

    @SubscribeEvent
   public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
       event.registerLayerDefinition(ModModelLayers.DESERT_CENTIPEDE_LAYER, DesertCentipedeModel::createBodyLayer);
   }

}
