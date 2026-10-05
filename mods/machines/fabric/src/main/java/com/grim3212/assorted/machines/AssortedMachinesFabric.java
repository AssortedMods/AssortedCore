package com.grim3212.assorted.machines;

import com.grim3212.assorted.machines.common.blocks.blockentity.MachinesBlockEntityTypes;
import com.grim3212.assorted.machines.common.crafting.MachineRecipeBackfill;
import com.grim3212.assorted.lib.core.inventory.IInventoryBlockEntity;
import com.grim3212.assorted.lib.inventory.FabricPlatformInventoryStorageHandlerSided;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class AssortedMachinesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        MachinesCommonMod.init();

        // TODO(11.0.0): remove along with MachineRecipeBackfill.
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> MachineRecipeBackfill.award(handler.player));

        ItemStorage.SIDED.registerForBlockEntities((be, direction) ->
                {
                    if (be instanceof IInventoryBlockEntity inv)
                        return ((FabricPlatformInventoryStorageHandlerSided) inv.getStorageHandler()).getFabricInventory(direction);
                    return null;
                },
                MachinesBlockEntityTypes.BASIC_ALLOY_FORGE.get(),
                MachinesBlockEntityTypes.BASIC_GRINDING_MILL.get(),
                MachinesBlockEntityTypes.INTERMEDIATE_ALLOY_FORGE.get(),
                MachinesBlockEntityTypes.INTERMEDIATE_GRINDING_MILL.get(),
                MachinesBlockEntityTypes.ADVANCED_ALLOY_FORGE.get(),
                MachinesBlockEntityTypes.ADVANCED_GRINDING_MILL.get(),
                MachinesBlockEntityTypes.EXPERT_ALLOY_FORGE.get(),
                MachinesBlockEntityTypes.EXPERT_GRINDING_MILL.get()
        );
    }
}
