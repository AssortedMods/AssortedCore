package com.grim3212.assorted.machines.api.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared behaviour for the machine recipes, which match a {@link MachineRecipeInput} of just the
 * input slots.
 */
public abstract class BaseMachineRecipe implements Recipe<MachineRecipeInput> {

    protected final String group;
    protected final ItemStackTemplate result;
    protected final float experience;
    protected final int cookTime;

    private @Nullable PlacementInfo placementInfo;

    public BaseMachineRecipe(String groupIn, ItemStackTemplate resultIn, float experienceIn, int cookTimeIn) {
        this.group = groupIn;
        this.result = resultIn;
        this.experience = experienceIn;
        this.cookTime = cookTimeIn;
    }

    @Override
    public ItemStack assemble(MachineRecipeInput input) {
        return this.result.create();
    }

    public float getExperience() {
        return this.experience;
    }

    /**
     * The recipe's result. Stored as an {@link ItemStackTemplate} because a stack cannot be built
     * before item components are bound.
     */
    public ItemStack getResultItem() {
        return this.result.create();
    }

    public ItemStackTemplate getResultTemplate() {
        return this.result;
    }

    @Override
    public String group() {
        return this.group;
    }

    public int getCookTime() {
        return cookTime;
    }

    /** In the recipe book now, so it toasts like a smelting recipe. */
    @Override
    public boolean showNotification() {
        return true;
    }

    /** Counted out one item per entry: the book's craftable check does not understand a count. */
    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            List<Ingredient> expanded = new ArrayList<>();

            for (MachineIngredient ingredient : this.machineIngredients()) {
                for (int i = 0; i < ingredient.getCount(); i++) {
                    expanded.add(ingredient.getBaseIngredient());
                }
            }

            this.placementInfo = expanded.isEmpty() ? PlacementInfo.NOT_PLACEABLE : PlacementInfo.create(expanded);
        }

        return this.placementInfo;
    }

    /** One per input slot, in the menu's slot order. */
    public abstract List<MachineIngredient> machineIngredients();

    @Override
    public List<RecipeDisplay> display() {
        List<MachineRecipeDisplay.Input> inputs = this.machineIngredients().stream().map(MachineRecipeDisplay.Input::new).toList();
        return List.of(new MachineRecipeDisplay(inputs, new SlotDisplay.ItemStackSlotDisplay(this.result), this.craftingStationDisplay(), this.cookTime, this.experience));
    }

    /** Every tier of the machine this is made in. */
    protected abstract SlotDisplay craftingStationDisplay();

    public abstract boolean validInput(ItemStack stack);
}
