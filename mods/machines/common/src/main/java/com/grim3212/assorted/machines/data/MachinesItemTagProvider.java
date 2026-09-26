package com.grim3212.assorted.machines.data;

import com.grim3212.assorted.machines.api.MachinesTags;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import com.grim3212.assorted.lib.data.LibItemTagProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MachinesItemTagProvider extends LibItemTagProvider {

    public MachinesItemTagProvider(PackOutput output, CompletableFuture<Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookup, blockTags);
    }

    @Override
    public void addCommonTags(Function<TagKey<Item>, TagAppender<Item>> appender, BiConsumer<TagKey<Block>, TagKey<Item>> copier) {
        // See MachinesBlockTagProvider: TagAppender only accepts ResourceKeys now.
        Function<TagKey<Item>, ItemTagger> tagger = (tag) -> new ItemTagger(appender.apply(tag));

        tagger.apply(MachinesTags.Items.GRINDING_MILL_ALLOWED_TOOLS).add(Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);

        copier.accept(MachinesTags.Blocks.STORAGE_BLOCKS, MachinesTags.Items.STORAGE_BLOCKS);
        copier.accept(MachinesTags.Blocks.STORAGE_BLOCKS_BRONZE, MachinesTags.Items.STORAGE_BLOCKS_BRONZE);
        copier.accept(MachinesTags.Blocks.STORAGE_BLOCKS_ELECTRUM, MachinesTags.Items.STORAGE_BLOCKS_ELECTRUM);
        copier.accept(MachinesTags.Blocks.STORAGE_BLOCKS_INVAR, MachinesTags.Items.STORAGE_BLOCKS_INVAR);
        copier.accept(MachinesTags.Blocks.STORAGE_BLOCKS_STEEL, MachinesTags.Items.STORAGE_BLOCKS_STEEL);

        tagger.apply(MachinesTags.Items.INGOTS).add(MachinesItems.BRONZE_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS).add(MachinesItems.ELECTRUM_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS).add(MachinesItems.INVAR_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS).add(MachinesItems.STEEL_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS_BRONZE).add(MachinesItems.BRONZE_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS_ELECTRUM).add(MachinesItems.ELECTRUM_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS_INVAR).add(MachinesItems.INVAR_INGOT.get());
        tagger.apply(MachinesTags.Items.INGOTS_STEEL).add(MachinesItems.STEEL_INGOT.get());

        tagger.apply(MachinesTags.Items.NUGGETS).add(MachinesItems.BRONZE_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS).add(MachinesItems.ELECTRUM_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS).add(MachinesItems.INVAR_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS).add(MachinesItems.STEEL_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS_BRONZE).add(MachinesItems.BRONZE_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS_ELECTRUM).add(MachinesItems.ELECTRUM_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS_INVAR).add(MachinesItems.INVAR_NUGGET.get());
        tagger.apply(MachinesTags.Items.NUGGETS_STEEL).add(MachinesItems.STEEL_NUGGET.get());

        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.TIN_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.COPPER_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.SILVER_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.ALUMINUM_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.NICKEL_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.PLATINUM_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.LEAD_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.BRONZE_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.ELECTRUM_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.INVAR_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.STEEL_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.IRON_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS).add(MachinesItems.GOLD_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_TIN).add(MachinesItems.TIN_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_COPPER).add(MachinesItems.COPPER_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_SILVER).add(MachinesItems.SILVER_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_ALUMINUM).add(MachinesItems.ALUMINUM_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_NICKEL).add(MachinesItems.NICKEL_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_PLATINUM).add(MachinesItems.PLATINUM_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_LEAD).add(MachinesItems.LEAD_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_BRONZE).add(MachinesItems.BRONZE_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_ELECTRUM).add(MachinesItems.ELECTRUM_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_INVAR).add(MachinesItems.INVAR_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_STEEL).add(MachinesItems.STEEL_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_IRON).add(MachinesItems.IRON_DUST.get());
        tagger.apply(MachinesTags.Items.DUSTS_GOLD).add(MachinesItems.GOLD_DUST.get());

        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.TIN_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.COPPER_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.SILVER_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.ALUMINUM_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.NICKEL_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.PLATINUM_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.LEAD_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.BRONZE_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.ELECTRUM_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.INVAR_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.STEEL_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.IRON_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS).add(MachinesItems.GOLD_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_TIN).add(MachinesItems.TIN_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_COPPER).add(MachinesItems.COPPER_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_SILVER).add(MachinesItems.SILVER_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_ALUMINUM).add(MachinesItems.ALUMINUM_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_NICKEL).add(MachinesItems.NICKEL_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_PLATINUM).add(MachinesItems.PLATINUM_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_LEAD).add(MachinesItems.LEAD_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_BRONZE).add(MachinesItems.BRONZE_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_ELECTRUM).add(MachinesItems.ELECTRUM_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_INVAR).add(MachinesItems.INVAR_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_STEEL).add(MachinesItems.STEEL_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_IRON).add(MachinesItems.IRON_GEAR.get());
        tagger.apply(MachinesTags.Items.GEARS_GOLD).add(MachinesItems.GOLD_GEAR.get());

        tagger.apply(ItemTags.PIGLIN_LOVED).add(MachinesItems.GOLD_DUST.get(), MachinesItems.GOLD_GEAR.get());
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
