package com.grim3212.assorted.ores.gametest;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.data.OresWorldGenData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** That the ores are found in a new world: added to every overworld biome on each loader, and able to place in stone and deepslate. */
final class WorldGenTests {

    private static final List<Identifier> ORES = List.of(OresWorldGenData.ORE_ALUMINUM_KEY, OresWorldGenData.ORE_NICKEL_KEY,
            OresWorldGenData.ORE_TIN_KEY, OresWorldGenData.ORE_LEAD_KEY, OresWorldGenData.ORE_SILVER_KEY, OresWorldGenData.ORE_PLATINUM_KEY,
            OresWorldGenData.ORE_RUBY_KEY, OresWorldGenData.ORE_SAPPHIRE_KEY, OresWorldGenData.ORE_TOPAZ_KEY, OresWorldGenData.ORE_PERIDOT_KEY);

    private WorldGenTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("ores_are_added_to_every_overworld_biome", WorldGenTests::oresAreAddedToEveryOverworldBiome);
        out.accept("ores_place_in_stone_and_deepslate", WorldGenTests::oresPlaceInStoneAndDeepslate);
    }

    /** Each loader adds features to biomes its own way, through Lib, and nothing else shows it did. */
    private static void oresAreAddedToEveryOverworldBiome(GameTestHelper helper) {
        List<String> missing = new ArrayList<>();
        int step = GenerationStep.Decoration.UNDERGROUND_ORES.ordinal();

        for (Holder<Biome> biome : helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD)) {
            List<HolderSet<PlacedFeature>> steps = biome.value().getGenerationSettings().features();
            for (Identifier ore : ORES) {
                ResourceKey<PlacedFeature> key = ResourceKey.create(Registries.PLACED_FEATURE, ore);
                boolean added = steps.size() > step && steps.get(step).stream().anyMatch(feature -> feature.is(key));
                if (!added) {
                    missing.add(ore + " in " + biome.getRegisteredName());
                }
            }
        }

        helper.assertTrue(missing.isEmpty(), "ores missing from overworld biomes:\n  " + String.join("\n  ", missing.stream().limit(20).toList()));
        helper.succeed();
    }

    /** Each vein run in a block of stone and then of deepslate, which its targets have to turn into the matching ore. */
    private static void oresPlaceInStoneAndDeepslate(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        for (Identifier ore : ORES) {
            String metal = ore.getPath().substring("ore_".length());
            Feature feature = level.registryAccess().lookupOrThrow(Registries.FEATURE).getValueOrThrow(ResourceKey.create(Registries.FEATURE, ore));
            check(helper, feature, Blocks.STONE, block(metal + "_ore"), problems);
            check(helper, feature, Blocks.DEEPSLATE, block("deepslate_" + metal + "_ore"), problems);
        }

        helper.assertTrue(problems.isEmpty(), String.join("\n", problems));
        helper.succeed();
    }

    /** A solid block of host rock so no ore is dropped for touching air; a few seeds, as one vein can roll small. */
    private static void check(GameTestHelper helper, Feature feature, Block host, Block expected, List<String> problems) {
        ServerLevel level = helper.getLevel();
        BlockPos from = helper.absolutePos(new BlockPos(1, 1, 1));
        BlockPos to = helper.absolutePos(new BlockPos(7, 6, 7));

        for (long seed = 0; seed < 5; seed++) {
            BlockPos.betweenClosed(from, to).forEach(pos -> level.setBlock(pos, host.defaultBlockState(), Block.UPDATE_CLIENTS));
            feature.place(level, level.getChunkSource().getGenerator(), RandomSource.create(seed), helper.absolutePos(new BlockPos(4, 3, 4)));
            if (BlockPos.betweenClosedStream(from, to).anyMatch(pos -> level.getBlockState(pos).is(expected))) {
                return;
            }
        }
        problems.add(BuiltInRegistries.BLOCK.getKey(expected) + " never placed in " + BuiltInRegistries.BLOCK.getKey(host));
    }

    private static Block block(String name) {
        return BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
