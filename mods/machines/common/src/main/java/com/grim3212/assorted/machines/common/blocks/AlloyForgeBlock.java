package com.grim3212.assorted.machines.common.blocks;

import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.machines.common.blocks.blockentity.AlloyForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlloyForgeBlock extends BaseMachineBlock {

    private final MachineTier tier;

    public AlloyForgeBlock(MachineTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (stateIn.getValue(ON)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = stateIn.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * d3 : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * d3 : d4;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        switch (this.tier) {
            case INTERMEDIATE:
                return AlloyForgeBlockEntity.intermediateBlockEntity(pos, state);
            case ADVANCED:
                return AlloyForgeBlockEntity.advancedBlockEntity(pos, state);
            case EXPERT:
                return AlloyForgeBlockEntity.expertBlockEntity(pos, state);
            case BASIC:
            default:
                return AlloyForgeBlockEntity.basicBlockEntity(pos, state);
        }
    }
}
