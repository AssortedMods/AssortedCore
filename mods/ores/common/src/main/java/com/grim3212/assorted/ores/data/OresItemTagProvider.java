package com.grim3212.assorted.ores.data;

import com.grim3212.assorted.ores.api.OresTags;
import com.grim3212.assorted.ores.common.items.OresItems;
import com.grim3212.assorted.lib.data.LibItemTagProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class OresItemTagProvider extends LibItemTagProvider {

    public OresItemTagProvider(PackOutput output, CompletableFuture<Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookup, blockTags);
    }

    @Override
    public void addCommonTags(Function<TagKey<Item>, TagAppender<Item>> appender, BiConsumer<TagKey<Block>, TagKey<Item>> copier) {
        // See OresBlockTagProvider: TagAppender only accepts ResourceKeys now.
        Function<TagKey<Item>, ItemTagger> tagger = (tag) -> new ItemTagger(appender.apply(tag));

        copier.accept(OresTags.Blocks.ORES, OresTags.Items.ORES);
        copier.accept(OresTags.Blocks.ORES_TIN, OresTags.Items.ORES_TIN);
        copier.accept(OresTags.Blocks.ORES_COPPER, OresTags.Items.ORES_COPPER);
        copier.accept(OresTags.Blocks.ORES_SILVER, OresTags.Items.ORES_SILVER);
        copier.accept(OresTags.Blocks.ORES_ALUMINUM, OresTags.Items.ORES_ALUMINUM);
        copier.accept(OresTags.Blocks.ORES_NICKEL, OresTags.Items.ORES_NICKEL);
        copier.accept(OresTags.Blocks.ORES_PLATINUM, OresTags.Items.ORES_PLATINUM);
        copier.accept(OresTags.Blocks.ORES_LEAD, OresTags.Items.ORES_LEAD);
        copier.accept(OresTags.Blocks.ORES_RUBY, OresTags.Items.ORES_RUBY);
        copier.accept(OresTags.Blocks.ORES_PERIDOT, OresTags.Items.ORES_PERIDOT);
        copier.accept(OresTags.Blocks.ORES_SAPPHIRE, OresTags.Items.ORES_SAPPHIRE);
        copier.accept(OresTags.Blocks.ORES_TOPAZ, OresTags.Items.ORES_TOPAZ);

        copier.accept(OresTags.Blocks.STORAGE_BLOCKS, OresTags.Items.STORAGE_BLOCKS);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_TIN, OresTags.Items.STORAGE_BLOCKS_TIN);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_COPPER, OresTags.Items.STORAGE_BLOCKS_COPPER);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_SILVER, OresTags.Items.STORAGE_BLOCKS_SILVER);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_ALUMINUM, OresTags.Items.STORAGE_BLOCKS_ALUMINUM);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_NICKEL, OresTags.Items.STORAGE_BLOCKS_NICKEL);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_PLATINUM, OresTags.Items.STORAGE_BLOCKS_PLATINUM);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_LEAD, OresTags.Items.STORAGE_BLOCKS_LEAD);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_RUBY, OresTags.Items.STORAGE_BLOCKS_RUBY);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_PERIDOT, OresTags.Items.STORAGE_BLOCKS_PERIDOT);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_SAPPHIRE, OresTags.Items.STORAGE_BLOCKS_SAPPHIRE);
        copier.accept(OresTags.Blocks.STORAGE_BLOCKS_TOPAZ, OresTags.Items.STORAGE_BLOCKS_TOPAZ);

        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_TIN, OresTags.Items.RAW_STORAGE_BLOCKS_TIN);
        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_COPPER, OresTags.Items.RAW_STORAGE_BLOCKS_COPPER);
        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_SILVER, OresTags.Items.RAW_STORAGE_BLOCKS_SILVER);
        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_ALUMINUM, OresTags.Items.RAW_STORAGE_BLOCKS_ALUMINUM);
        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_NICKEL, OresTags.Items.RAW_STORAGE_BLOCKS_NICKEL);
        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_PLATINUM, OresTags.Items.RAW_STORAGE_BLOCKS_PLATINUM);
        copier.accept(OresTags.Blocks.RAW_STORAGE_BLOCKS_LEAD, OresTags.Items.RAW_STORAGE_BLOCKS_LEAD);

        tagger.apply(OresTags.Items.GEMS).add(OresItems.RUBY.get());
        tagger.apply(OresTags.Items.GEMS).add(OresItems.PERIDOT.get());
        tagger.apply(OresTags.Items.GEMS).add(OresItems.SAPPHIRE.get());
        tagger.apply(OresTags.Items.GEMS).add(OresItems.TOPAZ.get());
        tagger.apply(OresTags.Items.GEMS_RUBY).add(OresItems.RUBY.get());
        tagger.apply(OresTags.Items.GEMS_PERIDOT).add(OresItems.PERIDOT.get());
        tagger.apply(OresTags.Items.GEMS_SAPPHIRE).add(OresItems.SAPPHIRE.get());
        tagger.apply(OresTags.Items.GEMS_TOPAZ).add(OresItems.TOPAZ.get());

        tagger.apply(OresTags.Items.RAW_MATERIALS).add(OresItems.RAW_ALUMINUM.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(OresItems.RAW_LEAD.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(OresItems.RAW_NICKEL.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(OresItems.RAW_PLATINUM.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(OresItems.RAW_SILVER.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(OresItems.RAW_TIN.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(Items.RAW_IRON);
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(Items.RAW_GOLD);
        tagger.apply(OresTags.Items.RAW_MATERIALS).add(Items.RAW_COPPER);
        tagger.apply(OresTags.Items.RAW_MATERIALS_ALUMINUM).add(OresItems.RAW_ALUMINUM.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS_LEAD).add(OresItems.RAW_LEAD.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS_NICKEL).add(OresItems.RAW_NICKEL.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS_PLATINUM).add(OresItems.RAW_PLATINUM.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS_SILVER).add(OresItems.RAW_SILVER.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS_TIN).add(OresItems.RAW_TIN.get());
        tagger.apply(OresTags.Items.RAW_MATERIALS_IRON).add(Items.RAW_IRON);
        tagger.apply(OresTags.Items.RAW_MATERIALS_GOLD).add(Items.RAW_GOLD);
        tagger.apply(OresTags.Items.RAW_MATERIALS_COPPER).add(Items.RAW_COPPER);

        tagger.apply(OresTags.Items.INGOTS).add(OresItems.TIN_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS).add(Items.COPPER_INGOT);
        tagger.apply(OresTags.Items.INGOTS).add(OresItems.SILVER_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS).add(OresItems.ALUMINUM_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS).add(OresItems.NICKEL_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS).add(OresItems.PLATINUM_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS).add(OresItems.LEAD_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS_TIN).add(OresItems.TIN_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
        tagger.apply(OresTags.Items.INGOTS_SILVER).add(OresItems.SILVER_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS_ALUMINUM).add(OresItems.ALUMINUM_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS_NICKEL).add(OresItems.NICKEL_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS_PLATINUM).add(OresItems.PLATINUM_INGOT.get());
        tagger.apply(OresTags.Items.INGOTS_LEAD).add(OresItems.LEAD_INGOT.get());

        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.TIN_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.COPPER_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.SILVER_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.ALUMINUM_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.NICKEL_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.PLATINUM_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS).add(OresItems.LEAD_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_TIN).add(OresItems.TIN_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_COPPER).add(OresItems.COPPER_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_SILVER).add(OresItems.SILVER_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_ALUMINUM).add(OresItems.ALUMINUM_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_NICKEL).add(OresItems.NICKEL_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_PLATINUM).add(OresItems.PLATINUM_NUGGET.get());
        tagger.apply(OresTags.Items.NUGGETS_LEAD).add(OresItems.LEAD_NUGGET.get());

        tagger.apply(OresTags.Items.DUSTS).add(OresItems.TIN_DUST.get());
        tagger.apply(OresTags.Items.DUSTS).add(OresItems.SILVER_DUST.get());
        tagger.apply(OresTags.Items.DUSTS).add(OresItems.ALUMINUM_DUST.get());
        tagger.apply(OresTags.Items.DUSTS).add(OresItems.NICKEL_DUST.get());
        tagger.apply(OresTags.Items.DUSTS).add(OresItems.PLATINUM_DUST.get());
        tagger.apply(OresTags.Items.DUSTS).add(OresItems.LEAD_DUST.get());
        tagger.apply(OresTags.Items.DUSTS_TIN).add(OresItems.TIN_DUST.get());
        tagger.apply(OresTags.Items.DUSTS_SILVER).add(OresItems.SILVER_DUST.get());
        tagger.apply(OresTags.Items.DUSTS_ALUMINUM).add(OresItems.ALUMINUM_DUST.get());
        tagger.apply(OresTags.Items.DUSTS_NICKEL).add(OresItems.NICKEL_DUST.get());
        tagger.apply(OresTags.Items.DUSTS_PLATINUM).add(OresItems.PLATINUM_DUST.get());
        tagger.apply(OresTags.Items.DUSTS_LEAD).add(OresItems.LEAD_DUST.get());

        tagger.apply(OresTags.Items.GEARS).add(OresItems.TIN_GEAR.get());
        tagger.apply(OresTags.Items.GEARS).add(OresItems.SILVER_GEAR.get());
        tagger.apply(OresTags.Items.GEARS).add(OresItems.ALUMINUM_GEAR.get());
        tagger.apply(OresTags.Items.GEARS).add(OresItems.NICKEL_GEAR.get());
        tagger.apply(OresTags.Items.GEARS).add(OresItems.PLATINUM_GEAR.get());
        tagger.apply(OresTags.Items.GEARS).add(OresItems.LEAD_GEAR.get());
        tagger.apply(OresTags.Items.GEARS_TIN).add(OresItems.TIN_GEAR.get());
        tagger.apply(OresTags.Items.GEARS_SILVER).add(OresItems.SILVER_GEAR.get());
        tagger.apply(OresTags.Items.GEARS_ALUMINUM).add(OresItems.ALUMINUM_GEAR.get());
        tagger.apply(OresTags.Items.GEARS_NICKEL).add(OresItems.NICKEL_GEAR.get());
        tagger.apply(OresTags.Items.GEARS_PLATINUM).add(OresItems.PLATINUM_GEAR.get());
        tagger.apply(OresTags.Items.GEARS_LEAD).add(OresItems.LEAD_GEAR.get());
    }

    private record ItemTagger(TagAppender<Item> appender) {

        ItemTagger add(Item... items) {
            for (Item item : items) {
                this.appender.add(BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow());
            }

            return this;
        }

        ItemTagger addTag(TagKey<Item> tag) {
            this.appender.addTag(tag);
            return this;
        }
    }
}
