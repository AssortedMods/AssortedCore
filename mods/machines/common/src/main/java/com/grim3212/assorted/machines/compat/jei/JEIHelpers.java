package com.grim3212.assorted.machines.compat.jei;

import com.google.common.collect.Lists;
import com.grim3212.assorted.machines.api.crafting.MachineIngredient;
import com.grim3212.assorted.machines.api.machines.MachineUtil;
import com.grim3212.assorted.lib.platform.Services;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class JEIHelpers {

    public static List<ItemStack> grindingMillAcceptedTools = Lists.newArrayList();

    public static void hydrateLists() {
        grindingMillAcceptedTools = Services.PLATFORM.getRegistry(Registries.ITEM).getValues().filter((item) -> MachineUtil.allowedInGrindingMillToolSlot(new ItemStack(item))).map((item) -> new ItemStack(item)).collect(Collectors.toList());
    }

    /**
     * Fills a recipe slot from a machine ingredient. The stacks come out of the ingredient's
     * {@code SlotDisplay}, which needs the slot's own context map to resolve.
     */
    public static void addMachineIngredient(IRecipeSlotBuilder slot, MachineIngredient ingredient) {
        slot.addItemStacks(ingredient.getMatchingStacks(slot.getContextMap()));
    }

}
