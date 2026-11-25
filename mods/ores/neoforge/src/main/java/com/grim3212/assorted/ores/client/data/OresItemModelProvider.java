package com.grim3212.assorted.ores.client.data;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.items.OresItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/** Item models for everything but block items, which {@link OresBlockstateProvider} models. */
public class OresItemModelProvider extends ItemModelProvider {

    public OresItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Ores item models";
    }

    @Override
    protected void registerModels() {
        this.basicItem(OresItems.TIN_INGOT.get());
        this.basicItem(OresItems.SILVER_INGOT.get());
        this.basicItem(OresItems.ALUMINUM_INGOT.get());
        this.basicItem(OresItems.NICKEL_INGOT.get());
        this.basicItem(OresItems.PLATINUM_INGOT.get());
        this.basicItem(OresItems.LEAD_INGOT.get());

        this.basicItem(OresItems.RAW_TIN.get());
        this.basicItem(OresItems.RAW_SILVER.get());
        this.basicItem(OresItems.RAW_ALUMINUM.get());
        this.basicItem(OresItems.RAW_NICKEL.get());
        this.basicItem(OresItems.RAW_PLATINUM.get());
        this.basicItem(OresItems.RAW_LEAD.get());

        this.basicItem(OresItems.TIN_NUGGET.get());
        this.basicItem(OresItems.COPPER_NUGGET.get());
        this.basicItem(OresItems.SILVER_NUGGET.get());
        this.basicItem(OresItems.ALUMINUM_NUGGET.get());
        this.basicItem(OresItems.NICKEL_NUGGET.get());
        this.basicItem(OresItems.PLATINUM_NUGGET.get());
        this.basicItem(OresItems.LEAD_NUGGET.get());

        this.basicItem(OresItems.RUBY.get());
        this.basicItem(OresItems.PERIDOT.get());
        this.basicItem(OresItems.SAPPHIRE.get());
        this.basicItem(OresItems.TOPAZ.get());

        this.basicItem(OresItems.TIN_DUST.get());
        this.basicItem(OresItems.SILVER_DUST.get());
        this.basicItem(OresItems.ALUMINUM_DUST.get());
        this.basicItem(OresItems.NICKEL_DUST.get());
        this.basicItem(OresItems.PLATINUM_DUST.get());
        this.basicItem(OresItems.LEAD_DUST.get());

        this.basicItem(OresItems.TIN_GEAR.get());
        this.basicItem(OresItems.SILVER_GEAR.get());
        this.basicItem(OresItems.ALUMINUM_GEAR.get());
        this.basicItem(OresItems.NICKEL_GEAR.get());
        this.basicItem(OresItems.PLATINUM_GEAR.get());
        this.basicItem(OresItems.LEAD_GEAR.get());
    }
}
