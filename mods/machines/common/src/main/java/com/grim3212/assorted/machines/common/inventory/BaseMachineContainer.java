package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.api.machines.MachineUtil;
import com.grim3212.assorted.lib.core.inventory.IItemStorageHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMachineContainer extends RecipeBookMenu {

    /** Slot 3 in both machines. */
    private static final int RESULT_SLOT = 3;

    protected final ContainerData machineData;
    protected final Level world;
    protected final RecipeType<? extends BaseMachineRecipe> recipeType;
    private final IItemStorageHandler machineInventory;
    private List<Slot> inputSlots = List.of();

    public BaseMachineContainer(MenuType<? extends BaseMachineContainer> containerType, RecipeType<? extends BaseMachineRecipe> recipeType, int id, Inventory playerInventory, IItemStorageHandler machineInventory, ContainerData machineData) {
        super(containerType, id);
        checkContainerDataCount(machineData, 4);
        this.recipeType = recipeType;
        this.machineInventory = machineInventory;
        this.machineData = machineData;
        this.world = playerInventory.player.level();

        this.addDataSlots(machineData);
    }

    /** Call from the end of a subclass constructor, once the slots exist. Inputs run from zero; the grinding mill's tool slot follows them and is not an ingredient. */
    protected void defineInputSlots(int count) {
        List<Slot> slots = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            slots.add(this.getSlot(i));
        }

        this.inputSlots = List.copyOf(slots);
    }

    public List<Slot> recipeBookInputSlots() {
        return this.inputSlots;
    }

    public Slot recipeBookResultSlot() {
        return this.getSlot(RESULT_SLOT);
    }

    /**
     * Borrowed: {@code RecipeBookSettings} switches over {@link RecipeBookType} and throws on an
     * added value, which only NeoForge patches. Picks only the remembered open/filter pair.
     */
    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.BLAST_FURNACE;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents contents) {
        for (Slot slot : this.inputSlots) {
            contents.accountStack(slot.getItem());
        }
    }

    @Override
    public RecipeBookMenu.PostPlaceAction handlePlacement(boolean useMaxItems, boolean allowDroppingItemsToClear, RecipeHolder<?> recipe, ServerLevel level, Inventory inventory) {
        if (!(recipe.value() instanceof BaseMachineRecipe machineRecipe)) {
            return RecipeBookMenu.PostPlaceAction.NOTHING;
        }

        List<Slot> slotsToClear = new ArrayList<>(this.inputSlots);
        slotsToClear.add(this.recipeBookResultSlot());

        return MachineRecipePlacer.place(this.inputSlots, slotsToClear, machineRecipe.machineIngredients(), inventory, useMaxItems, allowDroppingItemsToClear);
    }

    @Override
    public abstract ItemStack quickMoveStack(Player playerIn, int index);

    @Override
    public boolean stillValid(Player playerIn) {
        return this.machineInventory.stillValid(playerIn);
    }

    protected boolean hasRecipe(ItemStack stack) {
        return MachineUtil.recipesOfType(this.world, (RecipeType<BaseMachineRecipe>) this.recipeType).anyMatch((recipe) -> {
            return recipe.validInput(stack);
        });
    }

    public abstract int getCookProgressionScaled();

    public abstract int getBurnLeftScaled();

    public boolean isBurning() {
        return this.machineData.get(0) > 0;
    }

}