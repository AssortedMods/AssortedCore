package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.lib.core.inventory.IItemStorageHandler;
import com.grim3212.assorted.lib.core.inventory.slot.SlotStorageHandler;
import com.grim3212.assorted.lib.platform.Services;
import net.minecraft.world.item.ItemStack;

public class MachineFuelSlot extends SlotStorageHandler {

    public MachineFuelSlot(IItemStorageHandler forgeInventory, int id, int x, int y) {
        super(forgeInventory, id, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return Services.PLATFORM.isFuel(stack);
    }
}
