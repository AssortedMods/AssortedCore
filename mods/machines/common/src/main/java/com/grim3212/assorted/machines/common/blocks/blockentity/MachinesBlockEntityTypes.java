package com.grim3212.assorted.machines.common.blocks.blockentity;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MachinesBlockEntityTypes {

    public static final RegistryProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistryProvider.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID).aliasFrom(Constants.FAMILY_ID);

    public static final IRegistryObject<BlockEntityType<AlloyForgeBlockEntity>> BASIC_ALLOY_FORGE = BLOCK_ENTITIES.register("basic_alloy_forge", () -> Services.PLATFORM.createBlockEntityType(AlloyForgeBlockEntity::basicBlockEntity, MachinesBlocks.BASIC_ALLOY_FORGE.get()));
    public static final IRegistryObject<BlockEntityType<AlloyForgeBlockEntity>> INTERMEDIATE_ALLOY_FORGE = BLOCK_ENTITIES.register("intermediate_alloy_forge", () -> Services.PLATFORM.createBlockEntityType(AlloyForgeBlockEntity::intermediateBlockEntity, MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get()));
    public static final IRegistryObject<BlockEntityType<AlloyForgeBlockEntity>> ADVANCED_ALLOY_FORGE = BLOCK_ENTITIES.register("advanced_alloy_forge", () -> Services.PLATFORM.createBlockEntityType(AlloyForgeBlockEntity::advancedBlockEntity, MachinesBlocks.ADVANCED_ALLOY_FORGE.get()));
    public static final IRegistryObject<BlockEntityType<AlloyForgeBlockEntity>> EXPERT_ALLOY_FORGE = BLOCK_ENTITIES.register("expert_alloy_forge", () -> Services.PLATFORM.createBlockEntityType(AlloyForgeBlockEntity::expertBlockEntity, MachinesBlocks.EXPERT_ALLOY_FORGE.get()));

    public static final IRegistryObject<BlockEntityType<GrindingMillBlockEntity>> BASIC_GRINDING_MILL = BLOCK_ENTITIES.register("basic_grinding_mill", () -> Services.PLATFORM.createBlockEntityType(GrindingMillBlockEntity::basicBlockEntity, MachinesBlocks.BASIC_GRINDING_MILL.get()));
    public static final IRegistryObject<BlockEntityType<GrindingMillBlockEntity>> INTERMEDIATE_GRINDING_MILL = BLOCK_ENTITIES.register("intermediate_grinding_mill", () -> Services.PLATFORM.createBlockEntityType(GrindingMillBlockEntity::intermediateBlockEntity, MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get()));
    public static final IRegistryObject<BlockEntityType<GrindingMillBlockEntity>> ADVANCED_GRINDING_MILL = BLOCK_ENTITIES.register("advanced_grinding_mill", () -> Services.PLATFORM.createBlockEntityType(GrindingMillBlockEntity::advancedBlockEntity, MachinesBlocks.ADVANCED_GRINDING_MILL.get()));
    public static final IRegistryObject<BlockEntityType<GrindingMillBlockEntity>> EXPERT_GRINDING_MILL = BLOCK_ENTITIES.register("expert_grinding_mill", () -> Services.PLATFORM.createBlockEntityType(GrindingMillBlockEntity::expertBlockEntity, MachinesBlocks.EXPERT_GRINDING_MILL.get()));

    public static void init() {
    }
}
