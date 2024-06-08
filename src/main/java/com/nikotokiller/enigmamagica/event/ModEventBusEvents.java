package com.nikotokiller.enigmamagica.event;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.entity.ModEntities;
import com.nikotokiller.enigmamagica.entity.custom.DesertCentipedeEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EnigmaMagica.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.DESERT_CENTIPEDE.get(), DesertCentipedeEntity.createAttributes().build());
    }

}
