package com.grim3212.assorted.machines.api.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

/**
 * Shared behaviour for the machine recipes, which match a {@link MachineRecipeInput} of just the
 * input slots.
 */
public abstract class BaseMachineRecipe implements Recipe<MachineRecipeInput> {

    protected final String group;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookTime;

    public BaseMachineRecipe(String groupIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        this.group = groupIn;
        this.result = resultIn;
        this.experience = experienceIn;
        this.cookTime = cookTimeIn;
    }

    @Override
    public ItemStack assemble(MachineRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    public float getExperience() {
        return this.experience;
    }

    /** A copy of the recipe's result, which needs no registries to read. */
    public ItemStack getResultItem() {
        return this.result.copy();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public int getCookTime() {
        return cookTime;
    }

    /**
     * Kept out of the recipe book. 1.21.1's book sorts every recipe it is sent into a tab from a fixed
     * enum of categories, and a machine has none, so the client would only log each one as an unknown
     * recipe category. JEI lists them instead.
     */
    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        this.machineIngredients().forEach(ingredient -> ingredients.add(ingredient.getBaseIngredient()));
        return ingredients;
    }

    /** One per input slot, in the menu's slot order. */
    public abstract List<MachineIngredient> machineIngredients();

    public abstract boolean validInput(ItemStack stack);
}
