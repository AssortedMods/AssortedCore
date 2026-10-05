package com.grim3212.assorted.ores.common.worldgen;

import com.grim3212.assorted.ores.data.OresWorldGenData;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IWorldGenHelper;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class OresBiomeModifiers {
    public static void init() {
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_ALUMINUM_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_NICKEL_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_TIN_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_LEAD_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_SILVER_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_PLATINUM_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_RUBY_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_SAPPHIRE_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_TOPAZ_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, OresWorldGenData.ORE_PERIDOT_KEY);
    }

    private static IWorldGenHelper.BiomePredicate matchesTag(TagKey<Biome> tag) {
        return (resourceLocation, biome) -> biome.is(tag);
    }
}
