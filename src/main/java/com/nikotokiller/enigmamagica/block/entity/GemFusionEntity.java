package com.nikotokiller.enigmamagica.block.entity;

import com.nikotokiller.enigmamagica.recipe.GemFusionRecipe;
import com.nikotokiller.enigmamagica.screen.GemFusionMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GemFusionEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3);

    private static final int OUTPUT_SLOT = 0;
    private static final int INPUT_SLOT_LEFT = 1;
    private static final int INPUT_SLOT_RIGHT = 2;

    private LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 15;

    public GemFusionEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GEM_FUSION_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> GemFusionEntity.this.progress;
                    case 1 -> GemFusionEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> GemFusionEntity.this.progress = pValue;
                    case 1 -> GemFusionEntity.this.maxProgress = pValue;
                };
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyOptional = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyOptional.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.enigmamagica.gem_fusion_table");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GemFusionMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("gem_fusion.progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_fusion.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()){
            increaseCraftingProcess();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()){
                craftItem();
                resetProgress();
            }
        }else{
            resetProgress();
        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<GemFusionRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isPresent()) {
            GemFusionRecipe recipe = recipeOptional.get();

            this.itemStackHandler.extractItem(INPUT_SLOT_LEFT, 1, false);
            this.itemStackHandler.extractItem(INPUT_SLOT_RIGHT, 1, false);

            NonNullList<ItemStack> results = recipe.getResultItems();
            ItemStack result = results.get(0);

            this.itemStackHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), this.itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
        }
    }


    private void increaseCraftingProcess() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<GemFusionRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isEmpty()) {
            return false;
        }

        GemFusionRecipe recipe = recipeOptional.get();
        NonNullList<ItemStack> results = recipe.getResultItems();
        ItemStack result = results.get(0);

        return canInsertAmountIntoOutputSlot(result.getCount()) &&
                canInsertItemIntoOutputSlots(result.getItem());
    }

    private Optional<GemFusionRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(GemFusionRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlots(Item item) {
        ItemStack output = this.itemStackHandler.getStackInSlot(OUTPUT_SLOT);

        return output.isEmpty() || output.is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count){
        ItemStack output = this.itemStackHandler.getStackInSlot(OUTPUT_SLOT);

        return output.getCount() + count <= output.getMaxStackSize();
    }

}
