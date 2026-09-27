package com.grim3212.assorted.ores.gametest;

import com.google.gson.JsonParser;
import com.grim3212.assorted.ores.Family;
import com.mojang.serialization.JsonOps;
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

import static com.grim3212.assorted.ores.gametest.OresTestSupport.*;

/**
 * The metals' dusts and gears, which Assorted Machines registered before they moved here.
 */
final class DustAndGearTests {

    private DustAndGearTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("dusts_and_gears_are_ores_own", DustAndGearTests::dustsAndGearsAreOresOwn);
        out.accept("ore_metal_gears_craft_and_dusts_smelt", DustAndGearTests::gearsCraftAndDustsSmelt);
    }

    /** Each one is registered here, not in Assorted Machines, and a stack saved under Assorted Core reads back as it. */
    private static void dustsAndGearsAreOresOwn(GameTestHelper helper) {
        for (String metal : ORE_METALS) {
            for (String name : List.of(metal + "_dust", metal + "_gear")) {
                Item item = item(helper, name);
                helper.assertFalse(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath("assortedmachines", name)),
                        "assortedmachines:" + name + " is still registered");

                Identifier old = Identifier.fromNamespaceAndPath(Family.ID, name);
                ItemStack stack = ItemStack.CODEC.parse(helper.getLevel().registryAccess().createSerializationContext(JsonOps.INSTANCE),
                        JsonParser.parseString("{\"id\": \"" + old + "\", \"count\": 1}")).getOrThrow();
                helper.assertTrue(stack.is(item), "a stack saved as " + old + " reads back as " + stack);
            }
        }

        helper.succeed();
    }

    /** Four ingots around a stick make a gear, and a dust smelts and blasts back into its ingot. */
    private static void gearsCraftAndDustsSmelt(GameTestHelper helper) {
        ItemStack stick = new ItemStack(Items.STICK);

        for (String metal : ORE_METALS) {
            Item ingot = item(helper, metal + "_ingot");
            Item dust = item(helper, metal + "_dust");
            ItemStack ingots = new ItemStack(ingot);

            assertCrafts(helper, 3, 3, List.of(ItemStack.EMPTY, ingots, ItemStack.EMPTY, ingots, stick, ingots, ItemStack.EMPTY, ingots, ItemStack.EMPTY),
                    item(helper, metal + "_gear"), 1, metal + " gear");
            assertCooks(helper, RecipeType.SMELTING, dust, ingot, "smelting");
            assertCooks(helper, RecipeType.BLASTING, dust, ingot, "blasting");
        }

        helper.succeed();
    }
}
