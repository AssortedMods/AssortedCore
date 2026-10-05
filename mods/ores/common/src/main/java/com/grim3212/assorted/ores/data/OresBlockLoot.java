package com.grim3212.assorted.ores.data;

import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.ores.common.items.OresItems;
import com.grim3212.assorted.lib.data.LibBlockLootProvider;
import net.minecraft.core.HolderLookup;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OresBlockLoot extends LibBlockLootProvider {

    // Loot sub providers are handed the registry lookup at construction now.
    public OresBlockLoot(HolderLookup.Provider registries) {
        super(registries, () -> OresBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList()));
    }

    @Override
    public void generate() {
        this.dropSelf(OresBlocks.TIN_BLOCK.get());
        this.dropSelf(OresBlocks.SILVER_BLOCK.get());
        this.dropSelf(OresBlocks.ALUMINUM_BLOCK.get());
        this.dropSelf(OresBlocks.NICKEL_BLOCK.get());
        this.dropSelf(OresBlocks.PLATINUM_BLOCK.get());
        this.dropSelf(OresBlocks.LEAD_BLOCK.get());
        this.dropSelf(OresBlocks.RUBY_BLOCK.get());
        this.dropSelf(OresBlocks.PERIDOT_BLOCK.get());
        this.dropSelf(OresBlocks.SAPPHIRE_BLOCK.get());
        this.dropSelf(OresBlocks.TOPAZ_BLOCK.get());

        this.dropSelf(OresBlocks.RAW_TIN_BLOCK.get());
        this.dropSelf(OresBlocks.RAW_SILVER_BLOCK.get());
        this.dropSelf(OresBlocks.RAW_ALUMINUM_BLOCK.get());
        this.dropSelf(OresBlocks.RAW_NICKEL_BLOCK.get());
        this.dropSelf(OresBlocks.RAW_PLATINUM_BLOCK.get());
        this.dropSelf(OresBlocks.RAW_LEAD_BLOCK.get());

        this.add(OresBlocks.TIN_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_TIN.get());
        });
        this.add(OresBlocks.DEEPSLATE_TIN_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_TIN.get());
        });
        this.add(OresBlocks.SILVER_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_SILVER.get());
        });
        this.add(OresBlocks.DEEPSLATE_SILVER_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_SILVER.get());
        });
        this.add(OresBlocks.ALUMINUM_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_ALUMINUM.get());
        });
        this.add(OresBlocks.DEEPSLATE_ALUMINUM_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_ALUMINUM.get());
        });
        this.add(OresBlocks.NICKEL_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_NICKEL.get());
        });
        this.add(OresBlocks.DEEPSLATE_NICKEL_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_NICKEL.get());
        });
        this.add(OresBlocks.PLATINUM_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_PLATINUM.get());
        });
        this.add(OresBlocks.DEEPSLATE_PLATINUM_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_PLATINUM.get());
        });
        this.add(OresBlocks.LEAD_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_LEAD.get());
        });
        this.add(OresBlocks.DEEPSLATE_LEAD_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RAW_LEAD.get());
        });

        this.add(OresBlocks.RUBY_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RUBY.get());
        });
        this.add(OresBlocks.DEEPSLATE_RUBY_ORE.get(), (ruby) -> {
            return createOreDrop(ruby, OresItems.RUBY.get());
        });
        this.add(OresBlocks.PERIDOT_ORE.get(), (amethyst) -> {
            return createOreDrop(amethyst, OresItems.PERIDOT.get());
        });
        this.add(OresBlocks.DEEPSLATE_PERIDOT_ORE.get(), (amethyst) -> {
            return createOreDrop(amethyst, OresItems.PERIDOT.get());
        });
        this.add(OresBlocks.SAPPHIRE_ORE.get(), (sapphire) -> {
            return createOreDrop(sapphire, OresItems.SAPPHIRE.get());
        });
        this.add(OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), (sapphire) -> {
            return createOreDrop(sapphire, OresItems.SAPPHIRE.get());
        });
        this.add(OresBlocks.TOPAZ_ORE.get(), (topaz) -> {
            return createOreDrop(topaz, OresItems.TOPAZ.get());
        });
        this.add(OresBlocks.DEEPSLATE_TOPAZ_ORE.get(), (topaz) -> {
            return createOreDrop(topaz, OresItems.TOPAZ.get());
        });
    }
}
