package com.nikotokiller.enigmamagica.block.entity;

import com.nikotokiller.enigmamagica.recipe.GemCutterRecipe;
import com.nikotokiller.enigmamagica.screen.GemCutterMenu;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GemCutterEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT_LEFT = 1;
    private static final int OUTPUT_SLOT_RIGHT = 2;

    private LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 15;

    public GemCutterEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GEM_CUTTER_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> GemCutterEntity.this.progress;
                    case 1 -> GemCutterEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                 switch (pIndex){
                    case 0 -> GemCutterEntity.this.progress = pValue;
                    case 1 -> GemCutterEntity.this.maxProgress = pValue;
                };
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyOptional.cast();
        }
        return super.getCapability(cap);
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

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.enigmamagica.gem_cutter_bottom");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GemCutterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("gem_cutter.progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_cutter.progress");
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
        Optional<GemCutterRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isPresent()) {
            GemCutterRecipe recipe = recipeOptional.get();

            this.itemStackHandler.extractItem(INPUT_SLOT, 1, false);

            NonNullList<ItemStack> results = recipe.getResultItems();
            ItemStack resultOne = results.get(0);
            ItemStack resultTwo = results.size() > 1 ? results.get(1) : ItemStack.EMPTY;

            this.itemStackHandler.setStackInSlot(OUTPUT_SLOT_LEFT, new ItemStack(resultOne.getItem(), this.itemStackHandler.getStackInSlot(OUTPUT_SLOT_LEFT).getCount() + resultOne.getCount()));
            this.itemStackHandler.setStackInSlot(OUTPUT_SLOT_RIGHT, new ItemStack(resultTwo.getItem(), this.itemStackHandler.getStackInSlot(OUTPUT_SLOT_RIGHT).getCount() + resultTwo.getCount()));
        }
    }


    private void increaseCraftingProcess() {
          progress++;
    }

    private boolean hasRecipe() {
        Optional<GemCutterRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isEmpty()) {
            return false;
        }

        GemCutterRecipe recipe = recipeOptional.get();
        NonNullList<ItemStack> results = recipe.getResultItems();
        ItemStack resultOne = results.get(0);
        ItemStack resultTwo = results.size() > 1 ? results.get(1) : ItemStack.EMPTY;

        return canInsertAmountIntoOutputSlot(resultOne.getCount(), resultTwo.getCount()) &&
                canInsertItemIntoOutputSlots(resultOne.getItem(), resultTwo.getItem());
    }

    private Optional<GemCutterRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(GemCutterRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlots(Item itemOne, Item itemTwo) {
        ItemStack outputLeft = this.itemStackHandler.getStackInSlot(OUTPUT_SLOT_LEFT);
        ItemStack outputRight = this.itemStackHandler.getStackInSlot(OUTPUT_SLOT_RIGHT);

        return (outputLeft.isEmpty() || outputLeft.is(itemOne)) && (outputRight.isEmpty() || outputRight.is(itemTwo));
    }

    private boolean canInsertAmountIntoOutputSlot(int countOne, int countTwo){
        ItemStack outputLeft = this.itemStackHandler.getStackInSlot(OUTPUT_SLOT_LEFT);
        ItemStack outputRight = this.itemStackHandler.getStackInSlot(OUTPUT_SLOT_RIGHT);

        return (outputLeft.getCount() + countOne <= outputLeft.getMaxStackSize()) &&
                (outputRight.getCount() + countTwo <= outputRight.getMaxStackSize());
    }

}
