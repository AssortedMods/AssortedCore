package com.grim3212.assorted.machines.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.machines.Constants;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. A block, item or entity whose name is its id in title case needs
 * no line here (see {@link LibLanguageProvider}); these are the names that read differently, and
 * every key that is not a name.
 */
public class MachinesLanguageProvider extends LibLanguageProvider {

    public MachinesLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void addNames() {
        this.add("itemGroup." + Constants.FAMILY_ID, "Assorted Core");

        this.add(Constants.MOD_ID + ".container.alloy_forge", "Alloy Forge");
        this.add(Constants.MOD_ID + ".container.grinding_mill", "Grinding Mill");

        // The recipe book's "only what I can make" toggle, one name per machine.
        this.add("gui." + Constants.MOD_ID + ".recipebook.toggleRecipes.alloyable", "Showing Alloyable");
        this.add("gui." + Constants.MOD_ID + ".recipebook.toggleRecipes.grindable", "Showing Grindable");

        this.add("tag.item." + Constants.MOD_ID + ".grinding_mill_allowed_tools", "Grinding Mill Tools");
        this.add("tag.item.c.gear", "Gears");

        // c: item tags this mod adds that neither loader names, one family at a time.
        this.tagFamily("dusts", "%s Dusts", "bronze", "copper", "electrum", "gold", "invar", "iron", "steel");
        this.tagFamily("gear", "%s Gears", "bronze", "copper", "electrum", "gold", "invar", "iron", "steel");
        this.tagFamily("ingots", "%s Ingots", "bronze", "electrum", "invar", "steel");
        this.tagFamily("nuggets", "%s Nuggets", "bronze", "electrum", "invar", "steel");
        this.tagFamily("storage_blocks", "%s Storage Blocks", "bronze", "electrum", "invar", "steel");

        // Families whose names read differently from their ids.
        this.nameBlocks("(.+)_block", m -> "Block of " + titleCase(m.group(1)));

        this.addManual();
    }

    /** The section is the family's, so every part writes its title and description the same. */
    private void addManual() {
        this.add("manual." + Constants.FAMILY_ID + ".title", "Assorted Core");
        this.add("manual." + Constants.FAMILY_ID + ".description",
                "The ores, metals and gems the other Assorted mods build on, and the two machines that work them.");

        this.addMachinesChapter();
    }

    private void addMachinesChapter() {
        String chapter = "manual." + Constants.FAMILY_ID + ".chapter.machines";
        this.add(chapter, "Machines");

        this.add(chapter + ".core.title", "Machine Core");
        this.add(chapter + ".core",
                "Every machine is built around a machine core.");

        this.add(chapter + ".tiers.title", "Tiers");
        this.add(chapter + ".tiers",
                "Each machine comes in four tiers, doing the same work at different speeds. Basic, intermediate, advanced, and expert.");

        this.add(chapter + ".alloy_forge.title", "Alloy Forge");
        this.add(chapter + ".alloy_forge",
                "The alloy forge takes two ingredients and fuel, and melts them together.");

        this.add(chapter + ".alloying.title", "Alloying");
        this.add(chapter + ".alloying",
                "The alloy forge can make a number of different alloys. Here are the recipes for the alloys in this mod.");

        this.add(chapter + ".grinding_mill.title", "Grinding Mill");
        this.add(chapter + ".grinding_mill",
                "The grinding mill breaks one thing down into another, and doubles what an ore is worth on the way.");

        this.add(chapter + ".grinding.title", "Grinding");
        this.add(chapter + ".grinding",
                "An ore grinds into two dusts, a raw chunk into two, and an ingot back into one. Every metal works the same way.");

        this.add(chapter + ".dusts.title", "Dusts");
        this.add(chapter + ".dusts",
                "Grind an ore into dust and smelt the dust, and one ore has become two ingots.");

        this.add(chapter + ".steel.title", "Steel");
        this.add(chapter + ".steel",
                "Iron dust forged with coal. Steel is the sturdiest of the metals here and makes very strong tools.");

        this.add(chapter + ".gears.title", "Gears");
        this.add(chapter + ".gears",
                "Every metal can be made into a gear. Machines and the other Assorted mods can be built out of them.");
    }

    /** Names c:{@code family}/material for each material, as {@code format} around the material name. */
    private void tagFamily(String family, String format, String... materials) {
        for (String material : materials) {
            this.add("tag.item.c." + family + "." + material, String.format(format, titleCase(material)));
        }
    }
}
