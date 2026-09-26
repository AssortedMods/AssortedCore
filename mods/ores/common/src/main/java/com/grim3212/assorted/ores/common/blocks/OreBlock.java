package com.grim3212.assorted.ores.common.blocks;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;

public class OreBlock extends DropExperienceBlock {

    public OreBlock(Properties properties) {
        super(ConstantInt.of(0), properties);
    }

    public OreBlock(Properties properties, UniformInt xpRange) {
        super(xpRange, properties);
    }
}
