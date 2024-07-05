package com.nikotokiller.enigmamagica.recipe;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EnigmaMagica.MODID);

    public static final RegistryObject<RecipeSerializer<GemCutterRecipe>> GEM_CUTTER_SERIALIZER =
            RECIPE_SERIALIZER_DEFERRED_REGISTER.register("gem_cutting", () -> GemCutterRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        RECIPE_SERIALIZER_DEFERRED_REGISTER.register(eventBus);
    }

}
