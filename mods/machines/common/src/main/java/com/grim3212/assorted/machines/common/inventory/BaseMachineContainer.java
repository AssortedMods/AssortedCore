package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.lib.core.inventory.IItemStorageHandler;
import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.api.machines.MachineUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * What the two machine menus share. Without a recipe book in 1.21.1, JEI's transfer button is what
 * fills a machine from a recipe, so the slot ranges it is given are kept here beside the slots.
 */
public abstract class BaseMachineContainer extends AbstractContainerMenu {

    /** The slots JEI's transfer button fills, from slot 0 in the order its categories lay them out. */
    public static final int TRANSFER_SLOTS = 2;
    /** The player's main inventory and hotbar follow the four machine slots. */
    public static final int INVENTORY_START = 4;
    public static final int INVENTORY_SIZE = 36;

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

    /** The slots a recipe's ingredients go in, in the recipe's order. */
    public List<Slot> inputSlots() {
        return this.inputSlots;
    }

    @Override
    public abstract ItemStack quickMoveStack(Player playerIn, int index);

    @Override
    public boolean stillValid(Player playerIn) {
        return this.machineInventory.stillValid(playerIn);
    }

    protected boolean hasRecipe(ItemStack stack) {
        return MachineUtil.recipesOfType(this.world, this.recipeType).anyMatch((recipe) -> {
            return recipe.validInput(stack);
        });
    }

    public abstract int getCookProgressionScaled();

    public abstract int getBurnLeftScaled();

    public boolean isBurning() {
        return this.machineData.get(0) > 0;
    }

}
