package com.nikotokiller.enigmamagica.entity;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.entity.custom.AcidSpitProjectileEntity;
import com.nikotokiller.enigmamagica.entity.custom.DesertCentipedeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EnigmaMagica.MODID);

    public static final RegistryObject<EntityType<DesertCentipedeEntity>> DESERT_CENTIPEDE =
            ENTITY_TYPE_DEFERRED_REGISTER.register("desert_centipede", () -> EntityType.Builder.of(DesertCentipedeEntity::new, MobCategory.MONSTER).sized(1.3f, 3f).build("desert_centipede"));

    public static final RegistryObject<EntityType<AcidSpitProjectileEntity>> ACID_SPIT_PROJECTILE =
            ENTITY_TYPE_DEFERRED_REGISTER.register("acid_spit_projectile",
                    () -> EntityType.Builder.<AcidSpitProjectileEntity>of(AcidSpitProjectileEntity::new, MobCategory.MISC)
                            .sized(1f, 0.5f)
                            .build("acid_spit_projectile"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }

}
