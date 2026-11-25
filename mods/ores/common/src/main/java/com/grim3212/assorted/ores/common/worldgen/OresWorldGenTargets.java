package com.grim3212.assorted.ores.common.worldgen;

import com.google.common.collect.ImmutableList;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class OresWorldGenTargets {

    static RuleTest stoneOreTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    static RuleTest deepslateOreTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_ALUMINUM_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.ALUMINUM_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_ALUMINUM_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_NICKEL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.NICKEL_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_NICKEL_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_TIN_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.TIN_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_TIN_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_LEAD_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.LEAD_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_LEAD_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_PLATINUM_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.PLATINUM_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_PLATINUM_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_RUBY_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.RUBY_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_RUBY_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_SAPPHIRE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.SAPPHIRE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_TOPAZ_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.TOPAZ_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_TOPAZ_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_PERIDOT_TARGET_LIST = ImmutableList.of(OreConfiguration.target(stoneOreTest, OresBlocks.PERIDOT_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreTest, OresBlocks.DEEPSLATE_PERIDOT_ORE.get().defaultBlockState()));

}
