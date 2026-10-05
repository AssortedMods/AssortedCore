package com.grim3212.assorted.ores.data;

import com.grim3212.assorted.ores.api.OresTags;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.lib.data.LibBlockTagProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class OresBlockTagProvider extends LibBlockTagProvider {

    public OresBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOutput, lookup);
    }

    @Override
    public void addCommonTags(Function<TagKey<Block>, TagAppender<Block>> appender) {
        // The intrinsic tag appender is gone; TagAppender only accepts ResourceKeys. Wrap it back
        // into something that takes blocks so the tag lists below stay readable.
        Function<TagKey<Block>, BlockTagger> tagger = (tag) -> new BlockTagger(appender.apply(tag));

        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(OresBlocks.TIN_ORE.get(), OresBlocks.SILVER_ORE.get(), OresBlocks.ALUMINUM_ORE.get(), OresBlocks.NICKEL_ORE.get(), OresBlocks.PLATINUM_ORE.get(), OresBlocks.LEAD_ORE.get(), OresBlocks.RUBY_ORE.get(), OresBlocks.PERIDOT_ORE.get(), OresBlocks.SAPPHIRE_ORE.get(), OresBlocks.TOPAZ_ORE.get());
        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(OresBlocks.DEEPSLATE_TIN_ORE.get(), OresBlocks.DEEPSLATE_SILVER_ORE.get(), OresBlocks.DEEPSLATE_ALUMINUM_ORE.get(), OresBlocks.DEEPSLATE_NICKEL_ORE.get(), OresBlocks.DEEPSLATE_PLATINUM_ORE.get(), OresBlocks.DEEPSLATE_LEAD_ORE.get(), OresBlocks.DEEPSLATE_RUBY_ORE.get(), OresBlocks.DEEPSLATE_PERIDOT_ORE.get(), OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), OresBlocks.DEEPSLATE_TOPAZ_ORE.get());
        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(OresBlocks.TIN_BLOCK.get(), OresBlocks.SILVER_BLOCK.get(), OresBlocks.ALUMINUM_BLOCK.get(), OresBlocks.NICKEL_BLOCK.get(), OresBlocks.PLATINUM_BLOCK.get(), OresBlocks.LEAD_BLOCK.get(), OresBlocks.RUBY_BLOCK.get(), OresBlocks.PERIDOT_BLOCK.get(), OresBlocks.SAPPHIRE_BLOCK.get(), OresBlocks.TOPAZ_BLOCK.get());
        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(OresBlocks.RAW_TIN_BLOCK.get(), OresBlocks.RAW_SILVER_BLOCK.get(), OresBlocks.RAW_ALUMINUM_BLOCK.get(), OresBlocks.RAW_NICKEL_BLOCK.get(), OresBlocks.RAW_PLATINUM_BLOCK.get(), OresBlocks.RAW_LEAD_BLOCK.get());

        tagger.apply(BlockTags.NEEDS_STONE_TOOL).add(OresBlocks.TIN_ORE.get(), OresBlocks.TIN_BLOCK.get(), OresBlocks.ALUMINUM_ORE.get(), OresBlocks.ALUMINUM_BLOCK.get(), OresBlocks.DEEPSLATE_TIN_ORE.get(), OresBlocks.RAW_TIN_BLOCK.get(), OresBlocks.DEEPSLATE_ALUMINUM_ORE.get(), OresBlocks.RAW_ALUMINUM_BLOCK.get());
        tagger.apply(BlockTags.NEEDS_IRON_TOOL).add(OresBlocks.SILVER_ORE.get(), OresBlocks.SILVER_BLOCK.get(), OresBlocks.NICKEL_ORE.get(), OresBlocks.NICKEL_BLOCK.get(), OresBlocks.LEAD_ORE.get(), OresBlocks.RUBY_ORE.get(), OresBlocks.PERIDOT_ORE.get(), OresBlocks.SAPPHIRE_ORE.get(), OresBlocks.TOPAZ_ORE.get(), OresBlocks.LEAD_BLOCK.get(), OresBlocks.RUBY_BLOCK.get(), OresBlocks.PERIDOT_BLOCK.get(), OresBlocks.SAPPHIRE_BLOCK.get(), OresBlocks.TOPAZ_BLOCK.get(), OresBlocks.DEEPSLATE_SILVER_ORE.get(), OresBlocks.RAW_SILVER_BLOCK.get(), OresBlocks.DEEPSLATE_NICKEL_ORE.get(), OresBlocks.RAW_NICKEL_BLOCK.get(), OresBlocks.DEEPSLATE_LEAD_ORE.get(), OresBlocks.RAW_LEAD_BLOCK.get(), OresBlocks.DEEPSLATE_RUBY_ORE.get(), OresBlocks.DEEPSLATE_PERIDOT_ORE.get(), OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), OresBlocks.DEEPSLATE_TOPAZ_ORE.get());
        tagger.apply(BlockTags.NEEDS_DIAMOND_TOOL).add(OresBlocks.PLATINUM_ORE.get(), OresBlocks.PLATINUM_BLOCK.get(), OresBlocks.DEEPSLATE_PLATINUM_ORE.get(), OresBlocks.RAW_PLATINUM_BLOCK.get());

        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_TIN);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_COPPER);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_SILVER);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_ALUMINUM);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_NICKEL);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_PLATINUM);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_LEAD);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_RUBY);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_PERIDOT);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_SAPPHIRE);
        tagger.apply(OresTags.Blocks.ORES).addTag(OresTags.Blocks.ORES_TOPAZ);

        tagger.apply(OresTags.Blocks.ORES_TIN).add(OresBlocks.TIN_ORE.get(), OresBlocks.DEEPSLATE_TIN_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_COPPER).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE);
        tagger.apply(OresTags.Blocks.ORES_SILVER).add(OresBlocks.SILVER_ORE.get(), OresBlocks.DEEPSLATE_SILVER_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_ALUMINUM).add(OresBlocks.ALUMINUM_ORE.get(), OresBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_NICKEL).add(OresBlocks.NICKEL_ORE.get(), OresBlocks.DEEPSLATE_NICKEL_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_PLATINUM).add(OresBlocks.PLATINUM_ORE.get(), OresBlocks.DEEPSLATE_PLATINUM_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_LEAD).add(OresBlocks.LEAD_ORE.get(), OresBlocks.DEEPSLATE_LEAD_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_RUBY).add(OresBlocks.RUBY_ORE.get(), OresBlocks.DEEPSLATE_RUBY_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_PERIDOT).add(OresBlocks.PERIDOT_ORE.get(), OresBlocks.DEEPSLATE_PERIDOT_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_SAPPHIRE).add(OresBlocks.SAPPHIRE_ORE.get(), OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
        tagger.apply(OresTags.Blocks.ORES_TOPAZ).add(OresBlocks.TOPAZ_ORE.get(), OresBlocks.DEEPSLATE_TOPAZ_ORE.get());

        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.TIN_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(Blocks.COPPER_BLOCK.weathering().unaffected());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.SILVER_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.ALUMINUM_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.NICKEL_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.PLATINUM_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.LEAD_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.RUBY_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.PERIDOT_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.SAPPHIRE_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(OresBlocks.TOPAZ_BLOCK.get());

        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_TIN).add(OresBlocks.TIN_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_COPPER).add(Blocks.COPPER_BLOCK.weathering().unaffected());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_SILVER).add(OresBlocks.SILVER_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_ALUMINUM).add(OresBlocks.ALUMINUM_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_NICKEL).add(OresBlocks.NICKEL_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_PLATINUM).add(OresBlocks.PLATINUM_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_LEAD).add(OresBlocks.LEAD_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_RUBY).add(OresBlocks.RUBY_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_PERIDOT).add(OresBlocks.PERIDOT_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_SAPPHIRE).add(OresBlocks.SAPPHIRE_BLOCK.get());
        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(OresBlocks.TOPAZ_BLOCK.get());

        tagger.apply(OresTags.Blocks.STORAGE_BLOCKS).add(Blocks.RAW_COPPER_BLOCK, OresBlocks.RAW_TIN_BLOCK.get(), OresBlocks.RAW_SILVER_BLOCK.get(), OresBlocks.RAW_ALUMINUM_BLOCK.get(), OresBlocks.RAW_NICKEL_BLOCK.get(), OresBlocks.RAW_PLATINUM_BLOCK.get(), OresBlocks.RAW_LEAD_BLOCK.get());
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_TIN).add(OresBlocks.RAW_TIN_BLOCK.get());
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_COPPER).add(Blocks.RAW_COPPER_BLOCK);
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_SILVER).add(OresBlocks.RAW_SILVER_BLOCK.get());
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_ALUMINUM).add(OresBlocks.RAW_ALUMINUM_BLOCK.get());
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_NICKEL).add(OresBlocks.RAW_NICKEL_BLOCK.get());
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_PLATINUM).add(OresBlocks.RAW_PLATINUM_BLOCK.get());
        tagger.apply(OresTags.Blocks.RAW_STORAGE_BLOCKS_LEAD).add(OresBlocks.RAW_LEAD_BLOCK.get());
    }

    private record BlockTagger(TagAppender<Block> appender) {

        BlockTagger add(Block... blocks) {
            for (Block block : blocks) {
                this.appender.add(BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow());
            }

            return this;
        }

        BlockTagger addTag(TagKey<Block> tag) {
            this.appender.addTag(tag);
            return this;
        }
    }
}
