package com.nikotokiller.enigmamagica.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class GemCutterRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final NonNullList<ItemStack> outputItems;
    private final ResourceLocation id;

    public GemCutterRecipe(NonNullList<Ingredient> inputItems, NonNullList<ItemStack> outputItems, ResourceLocation id) {
        this.inputItems = inputItems;
        this.outputItems = outputItems;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return inputItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return outputItems.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputItems.get(0).copy();
    }

    public NonNullList<ItemStack> getResultItems() {
        return outputItems;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<GemCutterRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "gem_cutting";
    }

    public static class Serializer implements RecipeSerializer<GemCutterRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(EnigmaMagica.MODID, "gem_cutting");

        @Override
        public GemCutterRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonArray outputs = GsonHelper.getAsJsonArray(pSerializedRecipe, "output");
            NonNullList<ItemStack> outputItems = NonNullList.withSize(outputs.size(), ItemStack.EMPTY);

            for (int i = 0; i < outputs.size(); i++) {
                outputItems.set(i, ShapedRecipe.itemStackFromJson(outputs.get(i).getAsJsonObject()));
            }

            return new GemCutterRecipe(inputs, outputItems, pRecipeId);
        }

        @Override
        public GemCutterRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            NonNullList<ItemStack> outputItems = NonNullList.withSize(pBuffer.readInt(), ItemStack.EMPTY);
            for (int i = 0; i < outputItems.size(); i++) {
                outputItems.set(i, pBuffer.readItem());
            }

            return new GemCutterRecipe(inputs, outputItems, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GemCutterRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());
            for (Ingredient ingredient : pRecipe.inputItems) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeInt(pRecipe.outputItems.size());
            for (ItemStack output : pRecipe.outputItems) {
                pBuffer.writeItem(output);
            }
        }
    }
}
