package com.grim3212.assorted.ores.client.data;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.items.OresItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

/** Item models for everything but block items, which {@link OresBlockstateProvider} models. */
public class OresItemModelProvider extends ModelProvider {

    public OresItemModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public String getName() {
        return "Assorted Ores item models";
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(holder -> !(holder.value() instanceof BlockItem));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generatedItem(itemModels, OresItems.TIN_INGOT.get());
        generatedItem(itemModels, OresItems.SILVER_INGOT.get());
        generatedItem(itemModels, OresItems.ALUMINUM_INGOT.get());
        generatedItem(itemModels, OresItems.NICKEL_INGOT.get());
        generatedItem(itemModels, OresItems.PLATINUM_INGOT.get());
        generatedItem(itemModels, OresItems.LEAD_INGOT.get());

        generatedItem(itemModels, OresItems.RAW_TIN.get());
        generatedItem(itemModels, OresItems.RAW_SILVER.get());
        generatedItem(itemModels, OresItems.RAW_ALUMINUM.get());
        generatedItem(itemModels, OresItems.RAW_NICKEL.get());
        generatedItem(itemModels, OresItems.RAW_PLATINUM.get());
        generatedItem(itemModels, OresItems.RAW_LEAD.get());

        generatedItem(itemModels, OresItems.TIN_NUGGET.get());
        generatedItem(itemModels, OresItems.SILVER_NUGGET.get());
        generatedItem(itemModels, OresItems.ALUMINUM_NUGGET.get());
        generatedItem(itemModels, OresItems.NICKEL_NUGGET.get());
        generatedItem(itemModels, OresItems.PLATINUM_NUGGET.get());
        generatedItem(itemModels, OresItems.LEAD_NUGGET.get());

        generatedItem(itemModels, OresItems.RUBY.get());
        generatedItem(itemModels, OresItems.PERIDOT.get());
        generatedItem(itemModels, OresItems.SAPPHIRE.get());
        generatedItem(itemModels, OresItems.TOPAZ.get());

        generatedItem(itemModels, OresItems.TIN_DUST.get());
        generatedItem(itemModels, OresItems.SILVER_DUST.get());
        generatedItem(itemModels, OresItems.ALUMINUM_DUST.get());
        generatedItem(itemModels, OresItems.NICKEL_DUST.get());
        generatedItem(itemModels, OresItems.PLATINUM_DUST.get());
        generatedItem(itemModels, OresItems.LEAD_DUST.get());

        generatedItem(itemModels, OresItems.TIN_GEAR.get());
        generatedItem(itemModels, OresItems.SILVER_GEAR.get());
        generatedItem(itemModels, OresItems.ALUMINUM_GEAR.get());
        generatedItem(itemModels, OresItems.NICKEL_GEAR.get());
        generatedItem(itemModels, OresItems.PLATINUM_GEAR.get());
        generatedItem(itemModels, OresItems.LEAD_GEAR.get());
    }

    private void generatedItem(ItemModelGenerators itemModels, Item i) {
        itemModels.generateFlatItem(i, ModelTemplates.FLAT_ITEM);
    }
}
