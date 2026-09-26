package com.grim3212.assorted.machines.client.data;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.machines.common.blocks.BaseMachineBlock;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

/**
 * Block states and block models. This owns every block and block item; {@link
 * MachinesItemModelProvider} owns the rest, so the two never write the same file.
 */
public class MachinesBlockstateProvider extends ModelProvider {

    public MachinesBlockstateProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    private static String name(Block b) {
        return BuiltInRegistries.BLOCK.getKey(b).getPath();
    }

    @Override
    public String getName() {
        return "Assorted Machines block states";
    }

    /**
     * Only the block items belong here; every other item is {@link MachinesItemModelProvider}'s, so the
     * two providers never write the same file.
     */
    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(holder -> holder.value() instanceof BlockItem);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(MachinesBlocks.BRONZE_BLOCK.get());
        blockModels.createTrivialCube(MachinesBlocks.ELECTRUM_BLOCK.get());
        blockModels.createTrivialCube(MachinesBlocks.INVAR_BLOCK.get());
        blockModels.createTrivialCube(MachinesBlocks.STEEL_BLOCK.get());
        blockModels.createTrivialCube(MachinesBlocks.MACHINE_CORE.get());

        machine(blockModels, MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachineTier.BASIC);
        machine(blockModels, MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(), MachineTier.INTERMEDIATE);
        machine(blockModels, MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachineTier.ADVANCED);
        machine(blockModels, MachinesBlocks.EXPERT_ALLOY_FORGE.get(), MachineTier.EXPERT);

        machine(blockModels, MachinesBlocks.BASIC_GRINDING_MILL.get(), MachineTier.BASIC);
        machine(blockModels, MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(), MachineTier.INTERMEDIATE);
        machine(blockModels, MachinesBlocks.ADVANCED_GRINDING_MILL.get(), MachineTier.ADVANCED);
        machine(blockModels, MachinesBlocks.EXPERT_GRINDING_MILL.get(), MachineTier.EXPERT);
    }

    /**
     * A machine, rotated to its {@code FACING} with {@link
     * BlockModelGenerators#ROTATION_HORIZONTAL_FACING}.
     */
    private void machine(BlockModelGenerators blockModels, Block b, MachineTier tier) {
        String name = name(b);
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.SIDE, texture("block/" + tier.getName() + "_machine_side"))
                .put(TextureSlot.TOP, texture("block/" + tier.getName() + "_machine_top"))
                .put(TextureSlot.FRONT, texture("block/" + name + "_front"));

        MultiVariant machineOff = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ORIENTABLE.create(b, textures, blockModels.modelOutput));
        MultiVariant machineOn = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ORIENTABLE.createWithSuffix(b, "_on", textures.copyAndUpdate(TextureSlot.FRONT, texture("block/" + name + "_front_on")), blockModels.modelOutput));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(b)
                .with(BlockModelGenerators.createBooleanModelDispatch(BaseMachineBlock.ON, machineOn, machineOff))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private Material texture(String name) {
        return new Material(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

}
