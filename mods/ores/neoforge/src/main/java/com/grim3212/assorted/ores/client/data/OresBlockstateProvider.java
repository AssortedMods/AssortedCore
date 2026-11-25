package com.grim3212.assorted.ores.client.data;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Block states, block models and the block items' models. {@link OresItemModelProvider} owns every
 * other item, so the two never write the same file.
 */
public class OresBlockstateProvider extends BlockStateProvider {

    public OresBlockstateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Ores block states";
    }

    @Override
    protected void registerStatesAndModels() {
        this.cube(OresBlocks.TIN_ORE.get());
        this.cube(OresBlocks.SILVER_ORE.get());
        this.cube(OresBlocks.ALUMINUM_ORE.get());
        this.cube(OresBlocks.NICKEL_ORE.get());
        this.cube(OresBlocks.PLATINUM_ORE.get());
        this.cube(OresBlocks.LEAD_ORE.get());
        this.cube(OresBlocks.RUBY_ORE.get());
        this.cube(OresBlocks.PERIDOT_ORE.get());
        this.cube(OresBlocks.SAPPHIRE_ORE.get());
        this.cube(OresBlocks.TOPAZ_ORE.get());
        this.cube(OresBlocks.TIN_BLOCK.get());
        this.cube(OresBlocks.SILVER_BLOCK.get());
        this.cube(OresBlocks.ALUMINUM_BLOCK.get());
        this.cube(OresBlocks.NICKEL_BLOCK.get());
        this.cube(OresBlocks.PLATINUM_BLOCK.get());
        this.cube(OresBlocks.LEAD_BLOCK.get());
        this.cube(OresBlocks.RUBY_BLOCK.get());
        this.cube(OresBlocks.PERIDOT_BLOCK.get());
        this.cube(OresBlocks.SAPPHIRE_BLOCK.get());
        this.cube(OresBlocks.TOPAZ_BLOCK.get());

        this.cube(OresBlocks.DEEPSLATE_TIN_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_SILVER_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_NICKEL_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_PLATINUM_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_LEAD_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_RUBY_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_PERIDOT_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
        this.cube(OresBlocks.DEEPSLATE_TOPAZ_ORE.get());

        this.cube(OresBlocks.RAW_TIN_BLOCK.get());
        this.cube(OresBlocks.RAW_SILVER_BLOCK.get());
        this.cube(OresBlocks.RAW_ALUMINUM_BLOCK.get());
        this.cube(OresBlocks.RAW_NICKEL_BLOCK.get());
        this.cube(OresBlocks.RAW_PLATINUM_BLOCK.get());
        this.cube(OresBlocks.RAW_LEAD_BLOCK.get());
    }

    /** A cube with the block's own texture on every face, and an item model that is the block's. */
    private void cube(Block block) {
        this.simpleBlockWithItem(block, this.cubeAll(block));
    }
}
