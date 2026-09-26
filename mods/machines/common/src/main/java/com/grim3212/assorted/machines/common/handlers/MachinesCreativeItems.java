package com.grim3212.assorted.machines.common.handlers;

import com.grim3212.assorted.lib.core.creative.CreativeTabItems;
import com.grim3212.assorted.lib.core.creative.SharedCreativeTabs;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** This part's share of the Assorted Core tab, which every part asks for and the first to load registers. */
public class MachinesCreativeItems {

    public static final ResourceKey<CreativeModeTab> TAB = SharedCreativeTabs.tab(Identifier.fromNamespaceAndPath(Family.ID, "tab"), Family.ICONS);

    private static List<ItemStack> getMachines() {
        CreativeTabItems items = new CreativeTabItems();

        items.add(MachinesBlocks.BASIC_GRINDING_MILL.get());
        items.add(MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get());
        items.add(MachinesBlocks.ADVANCED_GRINDING_MILL.get());
        items.add(MachinesBlocks.EXPERT_GRINDING_MILL.get());

        items.add(MachinesBlocks.BASIC_ALLOY_FORGE.get());
        items.add(MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get());
        items.add(MachinesBlocks.ADVANCED_ALLOY_FORGE.get());
        items.add(MachinesBlocks.EXPERT_ALLOY_FORGE.get());

        items.add(MachinesBlocks.MACHINE_CORE.get());

        return items.getItems();
    }

    private static List<ItemStack> getMetals() {
        CreativeTabItems items = new CreativeTabItems();

        items.add(MachinesItems.TIN_DUST.get());
        items.add(MachinesItems.TIN_GEAR.get());
        items.add(MachinesItems.SILVER_DUST.get());
        items.add(MachinesItems.SILVER_GEAR.get());
        items.add(MachinesItems.ALUMINUM_DUST.get());
        items.add(MachinesItems.ALUMINUM_GEAR.get());
        items.add(MachinesItems.NICKEL_DUST.get());
        items.add(MachinesItems.NICKEL_GEAR.get());
        items.add(MachinesItems.PLATINUM_DUST.get());
        items.add(MachinesItems.PLATINUM_GEAR.get());
        items.add(MachinesItems.LEAD_DUST.get());
        items.add(MachinesItems.LEAD_GEAR.get());

        items.add(MachinesBlocks.BRONZE_BLOCK.get());
        items.add(MachinesItems.BRONZE_DUST.get());
        items.add(MachinesItems.BRONZE_NUGGET.get());
        items.add(MachinesItems.BRONZE_INGOT.get());
        items.add(MachinesItems.BRONZE_GEAR.get());

        items.add(MachinesBlocks.ELECTRUM_BLOCK.get());
        items.add(MachinesItems.ELECTRUM_DUST.get());
        items.add(MachinesItems.ELECTRUM_NUGGET.get());
        items.add(MachinesItems.ELECTRUM_INGOT.get());
        items.add(MachinesItems.ELECTRUM_GEAR.get());

        items.add(MachinesBlocks.INVAR_BLOCK.get());
        items.add(MachinesItems.INVAR_DUST.get());
        items.add(MachinesItems.INVAR_NUGGET.get());
        items.add(MachinesItems.INVAR_INGOT.get());
        items.add(MachinesItems.INVAR_GEAR.get());

        items.add(MachinesBlocks.STEEL_BLOCK.get());
        items.add(MachinesItems.STEEL_DUST.get());
        items.add(MachinesItems.STEEL_NUGGET.get());
        items.add(MachinesItems.STEEL_INGOT.get());
        items.add(MachinesItems.STEEL_GEAR.get());

        items.add(MachinesItems.IRON_DUST.get());
        items.add(MachinesItems.IRON_GEAR.get());

        items.add(MachinesItems.GOLD_DUST.get());
        items.add(MachinesItems.GOLD_GEAR.get());

        items.add(MachinesItems.COPPER_DUST.get());
        items.add(MachinesItems.COPPER_GEAR.get());

        return items.getItems();
    }

    public static void init() {
        // The machines led the tab when this was all one mod, and the metals they make came after the ores.
        SharedCreativeTabs.add(TAB, 100, MachinesCreativeItems::getMachines);
        SharedCreativeTabs.add(TAB, 300, MachinesCreativeItems::getMetals);
    }
}
