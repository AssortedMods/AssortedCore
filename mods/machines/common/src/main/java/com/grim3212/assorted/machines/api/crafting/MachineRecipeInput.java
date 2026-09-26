package com.grim3212.assorted.machines.api.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

/**
 * The input slots a machine offers a recipe, so a recipe never sees the fuel or output slots.
 *
 * @param items The input slots, in the order the recipe expects them.
 */
public record MachineRecipeInput(List<ItemStack> items) implements RecipeInput {

    public static MachineRecipeInput of(ItemStack... stacks) {
        return new MachineRecipeInput(List.of(stacks));
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return this.items.size();
    }
}
