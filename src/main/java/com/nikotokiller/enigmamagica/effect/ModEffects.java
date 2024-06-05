package com.nikotokiller.enigmamagica.effect;

import com.mojang.blaze3d.shaders.Effect;
import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEffects {


    // Deferred Register for Effects
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EnigmaMagica.MODID);

    public static final RegistryObject<MobEffect> PHARAOHS_CURSE = EFFECTS.register("pharaohs_curse",
            () -> new PharaohsCurseEffect(MobEffectCategory.HARMFUL, 3215698));

    // Register the Deferred Register to the event bus
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

}
