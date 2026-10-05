package com.grim3212.assorted.machines.common.blocks;

import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.machines.common.blocks.blockentity.GrindingMillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GrindingMillBlock extends BaseMachineBlock {

    private final MachineTier tier;

    public GrindingMillBlock(MachineTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        switch (this.tier) {
            case INTERMEDIATE:
                return GrindingMillBlockEntity.intermediateBlockEntity(pos, state);
            case ADVANCED:
                return GrindingMillBlockEntity.advancedBlockEntity(pos, state);
            case EXPERT:
                return GrindingMillBlockEntity.expertBlockEntity(pos, state);
            case BASIC:
            default:
                return GrindingMillBlockEntity.basicBlockEntity(pos, state);
        }
    }
}
