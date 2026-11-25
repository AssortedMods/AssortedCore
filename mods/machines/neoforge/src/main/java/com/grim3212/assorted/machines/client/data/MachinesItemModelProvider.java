package com.grim3212.assorted.machines.client.data;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/** Item models for everything but block items, which {@link MachinesBlockstateProvider} models. */
public class MachinesItemModelProvider extends ItemModelProvider {

    public MachinesItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Machines item models";
    }

    @Override
    protected void registerModels() {
        generatedItem(MachinesItems.BRONZE_INGOT.get());
        generatedItem(MachinesItems.ELECTRUM_INGOT.get());
        generatedItem(MachinesItems.INVAR_INGOT.get());
        generatedItem(MachinesItems.STEEL_INGOT.get());

        generatedItem(MachinesItems.BRONZE_NUGGET.get());
        generatedItem(MachinesItems.ELECTRUM_NUGGET.get());
        generatedItem(MachinesItems.INVAR_NUGGET.get());
        generatedItem(MachinesItems.STEEL_NUGGET.get());

        generatedItem(MachinesItems.COPPER_DUST.get());
        generatedItem(MachinesItems.BRONZE_DUST.get());
        generatedItem(MachinesItems.ELECTRUM_DUST.get());
        generatedItem(MachinesItems.INVAR_DUST.get());
        generatedItem(MachinesItems.STEEL_DUST.get());
        generatedItem(MachinesItems.IRON_DUST.get());
        generatedItem(MachinesItems.GOLD_DUST.get());

        generatedItem(MachinesItems.COPPER_GEAR.get());
        generatedItem(MachinesItems.BRONZE_GEAR.get());
        generatedItem(MachinesItems.ELECTRUM_GEAR.get());
        generatedItem(MachinesItems.INVAR_GEAR.get());
        generatedItem(MachinesItems.STEEL_GEAR.get());
        generatedItem(MachinesItems.IRON_GEAR.get());
        generatedItem(MachinesItems.GOLD_GEAR.get());
    }

    private void generatedItem(Item i) {
        this.basicItem(i);
    }
}
