package com.nikotokiller.enigmamagica.datagen.loot;

import com.nikotokiller.enigmamagica.block.ModBlocks;
import com.nikotokiller.enigmamagica.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {


    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.GEM_FUSION_TABLE.get());
        this.add(ModBlocks.WARPSTONE_ORE.get(),
                block -> createSilkTouchDispatchTable(ModBlocks.WARPSTONE_ORE.get()
                        , this.applyExplosionDecay(ModBlocks.WARPSTONE_ORE.get()
                                , LootItem.lootTableItem(ModItems.WARPSTONE.get()).apply(SetItemCountFunction.setCount(
                                                BinomialDistributionGenerator.binomial(3, 0.3f)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
