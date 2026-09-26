package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipe;
import com.grim3212.assorted.machines.api.crafting.MachineRecipeDisplay;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeBookCategories;
import com.grim3212.assorted.machines.common.crafting.MachineRecipeBackfill;
import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.GameType;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/**
 * Machine recipes in the recipe book: reaching it, carrying their counts, and filling the machine.
 */
final class RecipeBookTests {

    private RecipeBookTests() {
    }

    /** 4 coal + 1 iron dust -> 1 steel ingot, the only machine recipe needing more than one of anything. */
    private static final ResourceKey<Recipe<?>> STEEL = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "steel_ingot"));

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("machine_recipes_reach_the_recipe_book", RecipeBookTests::machineRecipesReachTheRecipeBook);
        out.accept("machine_recipe_book_fills_the_machine", RecipeBookTests::machineRecipeBookFillsTheMachine);
        out.accept("machine_recipe_book_asks_for_a_ghost", RecipeBookTests::machineRecipeBookAsksForAGhost);
        out.accept("machine_display_survives_the_network", RecipeBookTests::machineDisplaySurvivesTheNetwork);
        out.accept("machine_recipe_backfills_on_join", RecipeBookTests::machineRecipeBackfillsOnJoin);
    }

    /** {@code ServerRecipeBook} drops a special recipe before sending it, and the book draws only a display. */
    private static void machineRecipesReachTheRecipeBook(GameTestHelper helper) {
        AlloyForgeRecipe steel = steelRecipe(helper).value();

        helper.assertFalse(steel.isSpecial(), "a special recipe never reaches the recipe book");
        helper.assertValueEqual(steel.recipeBookCategory(), MachinesRecipeBookCategories.ALLOY_FORGE.get(), "the steel recipe's book category");

        List<RecipeDisplay> displays = steel.display();
        helper.assertValueEqual(displays.size(), 1, "displays for one machine recipe");

        if (!(displays.getFirst() instanceof MachineRecipeDisplay display)) {
            throw helper.assertionException("a machine recipe described itself as a " + displays.getFirst().getClass().getSimpleName());
        }

        helper.assertValueEqual(display.inputs().size(), 2, "the alloy forge's input slots in its display");
        helper.assertValueEqual(display.inputs().get(0).count(), 4, "the coal count the display carries");
        helper.assertValueEqual(display.inputs().get(1).count(), 1, "the iron dust count the display carries");
        helper.assertValueEqual(display.duration(), steel.getCookTime(), "the display's duration");

        // Counted one item at a time: a slot asking for four appears four times.
        helper.assertFalse(steel.placementInfo().isImpossibleToPlace(), "a machine recipe that says it cannot be placed never reaches the book");
        helper.assertValueEqual(steel.placementInfo().ingredients().size(), 5, "counted out machine ingredients");
        helper.succeed();
    }

    /** The count is the part no vanilla display carries, and the id is how a client resolves the type. */
    private static void machineDisplaySurvivesTheNetwork(GameTestHelper helper) {
        Identifier id = BuiltInRegistries.RECIPE_DISPLAY.getKey(MachineRecipeDisplay.TYPE);
        helper.assertValueEqual(id, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "machine"), "the machine display's registered id");

        RecipeDisplay sent = steelRecipe(helper).value().display().getFirst();
        RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), helper.getLevel().registryAccess());
        RecipeDisplay.STREAM_CODEC.encode(buffer, sent);
        RecipeDisplay received = RecipeDisplay.STREAM_CODEC.decode(buffer);

        helper.assertValueEqual(received, sent, "the machine display after a round trip");
        helper.assertValueEqual(buffer.readableBytes(), 0, "bytes left over after reading the machine display");
        helper.succeed();
    }

    /** TODO(11.0.0): remove with {@link MachineRecipeBackfill}. The advancement is done, so it can never grant again. */
    private static void machineRecipeBackfillsOnJoin(GameTestHelper helper) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        RecipeHolder<AlloyForgeRecipe> steel = steelRecipe(helper);

        AdvancementHolder advancement = helper.getLevel().getServer().getAdvancements().get(STEEL.identifier().withPrefix("recipes/"));
        if (advancement == null) {
            throw helper.assertionException("no recipe advancement generated for " + STEEL.identifier());
        }

        for (String criterion : advancement.value().criteria().keySet()) {
            player.getAdvancements().award(advancement, criterion);
        }

        // What an upgraded save looks like: advancement done, recipe never stored.
        player.getRecipeBook().remove(steel.id());
        helper.assertFalse(player.getRecipeBook().contains(steel.id()), "the recipe book kept a recipe the test just removed");

        MachineRecipeBackfill.award(player);
        helper.assertTrue(player.getRecipeBook().contains(steel.id()), "an already earned machine recipe was not backfilled");
        helper.succeed();
    }

    /** Clicking an affordable recipe fills the input slots at the recipe's counts. */
    private static void machineRecipeBookFillsTheMachine(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.getInventory().add(new ItemStack(Items.COAL, 9));
        player.getInventory().add(new ItemStack(MachinesItems.IRON_DUST.get(), 3));

        BaseMachineContainer menu = openForge(helper, player);
        RecipeBookMenu.PostPlaceAction action = menu.handlePlacement(false, false, steelRecipe(helper), helper.getLevel(), player.getInventory());

        helper.assertValueEqual(action, RecipeBookMenu.PostPlaceAction.NOTHING, "what the menu did with a recipe the player can afford");
        assertSlot(helper, menu, 0, Items.COAL, 4);
        assertSlot(helper, menu, 1, MachinesItems.IRON_DUST.get(), 1);
        helper.succeed();
    }

    /** Three coal is one short - only visible because the count is carried; one of each would look affordable. */
    private static void machineRecipeBookAsksForAGhost(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.getInventory().add(new ItemStack(Items.COAL, 3));
        player.getInventory().add(new ItemStack(MachinesItems.IRON_DUST.get(), 1));

        BaseMachineContainer menu = openForge(helper, player);
        RecipeBookMenu.PostPlaceAction action = menu.handlePlacement(false, false, steelRecipe(helper), helper.getLevel(), player.getInventory());

        helper.assertValueEqual(action, RecipeBookMenu.PostPlaceAction.PLACE_GHOST_RECIPE, "what the menu did with a recipe the player is one coal short of");
        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "the forge was filled from an inventory that could not pay for the recipe");
        helper.succeed();
    }

    private static BaseMachineContainer openForge(GameTestHelper helper, Player player) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        MenuProvider provider = helper.getBlockState(MACHINE).getMenuProvider(helper.getLevel(), helper.absolutePos(MACHINE));

        if (provider == null) {
            throw helper.assertionException("the alloy forge has no menu provider");
        }

        return (BaseMachineContainer) provider.createMenu(1, player.getInventory(), player);
    }

    @SuppressWarnings("unchecked")
    private static RecipeHolder<AlloyForgeRecipe> steelRecipe(GameTestHelper helper) {
        RecipeHolder<?> found = helper.getLevel().recipeAccess().byKey(STEEL).orElse(null);

        if (found == null || !(found.value() instanceof AlloyForgeRecipe)) {
            throw helper.assertionException("no alloy forge recipe loaded as " + STEEL.identifier());
        }

        return (RecipeHolder<AlloyForgeRecipe>) found;
    }

    private static void assertSlot(GameTestHelper helper, BaseMachineContainer menu, int slot, Item expected, int count) {
        ItemStack stack = menu.getSlot(slot).getItem();
        helper.assertTrue(stack.is(expected), "slot " + slot + " holds " + stack + " instead of " + BuiltInRegistries.ITEM.getKey(expected));
        helper.assertValueEqual(stack.getCount(), count, "the count the book put in slot " + slot);
    }
}
