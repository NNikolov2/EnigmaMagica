package com.nikotokiller.enigmamagica.item;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.item.custom.DesertStaffItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, EnigmaMagica.MODID);

    public static final RegistryObject<Item> WARPSTONE = ITEM_DEFERRED_REGISTER.register("warpstone", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WARPSTREAM = ITEM_DEFERRED_REGISTER.register("warpstream", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> UMBRACORE = ITEM_DEFERRED_REGISTER.register("umbracore", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WARPSTREAM_SHARD = ITEM_DEFERRED_REGISTER.register("warpstream_shard", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> UMBRACORE_SHARD = ITEM_DEFERRED_REGISTER.register("umbracore_shard", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DESERT_STAFF = ITEM_DEFERRED_REGISTER.register("desert_staff", () -> new DesertStaffItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEM_DEFERRED_REGISTER.register(eventBus);
    }

}
