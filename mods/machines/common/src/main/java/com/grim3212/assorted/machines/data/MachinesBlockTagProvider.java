package com.grim3212.assorted.machines.data;

import com.grim3212.assorted.machines.api.MachinesTags;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.lib.data.LibBlockTagProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class MachinesBlockTagProvider extends LibBlockTagProvider {

    public MachinesBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOutput, lookup);
    }

    @Override
    public void addCommonTags(Function<TagKey<Block>, TagAppender<Block>> appender) {
        // The intrinsic tag appender is gone; TagAppender only accepts ResourceKeys. Wrap it back
        // into something that takes blocks so the tag lists below stay readable.
        Function<TagKey<Block>, BlockTagger> tagger = (tag) -> new BlockTagger(appender.apply(tag));

        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(MachinesBlocks.BRONZE_BLOCK.get(), MachinesBlocks.ELECTRUM_BLOCK.get(), MachinesBlocks.INVAR_BLOCK.get(), MachinesBlocks.STEEL_BLOCK.get(), MachinesBlocks.MACHINE_CORE.get(), MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(), MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachinesBlocks.EXPERT_ALLOY_FORGE.get(), MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(), MachinesBlocks.ADVANCED_GRINDING_MILL.get(),
                MachinesBlocks.EXPERT_GRINDING_MILL.get());

        tagger.apply(BlockTags.NEEDS_STONE_TOOL).add(MachinesBlocks.MACHINE_CORE.get(), MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(), MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachinesBlocks.EXPERT_ALLOY_FORGE.get(), MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(), MachinesBlocks.ADVANCED_GRINDING_MILL.get(),
                MachinesBlocks.EXPERT_GRINDING_MILL.get());
        tagger.apply(BlockTags.NEEDS_IRON_TOOL).add(MachinesBlocks.BRONZE_BLOCK.get(), MachinesBlocks.ELECTRUM_BLOCK.get(), MachinesBlocks.INVAR_BLOCK.get(), MachinesBlocks.STEEL_BLOCK.get());

        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS).add(MachinesBlocks.BRONZE_BLOCK.get());
        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS).add(MachinesBlocks.ELECTRUM_BLOCK.get());
        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS).add(MachinesBlocks.INVAR_BLOCK.get());
        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS).add(MachinesBlocks.STEEL_BLOCK.get());

        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS_BRONZE).add(MachinesBlocks.BRONZE_BLOCK.get());
        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS_ELECTRUM).add(MachinesBlocks.ELECTRUM_BLOCK.get());
        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS_INVAR).add(MachinesBlocks.INVAR_BLOCK.get());
        tagger.apply(MachinesTags.Blocks.STORAGE_BLOCKS_STEEL).add(MachinesBlocks.STEEL_BLOCK.get());
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
