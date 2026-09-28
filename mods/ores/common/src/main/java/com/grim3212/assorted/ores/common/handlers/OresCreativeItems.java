package com.grim3212.assorted.ores.common.handlers;

import com.grim3212.assorted.lib.core.creative.CreativeTabItems;
import com.grim3212.assorted.lib.core.creative.SharedCreativeTabs;
import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.ores.common.items.OresItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** This part's share of the Assorted Core tab, which every part asks for and the first to load registers. */
public class OresCreativeItems {

    public static final ResourceKey<CreativeModeTab> TAB = Families.tab(Constants.FAMILY_ID);

    private static List<ItemStack> getCreativeItems() {
        CreativeTabItems items = new CreativeTabItems();

        items.add(OresBlocks.TIN_ORE.get());
        items.add(OresBlocks.DEEPSLATE_TIN_ORE.get());
        items.add(OresBlocks.RAW_TIN_BLOCK.get());
        items.add(OresBlocks.TIN_BLOCK.get());
        items.add(OresItems.TIN_DUST.get());
        items.add(OresItems.TIN_NUGGET.get());
        items.add(OresItems.TIN_INGOT.get());
        items.add(OresItems.RAW_TIN.get());
        items.add(OresItems.TIN_GEAR.get());

        items.add(OresBlocks.SILVER_ORE.get());
        items.add(OresBlocks.DEEPSLATE_SILVER_ORE.get());
        items.add(OresBlocks.RAW_SILVER_BLOCK.get());
        items.add(OresBlocks.SILVER_BLOCK.get());
        items.add(OresItems.SILVER_DUST.get());
        items.add(OresItems.SILVER_NUGGET.get());
        items.add(OresItems.SILVER_INGOT.get());
        items.add(OresItems.RAW_SILVER.get());
        items.add(OresItems.SILVER_GEAR.get());

        items.add(OresBlocks.ALUMINUM_ORE.get());
        items.add(OresBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        items.add(OresBlocks.RAW_ALUMINUM_BLOCK.get());
        items.add(OresBlocks.ALUMINUM_BLOCK.get());
        items.add(OresItems.ALUMINUM_DUST.get());
        items.add(OresItems.ALUMINUM_NUGGET.get());
        items.add(OresItems.ALUMINUM_INGOT.get());
        items.add(OresItems.RAW_ALUMINUM.get());
        items.add(OresItems.ALUMINUM_GEAR.get());

        items.add(OresBlocks.NICKEL_ORE.get());
        items.add(OresBlocks.DEEPSLATE_NICKEL_ORE.get());
        items.add(OresBlocks.RAW_NICKEL_BLOCK.get());
        items.add(OresBlocks.NICKEL_BLOCK.get());
        items.add(OresItems.NICKEL_DUST.get());
        items.add(OresItems.NICKEL_NUGGET.get());
        items.add(OresItems.NICKEL_INGOT.get());
        items.add(OresItems.RAW_NICKEL.get());
        items.add(OresItems.NICKEL_GEAR.get());

        items.add(OresBlocks.PLATINUM_ORE.get());
        items.add(OresBlocks.DEEPSLATE_PLATINUM_ORE.get());
        items.add(OresBlocks.RAW_PLATINUM_BLOCK.get());
        items.add(OresBlocks.PLATINUM_BLOCK.get());
        items.add(OresItems.PLATINUM_DUST.get());
        items.add(OresItems.PLATINUM_NUGGET.get());
        items.add(OresItems.PLATINUM_INGOT.get());
        items.add(OresItems.RAW_PLATINUM.get());
        items.add(OresItems.PLATINUM_GEAR.get());

        items.add(OresBlocks.LEAD_ORE.get());
        items.add(OresBlocks.DEEPSLATE_LEAD_ORE.get());
        items.add(OresBlocks.RAW_LEAD_BLOCK.get());
        items.add(OresBlocks.LEAD_BLOCK.get());
        items.add(OresItems.LEAD_DUST.get());
        items.add(OresItems.LEAD_NUGGET.get());
        items.add(OresItems.LEAD_INGOT.get());
        items.add(OresItems.RAW_LEAD.get());
        items.add(OresItems.LEAD_GEAR.get());

        items.add(OresBlocks.RUBY_ORE.get());
        items.add(OresBlocks.DEEPSLATE_RUBY_ORE.get());
        items.add(OresBlocks.RUBY_BLOCK.get());
        items.add(OresItems.RUBY.get());

        items.add(OresBlocks.PERIDOT_ORE.get());
        items.add(OresBlocks.DEEPSLATE_PERIDOT_ORE.get());
        items.add(OresBlocks.PERIDOT_BLOCK.get());
        items.add(OresItems.PERIDOT.get());

        items.add(OresBlocks.SAPPHIRE_ORE.get());
        items.add(OresBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
        items.add(OresBlocks.SAPPHIRE_BLOCK.get());
        items.add(OresItems.SAPPHIRE.get());

        items.add(OresBlocks.TOPAZ_ORE.get());
        items.add(OresBlocks.DEEPSLATE_TOPAZ_ORE.get());
        items.add(OresBlocks.TOPAZ_BLOCK.get());
        items.add(OresItems.TOPAZ.get());

        items.add(OresItems.COPPER_NUGGET.get());

        return items.getItems();
    }

    public static void init() {
        // After the machines and before the alloys, as the tab was when this was all one mod.
        SharedCreativeTabs.add(TAB, 200, OresCreativeItems::getCreativeItems);
    }
}
