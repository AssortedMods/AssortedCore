package com.grim3212.assorted.machines.client.data;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.items.MachinesItems;
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

/** Item models for everything but block items, which {@link MachinesBlockstateProvider} models. */
public class MachinesItemModelProvider extends ModelProvider {

    public MachinesItemModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public String getName() {
        return "Assorted Machines item models";
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
        generatedItem(itemModels, MachinesItems.BRONZE_INGOT.get());
        generatedItem(itemModels, MachinesItems.ELECTRUM_INGOT.get());
        generatedItem(itemModels, MachinesItems.INVAR_INGOT.get());
        generatedItem(itemModels, MachinesItems.STEEL_INGOT.get());

        generatedItem(itemModels, MachinesItems.BRONZE_NUGGET.get());
        generatedItem(itemModels, MachinesItems.ELECTRUM_NUGGET.get());
        generatedItem(itemModels, MachinesItems.INVAR_NUGGET.get());
        generatedItem(itemModels, MachinesItems.STEEL_NUGGET.get());

        generatedItem(itemModels, MachinesItems.TIN_DUST.get());
        generatedItem(itemModels, MachinesItems.COPPER_DUST.get());
        generatedItem(itemModels, MachinesItems.SILVER_DUST.get());
        generatedItem(itemModels, MachinesItems.ALUMINUM_DUST.get());
        generatedItem(itemModels, MachinesItems.NICKEL_DUST.get());
        generatedItem(itemModels, MachinesItems.PLATINUM_DUST.get());
        generatedItem(itemModels, MachinesItems.LEAD_DUST.get());
        generatedItem(itemModels, MachinesItems.BRONZE_DUST.get());
        generatedItem(itemModels, MachinesItems.ELECTRUM_DUST.get());
        generatedItem(itemModels, MachinesItems.INVAR_DUST.get());
        generatedItem(itemModels, MachinesItems.STEEL_DUST.get());
        generatedItem(itemModels, MachinesItems.IRON_DUST.get());
        generatedItem(itemModels, MachinesItems.GOLD_DUST.get());

        generatedItem(itemModels, MachinesItems.TIN_GEAR.get());
        generatedItem(itemModels, MachinesItems.COPPER_GEAR.get());
        generatedItem(itemModels, MachinesItems.SILVER_GEAR.get());
        generatedItem(itemModels, MachinesItems.ALUMINUM_GEAR.get());
        generatedItem(itemModels, MachinesItems.NICKEL_GEAR.get());
        generatedItem(itemModels, MachinesItems.PLATINUM_GEAR.get());
        generatedItem(itemModels, MachinesItems.LEAD_GEAR.get());
        generatedItem(itemModels, MachinesItems.BRONZE_GEAR.get());
        generatedItem(itemModels, MachinesItems.ELECTRUM_GEAR.get());
        generatedItem(itemModels, MachinesItems.INVAR_GEAR.get());
        generatedItem(itemModels, MachinesItems.STEEL_GEAR.get());
        generatedItem(itemModels, MachinesItems.IRON_GEAR.get());
        generatedItem(itemModels, MachinesItems.GOLD_GEAR.get());

    }

    private void generatedItem(ItemModelGenerators itemModels, Item i) {
        itemModels.generateFlatItem(i, ModelTemplates.FLAT_ITEM);
    }
}
