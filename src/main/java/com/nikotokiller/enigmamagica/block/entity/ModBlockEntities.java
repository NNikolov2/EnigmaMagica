package com.nikotokiller.enigmamagica.block.entity;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EnigmaMagica.MODID);

    public static final RegistryObject<BlockEntityType<GemCutterEntity>> GEM_CUTTER_ENTITY =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("gem_cutter_entity", () ->
                    BlockEntityType.Builder.of(GemCutterEntity::new,
                            ModBlocks.GEM_CUTTER_TOP.get(),
                            ModBlocks.GEM_CUTTER_MIDDLE.get(),
                            ModBlocks.GEM_CUTTER_BOTTOM.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }

}
