package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.*;

/**
 * The metals this mod makes: the alloy families, and the dusts and gears of the vanilla metals.
 */
final class MetalTests {

    private MetalTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("metal_families_round_trip", MetalTests::metalFamiliesRoundTrip);
        out.accept("dust_smelts_to_ingots", MetalTests::dustSmeltsToIngots);
    }

    /**
     * Every alloy owns an ingot, a nugget, a dust, a gear and a storage block, and the ingot goes to nuggets and
     * to a block and back again. The vanilla metals only get a dust and a gear, and Assorted Ores' metals none.
     */
    private static void metalFamiliesRoundTrip(GameTestHelper helper) {
        for (String metal : ALLOYS) {
            Item ingot = item(helper, metal + "_ingot");
            Item nugget = item(helper, metal + "_nugget");
            Item block = item(helper, metal + "_block");
            Item gear = item(helper, metal + "_gear");
            item(helper, metal + "_dust");

            ItemStack ingotStack = new ItemStack(ingot);
            ItemStack nuggetStack = new ItemStack(nugget);

            assertCrafts(helper, 1, 1, List.of(ingotStack), nugget, 9, metal + " ingot -> nuggets");
            assertCrafts(helper, 3, 3, List.of(nuggetStack, nuggetStack, nuggetStack, nuggetStack, nuggetStack,
                    nuggetStack, nuggetStack, nuggetStack, nuggetStack), ingot, 1, metal + " nuggets -> ingot");
            assertCrafts(helper, 3, 3, List.of(ingotStack, ingotStack, ingotStack, ingotStack, ingotStack,
                    ingotStack, ingotStack, ingotStack, ingotStack), block, 1, metal + " ingots -> block");
            assertCrafts(helper, 1, 1, List.of(new ItemStack(block)), ingot, 9, metal + " block -> ingots");
            assertCrafts(helper, 3, 3, cross(ingotStack, new ItemStack(Items.STICK)), gear, 1, metal + " gear");
        }

        for (String metal : VANILLA_METALS) {
            item(helper, metal + "_dust");
            item(helper, metal + "_gear");
        }

        for (String metal : ORE_METALS) {
            helper.assertFalse(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath(Constants.MOD_ID, metal + "_dust")),
                    metal + " dust is Assorted Ores' now but is still registered here");
            helper.assertFalse(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath(Constants.MOD_ID, metal + "_gear")),
                    metal + " gear is Assorted Ores' now but is still registered here");
        }

        helper.succeed();
    }

    /** Every alloy dust smelts and blasts back into its ingot, and the vanilla metals' dusts into theirs. */
    private static void dustSmeltsToIngots(GameTestHelper helper) {
        for (String metal : ALLOYS) {
            Item ingot = item(helper, metal + "_ingot");
            Item dust = item(helper, metal + "_dust");
            assertCooks(helper, RecipeType.SMELTING, dust, ingot, "smelting");
            assertCooks(helper, RecipeType.BLASTING, dust, ingot, "blasting");
        }

        assertCooks(helper, RecipeType.SMELTING, MachinesItems.IRON_DUST.get(), Items.IRON_INGOT, "smelting");
        assertCooks(helper, RecipeType.BLASTING, MachinesItems.IRON_DUST.get(), Items.IRON_INGOT, "blasting");
        assertCooks(helper, RecipeType.SMELTING, MachinesItems.GOLD_DUST.get(), Items.GOLD_INGOT, "smelting");
        assertCooks(helper, RecipeType.BLASTING, MachinesItems.GOLD_DUST.get(), Items.GOLD_INGOT, "blasting");
        assertCooks(helper, RecipeType.SMELTING, MachinesItems.COPPER_DUST.get(), Items.COPPER_INGOT, "smelting");
        assertCooks(helper, RecipeType.BLASTING, MachinesItems.COPPER_DUST.get(), Items.COPPER_INGOT, "blasting");

        helper.succeed();
    }
}
