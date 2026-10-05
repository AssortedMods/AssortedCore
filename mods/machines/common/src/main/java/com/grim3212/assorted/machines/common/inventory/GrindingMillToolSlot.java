package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.machines.api.machines.MachineUtil;
import com.grim3212.assorted.lib.core.inventory.IItemStorageHandler;
import com.grim3212.assorted.lib.core.inventory.slot.SlotStorageHandler;
import net.minecraft.world.item.ItemStack;

public class GrindingMillToolSlot extends SlotStorageHandler {

    public GrindingMillToolSlot(IItemStorageHandler forgeInventory, int id, int x, int y) {
        super(forgeInventory, id, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return MachineUtil.allowedInGrindingMillToolSlot(stack);
    }

}
