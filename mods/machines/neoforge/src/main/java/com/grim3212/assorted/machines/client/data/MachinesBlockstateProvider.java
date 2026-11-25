package com.grim3212.assorted.machines.client.data;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.machines.common.blocks.BaseMachineBlock;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Block states, block models and the block items' models. {@link MachinesItemModelProvider} models
 * the other items.
 */
public class MachinesBlockstateProvider extends BlockStateProvider {

    public MachinesBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    private static String name(Block b) {
        return BuiltInRegistries.BLOCK.getKey(b).getPath();
    }

    @Override
    public String getName() {
        return "Assorted Machines block states";
    }

    @Override
    protected void registerStatesAndModels() {
        cube(MachinesBlocks.BRONZE_BLOCK.get());
        cube(MachinesBlocks.ELECTRUM_BLOCK.get());
        cube(MachinesBlocks.INVAR_BLOCK.get());
        cube(MachinesBlocks.STEEL_BLOCK.get());
        cube(MachinesBlocks.MACHINE_CORE.get());

        machine(MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachineTier.BASIC);
        machine(MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(), MachineTier.INTERMEDIATE);
        machine(MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachineTier.ADVANCED);
        machine(MachinesBlocks.EXPERT_ALLOY_FORGE.get(), MachineTier.EXPERT);

        machine(MachinesBlocks.BASIC_GRINDING_MILL.get(), MachineTier.BASIC);
        machine(MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(), MachineTier.INTERMEDIATE);
        machine(MachinesBlocks.ADVANCED_GRINDING_MILL.get(), MachineTier.ADVANCED);
        machine(MachinesBlocks.EXPERT_GRINDING_MILL.get(), MachineTier.EXPERT);
    }

    private void cube(Block b) {
        this.simpleBlockWithItem(b, this.cubeAll(b));
    }

    /** A machine, lit or not by {@code ON}, and turned to its {@code FACING} as vanilla's furnace is. */
    private void machine(Block b, MachineTier tier) {
        String name = name(b);
        ResourceLocation side = texture("block/" + tier.getName() + "_machine_side");
        ResourceLocation top = texture("block/" + tier.getName() + "_machine_top");

        ModelFile machineOff = this.models().orientable(name, side, texture("block/" + name + "_front"), top);
        ModelFile machineOn = this.models().orientable(name + "_on", side, texture("block/" + name + "_front_on"), top);

        this.horizontalBlock(b, state -> state.getValue(BaseMachineBlock.ON) ? machineOn : machineOff);
        this.simpleBlockItem(b, machineOff);
    }

    private ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

}
