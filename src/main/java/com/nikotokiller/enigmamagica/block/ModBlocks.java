package com.nikotokiller.enigmamagica.block;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.block.custom.*;
import com.nikotokiller.enigmamagica.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

     public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, EnigmaMagica.MODID);

     public static final RegistryObject<Block> WARPSTONE_ORE = registerBlock("warpstone_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
             .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 5)));

     public static final RegistryObject<Block> GEM_CUTTER_BOTTOM = registerBlock("gem_cutter_bottom",
             () -> new Gem_Cutter_Bottom(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

     public static final RegistryObject<Block> GEM_CUTTER_MIDDLE = registerBlock("gem_cutter_middle",
             () -> new Gem_Cutter_Middle(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

     public static final RegistryObject<Block> GEM_CUTTER_TOP = registerBlock("gem_cutter_top",
             () -> new Gem_Cutter_Top(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

     public static final RegistryObject<Block> GEM_FUSION_TABLE = registerBlock("gem_fusion_table",
             () -> new Gem_Fusion_Table(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

     public static final RegistryObject<Block> ALLOY_FURNACE = registerBlock("alloy_furnace",
             () -> new Alloy_Furnace(BlockBehaviour.Properties.copy(Blocks.FURNACE)));

     private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
          RegistryObject<T> toReturn = BLOCK_DEFERRED_REGISTER.register(name, block);
          registerBlockItem(name, toReturn);
          return toReturn;
     }

     private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
          return ModItems.ITEM_DEFERRED_REGISTER.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
     }

     public static void register(IEventBus eventBus){
          BLOCK_DEFERRED_REGISTER.register(eventBus);
     }
}
