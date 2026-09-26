package com.grim3212.assorted.machines.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.function.Predicate;

/**
 * This part's chapter of the Assorted Core section, which every part shares; the explicit chapter order keeps the
 * section's order whichever parts are installed. The dust and gear families are matched by the shape of their ids.
 */
public class MachinesManualProvider extends LibManualProvider {

    /** The metals the alloy forge makes, which have no ore of their own. Steel has its own page. */
    private static final List<String> ALLOYS = List.of("bronze", "electrum", "invar");

    public MachinesManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Family.ID);
    }

    @Override
    protected void addChapters() {
        this.section(Family.MANUAL_ORDER, Family.ICONS.toArray(Identifier[]::new));

        ChapterBuilder machines = this.chapter("machines", 0);

        machines.recipes("core", MachinesBlocks.MACHINE_CORE.get()).opens(MachinesBlocks.MACHINE_CORE.get());
        machines.recipes("tiers", MachinesBlocks.BASIC_ALLOY_FORGE.get().asItem(),
                MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get().asItem(),
                MachinesBlocks.ADVANCED_ALLOY_FORGE.get().asItem(),
                MachinesBlocks.EXPERT_ALLOY_FORGE.get().asItem());
        machines.recipes("alloy_forge", MachinesBlocks.BASIC_ALLOY_FORGE.get())
                .opens(MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(),
                        MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        machines.recipes("alloying", MachinesItems.STEEL_INGOT.get(), MachinesItems.BRONZE_INGOT.get(), MachinesItems.ELECTRUM_INGOT.get(), MachinesItems.INVAR_INGOT.get()).every(60)
                .opensEveryItem(family(ALLOYS, "_ingot", "_nugget"))
                .opensEveryBlock(family(ALLOYS, "_block"));
        machines.recipes("grinding_mill", MachinesBlocks.BASIC_GRINDING_MILL.get())
                .opens(MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(),
                        MachinesBlocks.ADVANCED_GRINDING_MILL.get(), MachinesBlocks.EXPERT_GRINDING_MILL.get());
        machines.recipesById("grinding", recipeId(MachinesItems.IRON_DUST.get()), recipeId("iron_dust_from_ingot"), recipeId("iron_dust_from_raw_ore"))
                .every(60);
        machines.recipesById("dusts", recipeId("dusts/iron_smelting"), recipeId("dusts/iron_blasting")).every(60)
                .opensEveryItem(suffix("_dust"));
        machines.recipes("steel", MachinesItems.STEEL_INGOT.get())
                .opens(MachinesItems.STEEL_INGOT.get(), MachinesItems.STEEL_NUGGET.get())
                .opens(MachinesBlocks.STEEL_BLOCK.get());
        machines.recipes("gears", MachinesItems.STEEL_GEAR.get(), MachinesItems.COPPER_GEAR.get(), MachinesItems.IRON_GEAR.get()).every(60)
                .opensEveryItem(suffix("_gear"));
    }

    private static Predicate<Identifier> suffix(String suffix) {
        return id -> id.getPath().endsWith(suffix);
    }

    /** {@code <material><suffix>} for any of the materials and any of the suffixes. */
    private static Predicate<Identifier> family(List<String> materials, String... suffixes) {
        return id -> {
            for (String material : materials) {
                for (String suffix : suffixes) {
                    if (id.getPath().equals(material + suffix)) {
                        return true;
                    }
                }
            }
            return false;
        };
    }
}
