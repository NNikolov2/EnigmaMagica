package com.nikotokiller.enigmamagica.worldgen;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> WARPSTONE_ORE_PLACED_KEY = registerKey("warpstone_ore_placed");

    public static void boostrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureHolderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

       register(context, WARPSTONE_ORE_PLACED_KEY, configuredFeatureHolderGetter.getOrThrow(ModConfiguredFeatures.OVERWORLD_WARPSTONE_ORE_KEY), ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-10))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(EnigmaMagica.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifierList){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifierList)));
    }

}
