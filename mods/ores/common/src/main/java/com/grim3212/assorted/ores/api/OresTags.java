package com.grim3212.assorted.ores.api;

import com.grim3212.assorted.lib.util.LibCommonTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/** The c: tags the ores, metals and gems fill, which other mods read to use them. */
public class OresTags {

    public static class Blocks {
        public static final TagKey<Block> ORES = commonTag("ores");
        public static final TagKey<Block> ORES_TIN = commonTag("ores/tin");
        public static final TagKey<Block> ORES_COPPER = commonTag("ores/copper");
        public static final TagKey<Block> ORES_SILVER = commonTag("ores/silver");
        public static final TagKey<Block> ORES_ALUMINUM = commonTag("ores/aluminum");
        public static final TagKey<Block> ORES_NICKEL = commonTag("ores/nickel");
        public static final TagKey<Block> ORES_PLATINUM = commonTag("ores/platinum");
        public static final TagKey<Block> ORES_LEAD = commonTag("ores/lead");
        public static final TagKey<Block> ORES_RUBY = commonTag("ores/ruby");
        public static final TagKey<Block> ORES_PERIDOT = commonTag("ores/peridot");
        public static final TagKey<Block> ORES_SAPPHIRE = commonTag("ores/sapphire");
        public static final TagKey<Block> ORES_TOPAZ = commonTag("ores/topaz");

        public static final TagKey<Block> STORAGE_BLOCKS = commonTag("storage_blocks");
        public static final TagKey<Block> STORAGE_BLOCKS_TIN = commonTag("storage_blocks/tin");
        public static final TagKey<Block> STORAGE_BLOCKS_COPPER = commonTag("storage_blocks/copper");
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = commonTag("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_ALUMINUM = commonTag("storage_blocks/aluminum");
        public static final TagKey<Block> STORAGE_BLOCKS_NICKEL = commonTag("storage_blocks/nickel");
        public static final TagKey<Block> STORAGE_BLOCKS_PLATINUM = commonTag("storage_blocks/platinum");
        public static final TagKey<Block> STORAGE_BLOCKS_LEAD = commonTag("storage_blocks/lead");
        public static final TagKey<Block> STORAGE_BLOCKS_RUBY = commonTag("storage_blocks/ruby");
        public static final TagKey<Block> STORAGE_BLOCKS_PERIDOT = commonTag("storage_blocks/peridot");
        public static final TagKey<Block> STORAGE_BLOCKS_SAPPHIRE = commonTag("storage_blocks/sapphire");
        public static final TagKey<Block> STORAGE_BLOCKS_TOPAZ = commonTag("storage_blocks/topaz");

        public static final TagKey<Block> RAW_STORAGE_BLOCKS_TIN = commonTag("storage_blocks/raw_tin");
        public static final TagKey<Block> RAW_STORAGE_BLOCKS_COPPER = commonTag("storage_blocks/raw_copper");
        public static final TagKey<Block> RAW_STORAGE_BLOCKS_SILVER = commonTag("storage_blocks/raw_silver");
        public static final TagKey<Block> RAW_STORAGE_BLOCKS_ALUMINUM = commonTag("storage_blocks/raw_aluminum");
        public static final TagKey<Block> RAW_STORAGE_BLOCKS_NICKEL = commonTag("storage_blocks/raw_nickel");
        public static final TagKey<Block> RAW_STORAGE_BLOCKS_PLATINUM = commonTag("storage_blocks/raw_platinum");
        public static final TagKey<Block> RAW_STORAGE_BLOCKS_LEAD = commonTag("storage_blocks/raw_lead");

        private static TagKey<Block> commonTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> ORES = commonTag("ores");
        public static final TagKey<Item> ORES_TIN = commonTag("ores/tin");
        public static final TagKey<Item> ORES_COPPER = commonTag("ores/copper");
        public static final TagKey<Item> ORES_SILVER = commonTag("ores/silver");
        public static final TagKey<Item> ORES_ALUMINUM = commonTag("ores/aluminum");
        public static final TagKey<Item> ORES_NICKEL = commonTag("ores/nickel");
        public static final TagKey<Item> ORES_PLATINUM = commonTag("ores/platinum");
        public static final TagKey<Item> ORES_LEAD = commonTag("ores/lead");
        public static final TagKey<Item> ORES_RUBY = commonTag("ores/ruby");
        public static final TagKey<Item> ORES_PERIDOT = commonTag("ores/peridot");
        public static final TagKey<Item> ORES_SAPPHIRE = commonTag("ores/sapphire");
        public static final TagKey<Item> ORES_TOPAZ = commonTag("ores/topaz");

        public static final TagKey<Item> STORAGE_BLOCKS = commonTag("storage_blocks");
        public static final TagKey<Item> STORAGE_BLOCKS_TIN = commonTag("storage_blocks/tin");
        public static final TagKey<Item> STORAGE_BLOCKS_COPPER = commonTag("storage_blocks/copper");
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = commonTag("storage_blocks/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_ALUMINUM = commonTag("storage_blocks/aluminum");
        public static final TagKey<Item> STORAGE_BLOCKS_NICKEL = commonTag("storage_blocks/nickel");
        public static final TagKey<Item> STORAGE_BLOCKS_PLATINUM = commonTag("storage_blocks/platinum");
        public static final TagKey<Item> STORAGE_BLOCKS_LEAD = commonTag("storage_blocks/lead");
        public static final TagKey<Item> STORAGE_BLOCKS_RUBY = commonTag("storage_blocks/ruby");
        public static final TagKey<Item> STORAGE_BLOCKS_PERIDOT = commonTag("storage_blocks/peridot");
        public static final TagKey<Item> STORAGE_BLOCKS_SAPPHIRE = commonTag("storage_blocks/sapphire");
        public static final TagKey<Item> STORAGE_BLOCKS_TOPAZ = commonTag("storage_blocks/topaz");

        public static final TagKey<Item> RAW_STORAGE_BLOCKS_TIN = commonTag("storage_blocks/raw_tin");
        public static final TagKey<Item> RAW_STORAGE_BLOCKS_COPPER = commonTag("storage_blocks/raw_copper");
        public static final TagKey<Item> RAW_STORAGE_BLOCKS_SILVER = commonTag("storage_blocks/raw_silver");
        public static final TagKey<Item> RAW_STORAGE_BLOCKS_ALUMINUM = commonTag("storage_blocks/raw_aluminum");
        public static final TagKey<Item> RAW_STORAGE_BLOCKS_NICKEL = commonTag("storage_blocks/raw_nickel");
        public static final TagKey<Item> RAW_STORAGE_BLOCKS_PLATINUM = commonTag("storage_blocks/raw_platinum");
        public static final TagKey<Item> RAW_STORAGE_BLOCKS_LEAD = commonTag("storage_blocks/raw_lead");

        public static final TagKey<Item> INGOTS = commonTag("ingots");
        public static final TagKey<Item> INGOTS_TIN = commonTag("ingots/tin");
        public static final TagKey<Item> INGOTS_COPPER = commonTag("ingots/copper");
        public static final TagKey<Item> INGOTS_SILVER = commonTag("ingots/silver");
        public static final TagKey<Item> INGOTS_ALUMINUM = commonTag("ingots/aluminum");
        public static final TagKey<Item> INGOTS_NICKEL = commonTag("ingots/nickel");
        public static final TagKey<Item> INGOTS_PLATINUM = commonTag("ingots/platinum");
        public static final TagKey<Item> INGOTS_LEAD = commonTag("ingots/lead");

        public static final TagKey<Item> NUGGETS = commonTag("nuggets");
        public static final TagKey<Item> NUGGETS_TIN = commonTag("nuggets/tin");
        public static final TagKey<Item> NUGGETS_COPPER = commonTag("nuggets/copper");
        public static final TagKey<Item> NUGGETS_SILVER = commonTag("nuggets/silver");
        public static final TagKey<Item> NUGGETS_ALUMINUM = commonTag("nuggets/aluminum");
        public static final TagKey<Item> NUGGETS_NICKEL = commonTag("nuggets/nickel");
        public static final TagKey<Item> NUGGETS_PLATINUM = commonTag("nuggets/platinum");
        public static final TagKey<Item> NUGGETS_LEAD = commonTag("nuggets/lead");

        // Assorted Machines or another mod fills these; the dusts smelt into this mod's ingots.
        public static final TagKey<Item> DUSTS_TIN = commonTag("dusts/tin");
        public static final TagKey<Item> DUSTS_SILVER = commonTag("dusts/silver");
        public static final TagKey<Item> DUSTS_ALUMINUM = commonTag("dusts/aluminum");
        public static final TagKey<Item> DUSTS_NICKEL = commonTag("dusts/nickel");
        public static final TagKey<Item> DUSTS_PLATINUM = commonTag("dusts/platinum");
        public static final TagKey<Item> DUSTS_LEAD = commonTag("dusts/lead");

        public static final TagKey<Item> GEMS = commonTag("gems");
        public static final TagKey<Item> GEMS_RUBY = commonTag("gems/ruby");
        public static final TagKey<Item> GEMS_PERIDOT = commonTag("gems/peridot");
        public static final TagKey<Item> GEMS_SAPPHIRE = commonTag("gems/sapphire");
        public static final TagKey<Item> GEMS_TOPAZ = commonTag("gems/topaz");

        public static final TagKey<Item> RAW_MATERIALS = commonTag("raw_materials");
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
    }
}
