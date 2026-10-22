package com.grim3212.assorted.machines.data;

import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.lib.data.LibBlockLootProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MachinesBlockLoot extends LibBlockLootProvider {

    public MachinesBlockLoot(LootTableSubProvider.Context context) {
        super(context, () -> MachinesBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList()));
    }

    @Override
    public void generate() {
        this.dropSelf(MachinesBlocks.BRONZE_BLOCK.get());
        this.dropSelf(MachinesBlocks.ELECTRUM_BLOCK.get());
        this.dropSelf(MachinesBlocks.INVAR_BLOCK.get());
        this.dropSelf(MachinesBlocks.STEEL_BLOCK.get());

        this.dropSelf(MachinesBlocks.MACHINE_CORE.get());
        this.dropSelf(MachinesBlocks.BASIC_ALLOY_FORGE.get());
        this.dropSelf(MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get());
        this.dropSelf(MachinesBlocks.ADVANCED_ALLOY_FORGE.get());
        this.dropSelf(MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        this.dropSelf(MachinesBlocks.BASIC_GRINDING_MILL.get());
        this.dropSelf(MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get());
        this.dropSelf(MachinesBlocks.ADVANCED_GRINDING_MILL.get());
        this.dropSelf(MachinesBlocks.EXPERT_GRINDING_MILL.get());
    }
}
