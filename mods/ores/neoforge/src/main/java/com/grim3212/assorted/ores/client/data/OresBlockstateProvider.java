package com.grim3212.assorted.ores.client.data;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.stream.Stream;

/**
 * Block states and block models. This owns every block and block item; {@link
 * OresItemModelProvider} owns the rest, so the two never write the same file.
 */
public class OresBlockstateProvider extends ModelProvider {

    public OresBlockstateProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public String getName() {
        return "Assorted Ores block states";
    }

    /**
     * Only the block items belong here; every other item is {@link OresItemModelProvider}'s, so the
     * two providers never write the same file.
     */
    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(holder -> holder.value() instanceof BlockItem);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(OresBlocks.TIN_ORE.get());
        blockModels.createTrivialCube(OresBlocks.SILVER_ORE.get());
        blockModels.createTrivialCube(OresBlocks.ALUMINUM_ORE.get());
        blockModels.createTrivialCube(OresBlocks.NICKEL_ORE.get());
        blockModels.createTrivialCube(OresBlocks.PLATINUM_ORE.get());
        blockModels.createTrivialCube(OresBlocks.LEAD_ORE.get());
        blockModels.createTrivialCube(OresBlocks.RUBY_ORE.get());
        blockModels.createTrivialCube(OresBlocks.PERIDOT_ORE.get());
        blockModels.createTrivialCube(OresBlocks.SAPPHIRE_ORE.get());
        blockModels.createTrivialCube(OresBlocks.TOPAZ_ORE.get());
        blockModels.createTrivialCube(OresBlocks.TIN_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.SILVER_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.ALUMINUM_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.NICKEL_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.PLATINUM_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.LEAD_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.RUBY_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.PERIDOT_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.SAPPHIRE_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.TOPAZ_BLOCK.get());

        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_TIN_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_SILVER_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_NICKEL_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_PLATINUM_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_LEAD_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_RUBY_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_PERIDOT_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
        blockModels.createTrivialCube(OresBlocks.DEEPSLATE_TOPAZ_ORE.get());

        blockModels.createTrivialCube(OresBlocks.RAW_TIN_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.RAW_SILVER_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.RAW_ALUMINUM_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.RAW_NICKEL_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.RAW_PLATINUM_BLOCK.get());
        blockModels.createTrivialCube(OresBlocks.RAW_LEAD_BLOCK.get());
    }
}
