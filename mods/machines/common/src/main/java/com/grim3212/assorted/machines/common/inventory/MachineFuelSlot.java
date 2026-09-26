package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.lib.core.inventory.IItemStorageHandler;
import com.grim3212.assorted.lib.core.inventory.slot.SlotStorageHandler;
import com.grim3212.assorted.lib.platform.Services;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MachineFuelSlot extends SlotStorageHandler {

    // Burn times are data driven in 26.x and resolved against the level's fuel values, so the slot
    // has to be handed the level it belongs to.
    private final Level level;

    public MachineFuelSlot(Level level, IItemStorageHandler forgeInventory, int id, int x, int y) {
        super(forgeInventory, id, x, y);
        this.level = level;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return Services.PLATFORM.getFuelTime(this.level, stack) > 0;
    }
}
