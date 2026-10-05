package com.grim3212.assorted.ores.data;

import com.google.common.collect.Lists;
import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.worldgen.OresWorldGenTargets;
import com.grim3212.assorted.lib.data.LibDatapackRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OresWorldGenData extends LibDatapackRegistryProvider {

    public static final Identifier ORE_ALUMINUM_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_aluminum");
    public static final Identifier ORE_NICKEL_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_nickel");
    public static final Identifier ORE_TIN_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_tin");
    public static final Identifier ORE_LEAD_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_lead");
    public static final Identifier ORE_SILVER_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_silver");
    public static final Identifier ORE_PLATINUM_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_platinum");

    public static final Identifier ORE_RUBY_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_ruby");
    public static final Identifier ORE_SAPPHIRE_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_sapphire");
    public static final Identifier ORE_TOPAZ_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_topaz");
    public static final Identifier ORE_PERIDOT_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_peridot");

    @Override
    public void addEntries(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, context -> {
            OresWorldGenData.getConfiguredFeatures().forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, r), f);
            });
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            OresWorldGenData.getPlacedFeatures(context).forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.PLACED_FEATURE, r), f);
            });
        });
    }

    @Override
    public List<ResourceKey<? extends Registry<?>>> registries() {
        return Lists.newArrayList(Registries.CONFIGURED_FEATURE, Registries.PLACED_FEATURE);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(Identifier key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<Identifier, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<Identifier, ConfiguredFeature<?, ?>> map = new HashMap<>();

        map.put(ORE_ALUMINUM_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_ALUMINUM_TARGET_LIST, 12)));
        map.put(ORE_NICKEL_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_NICKEL_TARGET_LIST, 9)));
        map.put(ORE_TIN_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_TIN_TARGET_LIST, 9)));
        map.put(ORE_LEAD_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_LEAD_TARGET_LIST, 8)));
        map.put(ORE_SILVER_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_SILVER_TARGET_LIST, 8)));
        map.put(ORE_PLATINUM_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_PLATINUM_TARGET_LIST, 7)));

        map.put(ORE_RUBY_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_RUBY_TARGET_LIST, 8)));
        map.put(ORE_SAPPHIRE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_SAPPHIRE_TARGET_LIST, 8)));
        map.put(ORE_TOPAZ_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_TOPAZ_TARGET_LIST, 8)));
        map.put(ORE_PERIDOT_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OresWorldGenTargets.ORE_PERIDOT_TARGET_LIST, 8)));

        return map;
    }

    private static Map<Identifier, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<Identifier, PlacedFeature> map = new HashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        map.put(ORE_ALUMINUM_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_ALUMINUM_KEY)), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(16), VerticalAnchor.absolute(125)))));
        map.put(ORE_NICKEL_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_NICKEL_KEY)), commonOrePlacement(14, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(40)))));
        map.put(ORE_TIN_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_TIN_KEY)), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(20), VerticalAnchor.absolute(256)))));
        map.put(ORE_LEAD_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_LEAD_KEY)), commonOrePlacement(14, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32)))));
        map.put(ORE_SILVER_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_SILVER_KEY)), commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(12)))));
        map.put(ORE_PLATINUM_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_PLATINUM_KEY)), commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-16)))));

        map.put(ORE_RUBY_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_RUBY_KEY)), commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)))));
        map.put(ORE_SAPPHIRE_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_SAPPHIRE_KEY)), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-12), VerticalAnchor.absolute(50)))));
        map.put(ORE_TOPAZ_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_TOPAZ_KEY)), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));
        map.put(ORE_PERIDOT_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(ORE_PERIDOT_KEY)), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(48), VerticalAnchor.absolute(256)))));

        return map;
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier modifier) {
        return List.of(p_195347_, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }
}
