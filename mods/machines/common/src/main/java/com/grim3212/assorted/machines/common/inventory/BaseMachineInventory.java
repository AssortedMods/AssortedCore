package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.lib.core.inventory.impl.ItemStackStorageHandler;
import com.grim3212.assorted.lib.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BaseMachineInventory extends ItemStackStorageHandler {

    private final BaseMachineBlockEntity machine;
    private final Direction direction;

    public BaseMachineInventory(BaseMachineBlockEntity machine, Direction direction) {
        super(machine.getItems());
        this.machine = machine;
        this.direction = direction;
    }

    private int getSlot(int slot) {
        if (this.direction == null) {
            return slot;
        }

        int[] slots = this.machine.getSlotsForFace(this.direction);
        if (slot < slots.length)
            return slots[slot];
        return -1;
    }

    @Override
    public int getSlots() {
        if (this.direction == null) {
            return this.machine.getItems().size();
        }

        return this.machine.getSlotsForFace(this.direction).length;
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        int newSlot = getSlot(slot);
        if (newSlot == -1) {
            return 0;
        }

        return super.getStackLimit(newSlot, stack);
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        int newSlot = getSlot(slot);
        if (newSlot == -1) {
            return ItemStack.EMPTY;
        }

        return super.getStackInSlot(newSlot);
    }

    @Override
    public void setStackInSlot(int slotIn, ItemStack stack) {
        int newSlot = getSlot(slotIn);
        if (newSlot == -1) {
            return;
        }

        ItemStack itemstack = this.getStackInSlot(slotIn);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, itemstack);
        this.machine.getItems().set(newSlot, stack);
        if (stack.getCount() > this.getSlotLimit(newSlot)) {
            stack.setCount(this.getSlotLimit(newSlot));
        }

        if (this.machine.inputSlots().stream().anyMatch((x) -> x == newSlot) && !flag) {
            this.machine.setCookTimeTotal(this.machine.getCookTime());
            this.machine.setCookTime(0);
        }

        onContentsChanged(newSlot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slotIn, @NotNull ItemStack stack, boolean simulate) {
        int newSlot = getSlot(slotIn);
        if (newSlot == -1) {
            return stack;
        }

        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slotIn, stack))
            return stack;

        validateSlotIndex(newSlot);

        ItemStack existing = this.stacks.get(newSlot);

        int limit = getStackLimit(slotIn, stack);

        if (!existing.isEmpty()) {
            if (!Services.INVENTORY.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.setStackInSlot(slotIn, reachedLimit ? Services.INVENTORY.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
                onContentsChanged(newSlot);
            }
        }

        return reachedLimit ? Services.INVENTORY.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slotIn, int amount, boolean simulate) {
        int newSlot = getSlot(slotIn);
        if (newSlot == -1) {
            return ItemStack.EMPTY;
        }

        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(newSlot);

        ItemStack existing = this.stacks.get(newSlot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.setStackInSlot(slotIn, ItemStack.EMPTY);
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.setStackInSlot(slotIn, Services.INVENTORY.copyStackWithSize(existing, existing.getCount() - toExtract));
            }

            return Services.INVENTORY.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = this.machine.getBlockPos();
        if (this.machine.getLevel().getBlockEntity(pos) != this.machine) {
            return false;
        } else {
            return player.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void onContentsChanged(int slot) {
        this.machine.setChanged();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        int newSlot = getSlot(slot);
        if (newSlot == -1) {
            return false;
        }

        return this.machine.canPlaceItem(newSlot, stack);
    }
}
