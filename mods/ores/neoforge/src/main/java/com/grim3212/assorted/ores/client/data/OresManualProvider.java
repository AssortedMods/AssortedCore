package com.grim3212.assorted.ores.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.ores.common.items.OresItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.function.Predicate;

/**
 * This part's chapters of the Assorted Core section, which every part shares; the explicit chapter orders keep the
 * section's order whichever parts are installed. Each metal and gem family is matched by the shape of its ids.
 */
public class OresManualProvider extends LibManualProvider {

    /** The metals with an ore, a raw form and a full ingot family. */
    private static final List<String> ORE_METALS =
            List.of("tin", "silver", "aluminum", "nickel", "platinum", "lead");

    private static final List<String> GEMS = List.of("ruby", "peridot", "sapphire", "topaz");

    public OresManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Constants.FAMILY_ID);
    }

    @Override
    protected void addChapters() {
        this.addMetals();
        this.addGems();
    }

    private void addMetals() {
        ChapterBuilder metals = this.chapter("metals", 1);

        metals.items("ores", OresBlocks.TIN_ORE.get().asItem(), OresBlocks.SILVER_ORE.get().asItem(),
                        OresBlocks.ALUMINUM_ORE.get().asItem(), OresBlocks.NICKEL_ORE.get().asItem(),
                        OresBlocks.PLATINUM_ORE.get().asItem(), OresBlocks.LEAD_ORE.get().asItem()).every(50)
                .opensEveryBlock(family(ORE_METALS, "_ore", "_block", "_raw_block"))
                .opensEveryBlock(deepslate(ORE_METALS))
                .opensEveryBlock(rawBlocks(ORE_METALS))
                .opensEveryItem(family(ORE_METALS, "_ingot", "_nugget"))
                .opensEveryItem(raw(ORE_METALS));
        metals.recipesById("dusts", recipeId("dusts/tin_smelting"), recipeId("dusts/silver_smelting"), recipeId("dusts/aluminum_smelting"),
                        recipeId("dusts/nickel_smelting"), recipeId("dusts/platinum_smelting"), recipeId("dusts/lead_smelting")).every(60)
                .opensEveryItem(family(ORE_METALS, "_dust"));
        metals.recipes("gears", OresItems.TIN_GEAR.get(), OresItems.SILVER_GEAR.get(), OresItems.ALUMINUM_GEAR.get(),
                        OresItems.NICKEL_GEAR.get(), OresItems.PLATINUM_GEAR.get(), OresItems.LEAD_GEAR.get()).every(60)
                .opensEveryItem(family(ORE_METALS, "_gear"));
    }

    private void addGems() {
        ChapterBuilder gems = this.chapter("gems", 2);

        // An items page, since the grinding recipes named after the gems only load with Assorted Machines.
        gems.items("gems", OresItems.RUBY.get(), OresItems.PERIDOT.get(), OresItems.SAPPHIRE.get(),
                        OresItems.TOPAZ.get()).every(50)
                .opens(OresItems.RUBY.get(), OresItems.PERIDOT.get(), OresItems.SAPPHIRE.get(), OresItems.TOPAZ.get());
        gems.recipesById("smelting", recipeId("ruby_smelting"), recipeId("peridot_smelting"), recipeId("sapphire_smelting"), recipeId("topaz_smelting"))
                .every(60)
                .opensEveryBlock(family(GEMS, "_ore"))
                .opensEveryBlock(deepslate(GEMS));
        gems.recipes("storage", OresBlocks.RUBY_BLOCK.get(), OresBlocks.PERIDOT_BLOCK.get(), OresBlocks.SAPPHIRE_BLOCK.get(), OresBlocks.TOPAZ_BLOCK.get()).every(60)
                .opensEveryBlock(family(GEMS, "_block"));
    }

    /** {@code <material><suffix>} for any of the materials and any of the suffixes. */
    private static Predicate<Identifier> family(List<String> materials, String... suffixes) {
        return id -> {
            for (String material : materials) {
                for (String suffix : suffixes) {
                    if (id.getPath().equals(material + suffix)) {
                        return true;
                    }
                }
            }
            return false;
        };
    }

    private static Predicate<Identifier> deepslate(List<String> materials) {
        return id -> materials.stream().anyMatch(m -> id.getPath().equals("deepslate_" + m + "_ore"));
    }

    private static Predicate<Identifier> rawBlocks(List<String> materials) {
        return id -> materials.stream().anyMatch(m -> id.getPath().equals("raw_" + m + "_block"));
    }

    private static Predicate<Identifier> raw(List<String> materials) {
        return id -> materials.stream().anyMatch(m -> id.getPath().equals("raw_" + m));
    }
}
