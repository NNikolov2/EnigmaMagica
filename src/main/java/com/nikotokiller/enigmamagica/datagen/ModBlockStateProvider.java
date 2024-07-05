package com.nikotokiller.enigmamagica.datagen;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EnigmaMagica.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockWithItem(ModBlocks.WARPSTONE_ORE);

        horizontalBlock(ModBlocks.GEM_CUTTER_BOTTOM.get(), modLoc("block/gem_cutter_bottom"), modLoc("block/gemcuttertable"));
        horizontalBlock(ModBlocks.GEM_FUSION_TABLE.get(), modLoc("block/gem_fusion_table"), modLoc("block/gem_fusion_table"));
        horizontalBlock(ModBlocks.GEM_CUTTER_MIDDLE.get(), modLoc("block/gem_cutter_middle"), modLoc("block/gem_cutter_middle"));
        horizontalBlock(ModBlocks.GEM_CUTTER_TOP.get(), modLoc("block/gem_cutter_top"), modLoc("block/gem_cutter_top"));

        furnaceBlock(ModBlocks.ALLOY_FURNACE.get(), modLoc("block/alloy_furnace_unlit"), modLoc("block/alloy_furnace_liy"));
    }

    private void BlockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));;
    }

    private void horizontalBlock(Block block, ResourceLocation modelLocation, ResourceLocation itemModelLocation) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modelLocation))
                            .rotationY(((int) dir.toYRot()) % 360)
                            .build();
                });
        simpleBlockItem(block, new ModelFile.UncheckedModelFile(itemModelLocation));
    }

    private void furnaceBlock(Block block, ResourceLocation unlitModelLocation, ResourceLocation litModelLocation) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    boolean lit = state.getValue(BlockStateProperties.LIT);
                    ResourceLocation modelLocation = lit ? litModelLocation : unlitModelLocation;
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modelLocation))
                            .rotationY(((int) dir.toYRot()) % 360)
                            .build();
                });
        simpleBlockItem(block, new ModelFile.UncheckedModelFile(unlitModelLocation));
    }


}
