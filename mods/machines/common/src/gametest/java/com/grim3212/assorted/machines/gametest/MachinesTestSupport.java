package com.grim3212.assorted.machines.gametest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grim3212.assorted.machines.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static com.grim3212.assorted.lib.test.TestSupport.craft;

/**
 * Helpers and constants shared by Assorted Machines' gametest classes, which import them statically,
 * alongside AssortedLib's {@code TestSupport}.
 */
final class MachinesTestSupport {

    private MachinesTestSupport() {
    }

    static final BlockPos MACHINE = new BlockPos(4, 1, 4);

    /** The metals the alloy forge makes, each with an ingot, nugget, dust, gear and storage block of its own. */
    static final List<String> ALLOYS = List.of("bronze", "electrum", "invar", "steel");

    /** The vanilla metals, which this mod only gives a dust and a gear. */
    static final List<String> VANILLA_METALS = List.of("iron", "gold", "copper");

    /** The metals of Assorted Ores, whose dusts and gears are Assorted Ores' own. */
    static final List<String> ORE_METALS = List.of("tin", "silver", "aluminum", "nickel", "platinum", "lead");

    /** Anything in the tag, for a recipe naming a metal only Assorted Ores or another mod adds. */
    static Optional<ItemStack> anyIn(GameTestHelper helper, TagKey<Item> tag) {
        return helper.getLevel().registryAccess().lookupOrThrow(Registries.ITEM).get(tag)
                .flatMap(items -> items.stream().findFirst())
                .map(item -> new ItemStack(item.value()));
    }

    /** A ring of eight around a centre - the machine core and two of the three tier upgrades. */
    static List<ItemStack> ring(ItemStack corner, ItemStack edge, ItemStack centre) {
        return List.of(corner, edge, corner, edge, centre, edge, corner, edge, corner);
    }

    /** A plus of four around a centre - the expert upgrade and every gear. */
    static List<ItemStack> cross(ItemStack arm, ItemStack centre) {
        return List.of(ItemStack.EMPTY, arm, ItemStack.EMPTY, arm, centre, arm, ItemStack.EMPTY, arm, ItemStack.EMPTY);
    }

    /** {@code CraftingInput.of} wants exactly width * height stacks, so the size is passed in. */
    static void assertCrafts(GameTestHelper helper, int width, int height, List<ItemStack> grid, Item expected, int count, String what) {
        ItemStack result = craft(helper, CraftingInput.of(width, height, grid), what);
        helper.assertTrue(result.is(expected), what + " crafted " + result + " instead of " + expected);
        helper.assertValueEqual(result.getCount(), count, what + " result count");
    }

    static <T extends AbstractCookingRecipe> void assertCooks(GameTestHelper helper, RecipeType<T> type, Item input, Item expected, String what) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(new ItemStack(input));
        var found = helper.getLevel().recipeAccess().getRecipeFor(type, recipeInput, helper.getLevel());
        helper.assertTrue(found.isPresent(), "no " + what + " recipe for " + BuiltInRegistries.ITEM.getKey(input));

        ItemStack result = found.get().value().assemble(recipeInput);
        helper.assertTrue(result.is(expected), what + " " + BuiltInRegistries.ITEM.getKey(input) + " gave " + result + " instead of " + expected);
    }

    static Item item(GameTestHelper helper, String name) {
        Item found = BuiltInRegistries.ITEM.getOptional(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name)).orElse(null);
        helper.assertTrue(found != null, "nothing registered as " + Constants.MOD_ID + ":" + name);
        return found;
    }

    /** True when the mod ships a resource at this classpath path. */
    static boolean resourceExists(String path) {
        try (InputStream in = MachinesTestSupport.class.getResourceAsStream(path)) {
            return in != null;
        } catch (IOException e) {
            return false;
        }
    }

    static JsonObject readLang(GameTestHelper helper) {
        return readJson(helper, "/assets/" + Constants.MOD_ID + "/lang/en_us.json");
    }

    /** A json the mod ships, read off the classpath rather than through a resource pack. */
    static JsonObject readJson(GameTestHelper helper, String path) {
        try (InputStream in = MachinesTestSupport.class.getResourceAsStream(path)) {
            helper.assertTrue(in != null, path + " is not on the classpath");
            return JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (IOException e) {
            throw helper.assertionException("could not read " + path + ": " + e);
        }
    }
}
