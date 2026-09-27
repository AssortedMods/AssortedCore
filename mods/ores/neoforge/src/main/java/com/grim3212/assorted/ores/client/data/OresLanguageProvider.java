package com.grim3212.assorted.ores.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.Family;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. A block, item or entity whose name is its id in title case needs
 * no line here (see {@link LibLanguageProvider}); these are the names that read differently, and
 * every key that is not a name.
 */
public class OresLanguageProvider extends LibLanguageProvider {

    public OresLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void addNames() {
        this.add("itemGroup." + Family.ID, "Assorted Core");

        this.add("tag.item.c.gear", "Gears");

        // c: item tags this mod adds that neither loader names, one family at a time.
        this.tagFamily("dusts", "%s Dusts", "aluminum", "lead", "nickel", "platinum", "silver", "tin");
        this.tagFamily("gear", "%s Gears", "aluminum", "lead", "nickel", "platinum", "silver", "tin");
        this.tagFamily("gems", "%s Gems", "peridot", "ruby", "sapphire", "topaz");
        this.tagFamily("ingots", "%s Ingots", "aluminum", "lead", "nickel", "platinum", "silver", "tin");
        this.tagFamily("nuggets", "%s Nuggets", "aluminum", "lead", "nickel", "platinum", "silver", "tin");
        this.tagFamily("ores", "%s Ores", "aluminum", "lead", "nickel", "peridot", "platinum", "ruby", "sapphire", "silver", "tin", "topaz");
        this.tagFamily("raw_materials", "Raw %s Materials", "aluminum", "lead", "nickel", "platinum", "silver", "tin");
        this.tagFamily("storage_blocks", "%s Storage Blocks", "aluminum", "lead", "nickel", "peridot", "platinum", "raw_aluminum", "raw_lead", "raw_nickel", "raw_platinum", "raw_silver", "raw_tin", "ruby", "sapphire", "silver", "tin", "topaz");

        // Families whose names read differently from their ids.
        this.nameBlocks("(.+)_block", m -> "Block of " + titleCase(m.group(1)));

        this.addManual();
    }

    /** The section is the family's, so every part writes its title and description the same. */
    private void addManual() {
        this.add("manual." + Family.ID + ".title", "Assorted Core");
        this.add("manual." + Family.ID + ".description",
                "The ores, metals and gems the other Assorted mods build on, and the two machines that work them.");

        this.addMetalsChapter();
        this.addGemsChapter();
    }

    private void addMetalsChapter() {
        String chapter = "manual." + Family.ID + ".chapter.metals";
        this.add(chapter, "Ores and Metals");

        this.add(chapter + ".ores.title", "Ores");
        this.add(chapter + ".ores",
                "Six metals are added to the world, each with a deepslate form deeper down.");

        this.add(chapter + ".dusts.title", "Dusts");
        this.add(chapter + ".dusts",
                "Every metal here has a dust. The grinding mill from Assorted Machines turns an ore into two of them, and each one smelts back into an ingot.");

        this.add(chapter + ".gears.title", "Gears");
        this.add(chapter + ".gears",
                "Four ingots around a stick make a gear. Machines and the other Assorted mods can be built out of them.");
    }

    private void addGemsChapter() {
        String chapter = "manual." + Family.ID + ".chapter.gems";
        this.add(chapter, "Gems");

        this.add(chapter + ".gems.title", "Gems");
        this.add(chapter + ".gems",
                "Four gems are added to the world. Ruby, peridot, sapphire and topaz. Their ores drop the gem directly.");

        this.add(chapter + ".smelting.title", "From Ore");
        this.add(chapter + ".smelting",
                "A gem ore mined without Silk Touch already drops its gem. Smelt the ore itself when you have kept one whole.");

        this.add(chapter + ".storage.title", "Storage");
        this.add(chapter + ".storage",
                "Nine of a gem pack into a block, and the block breaks back down into nine. The same holds for every gem and metal here.");
    }

    /** Names c:{@code family}/material for each material, as {@code format} around the material name. */
    private void tagFamily(String family, String format, String... materials) {
        for (String material : materials) {
            this.add("tag.item.c." + family + "." + material, String.format(format, titleCase(material)));
        }
    }
}
