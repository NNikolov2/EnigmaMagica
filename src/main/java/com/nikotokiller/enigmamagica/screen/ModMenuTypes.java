package com.nikotokiller.enigmamagica.screen;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, EnigmaMagica.MODID);

    public static final RegistryObject<MenuType<GemCutterMenu>> GEM_CUTTER_MENU =
            registerMenuType("gem_cutter_menu", GemCutterMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENU_TYPE_DEFERRED_REGISTER.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENU_TYPE_DEFERRED_REGISTER.register(eventBus);
    }

}
