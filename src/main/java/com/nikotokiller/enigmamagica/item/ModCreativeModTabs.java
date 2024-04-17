package com.nikotokiller.enigmamagica.item;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EnigmaMagica.MODID);

    public static final RegistryObject<CreativeModeTab> ENIGMA_MAGICA_TAB  = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("enigma_magica_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WARPSTONE.get()))
            .title(Component.translatable("creativetab.enigma_magica_tab"))
            .displayItems((pParameters, output) -> {
                output.accept(ModItems.WARPSTONE.get());
                output.accept(ModItems.WARPSTREAM.get());
                output.accept(ModItems.WARPSTREAM_SHARD.get());
                output.accept(ModItems.UMBRACORE.get());
                output.accept(ModItems.UMBRACORE_SHARD.get());
            })
            .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
    }


}
