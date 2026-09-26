package com.grim3212.assorted.machines.api;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.lib.util.LibCommonTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/** The tags the machines read and fill. The metals come from Assorted Ores or any other mod, through the c: tags. */
public class MachinesTags {

    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS = commonTag("storage_blocks");
        public static final TagKey<Block> STORAGE_BLOCKS_BRONZE = commonTag("storage_blocks/bronze");
        public static final TagKey<Block> STORAGE_BLOCKS_ELECTRUM = commonTag("storage_blocks/electrum");
        public static final TagKey<Block> STORAGE_BLOCKS_INVAR = commonTag("storage_blocks/invar");
        public static final TagKey<Block> STORAGE_BLOCKS_STEEL = commonTag("storage_blocks/steel");

        private static TagKey<Block> commonTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> GRINDING_MILL_ALLOWED_TOOLS = modTag("grinding_mill_allowed_tools");

        public static final TagKey<Item> ORES_TIN = commonTag("ores/tin");
        public static final TagKey<Item> ORES_COPPER = commonTag("ores/copper");
        public static final TagKey<Item> ORES_SILVER = commonTag("ores/silver");
        public static final TagKey<Item> ORES_ALUMINUM = commonTag("ores/aluminum");
        public static final TagKey<Item> ORES_NICKEL = commonTag("ores/nickel");
        public static final TagKey<Item> ORES_PLATINUM = commonTag("ores/platinum");
        public static final TagKey<Item> ORES_LEAD = commonTag("ores/lead");

        public static final TagKey<Item> STORAGE_BLOCKS = commonTag("storage_blocks");
        public static final TagKey<Item> STORAGE_BLOCKS_BRONZE = commonTag("storage_blocks/bronze");
        public static final TagKey<Item> STORAGE_BLOCKS_ELECTRUM = commonTag("storage_blocks/electrum");
        public static final TagKey<Item> STORAGE_BLOCKS_INVAR = commonTag("storage_blocks/invar");
        public static final TagKey<Item> STORAGE_BLOCKS_STEEL = commonTag("storage_blocks/steel");

        public static final TagKey<Item> INGOTS = commonTag("ingots");
        public static final TagKey<Item> INGOTS_TIN = commonTag("ingots/tin");
        public static final TagKey<Item> INGOTS_COPPER = commonTag("ingots/copper");
        public static final TagKey<Item> INGOTS_SILVER = commonTag("ingots/silver");
        public static final TagKey<Item> INGOTS_ALUMINUM = commonTag("ingots/aluminum");
        public static final TagKey<Item> INGOTS_NICKEL = commonTag("ingots/nickel");
        public static final TagKey<Item> INGOTS_PLATINUM = commonTag("ingots/platinum");
        public static final TagKey<Item> INGOTS_LEAD = commonTag("ingots/lead");
        public static final TagKey<Item> INGOTS_BRONZE = commonTag("ingots/bronze");
        public static final TagKey<Item> INGOTS_ELECTRUM = commonTag("ingots/electrum");
        public static final TagKey<Item> INGOTS_INVAR = commonTag("ingots/invar");
        public static final TagKey<Item> INGOTS_STEEL = commonTag("ingots/steel");

        public static final TagKey<Item> NUGGETS = commonTag("nuggets");
        public static final TagKey<Item> NUGGETS_BRONZE = commonTag("nuggets/bronze");
        public static final TagKey<Item> NUGGETS_ELECTRUM = commonTag("nuggets/electrum");
        public static final TagKey<Item> NUGGETS_INVAR = commonTag("nuggets/invar");
        public static final TagKey<Item> NUGGETS_STEEL = commonTag("nuggets/steel");

        public static final TagKey<Item> DUSTS = commonTag("dusts");
        public static final TagKey<Item> DUSTS_TIN = commonTag("dusts/tin");
        public static final TagKey<Item> DUSTS_COPPER = commonTag("dusts/copper");
        public static final TagKey<Item> DUSTS_SILVER = commonTag("dusts/silver");
        public static final TagKey<Item> DUSTS_ALUMINUM = commonTag("dusts/aluminum");
        public static final TagKey<Item> DUSTS_NICKEL = commonTag("dusts/nickel");
        public static final TagKey<Item> DUSTS_PLATINUM = commonTag("dusts/platinum");
        public static final TagKey<Item> DUSTS_LEAD = commonTag("dusts/lead");
        public static final TagKey<Item> DUSTS_BRONZE = commonTag("dusts/bronze");
        public static final TagKey<Item> DUSTS_ELECTRUM = commonTag("dusts/electrum");
        public static final TagKey<Item> DUSTS_INVAR = commonTag("dusts/invar");
        public static final TagKey<Item> DUSTS_STEEL = commonTag("dusts/steel");
        public static final TagKey<Item> DUSTS_IRON = commonTag("dusts/iron");
        public static final TagKey<Item> DUSTS_GOLD = commonTag("dusts/gold");

        public static final TagKey<Item> GEARS = commonTag("gear");
        public static final TagKey<Item> GEARS_TIN = commonTag("gear/tin");
        public static final TagKey<Item> GEARS_COPPER = commonTag("gear/copper");
        public static final TagKey<Item> GEARS_SILVER = commonTag("gear/silver");
        public static final TagKey<Item> GEARS_ALUMINUM = commonTag("gear/aluminum");
        public static final TagKey<Item> GEARS_NICKEL = commonTag("gear/nickel");
        public static final TagKey<Item> GEARS_PLATINUM = commonTag("gear/platinum");
        public static final TagKey<Item> GEARS_LEAD = commonTag("gear/lead");
        public static final TagKey<Item> GEARS_BRONZE = commonTag("gear/bronze");
        public static final TagKey<Item> GEARS_ELECTRUM = commonTag("gear/electrum");
        public static final TagKey<Item> GEARS_INVAR = commonTag("gear/invar");
        public static final TagKey<Item> GEARS_STEEL = commonTag("gear/steel");
        public static final TagKey<Item> GEARS_IRON = commonTag("gear/iron");
        public static final TagKey<Item> GEARS_GOLD = commonTag("gear/gold");

        public static final TagKey<Item> RAW_MATERIALS_TIN = commonTag("raw_materials/tin");
        public static final TagKey<Item> RAW_MATERIALS_SILVER = commonTag("raw_materials/silver");
        public static final TagKey<Item> RAW_MATERIALS_ALUMINUM = commonTag("raw_materials/aluminum");
        public static final TagKey<Item> RAW_MATERIALS_NICKEL = commonTag("raw_materials/nickel");
        public static final TagKey<Item> RAW_MATERIALS_PLATINUM = commonTag("raw_materials/platinum");
        public static final TagKey<Item> RAW_MATERIALS_LEAD = commonTag("raw_materials/lead");
        public static final TagKey<Item> RAW_MATERIALS_IRON = commonTag("raw_materials/iron");
        public static final TagKey<Item> RAW_MATERIALS_GOLD = commonTag("raw_materials/gold");
        public static final TagKey<Item> RAW_MATERIALS_COPPER = commonTag("raw_materials/copper");

        private static TagKey<Item> commonTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }

        private static TagKey<Item> modTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }
}
