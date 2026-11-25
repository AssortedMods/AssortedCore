package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.lib.test.TestSupport;
import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipe;
import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipe;
import com.grim3212.assorted.machines.api.crafting.MachineIngredient;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import com.grim3212.assorted.machines.common.inventory.MachineFuelSlot;
import com.grim3212.assorted.machines.common.inventory.MachineResultSlot;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.blockEntity;

/**
 * How machine recipes reach a player in 1.21.1: kept out of the recipe book, which has no tab for a
 * machine, sent to the client whole with their counts, and moved into a machine by JEI's transfer
 * button rather than by the book.
 */
final class RecipeViewTests {

    private RecipeViewTests() {
    }

    /** 4 coal + 1 iron dust -> 1 steel ingot, the only machine recipe needing more than one of anything. */
    private static final ResourceLocation STEEL = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "steel_ingot");

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("machine_recipes_stay_out_of_the_recipe_book", RecipeViewTests::machineRecipesStayOutOfTheRecipeBook);
        out.accept("machine_recipe_survives_the_network", RecipeViewTests::machineRecipeSurvivesTheNetwork);
        out.accept("jei_transfers_into_the_machine_slots", RecipeViewTests::jeiTransfersIntoTheMachineSlots);
        out.accept("machine_recipe_counts_reach_jei", RecipeViewTests::machineRecipeCountsReachJei);
    }

    /**
     * The client sorts every recipe that is not special into a recipe book tab by a fixed enum, and
     * logs one with no tab as an unknown category, so every machine recipe - Assorted Ores' too - has
     * to be special, and so never lands in a player's book.
     */
    private static void machineRecipesStayOutOfTheRecipeBook(GameTestHelper helper) {
        int machineRecipes = 0;
        for (RecipeHolder<?> holder : helper.getLevel().getRecipeManager().getRecipes()) {
            if (holder.value() instanceof BaseMachineRecipe recipe) {
                machineRecipes++;
                helper.assertTrue(recipe.isSpecial(), holder.id() + " would be sent to the recipe book, which has no tab for it");
            }
        }
        helper.assertTrue(machineRecipes > 0, "no machine recipes are loaded");

        ServerPlayer player = TestSupport.survivalPlayer(helper);
        RecipeHolder<AlloyForgeRecipe> steel = steelRecipe(helper);
        player.awardRecipes(List.of(steel));
        helper.assertFalse(player.getRecipeBook().contains(steel), "awarding the steel recipe put it in the recipe book");
        helper.succeed();
    }

    /** 1.21.1 sends every recipe to every client through its serializer, and the counts are the part a plain ingredient has no room for. */
    // NeoForge deprecates this buffer constructor for an overload taking its connection type, which only its patched jar has.
    @SuppressWarnings("deprecation")
    private static void machineRecipeSurvivesTheNetwork(GameTestHelper helper) {
        RecipeHolder<AlloyForgeRecipe> sent = steelRecipe(helper);
        RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), helper.getLevel().registryAccess());
        RecipeHolder.STREAM_CODEC.encode(buffer, sent);
        RecipeHolder<?> received = RecipeHolder.STREAM_CODEC.decode(buffer);
        helper.assertValueEqual(buffer.readableBytes(), 0, "bytes left over after reading the steel recipe");
        helper.assertValueEqual(received.id(), sent.id(), "the steel recipe's id after a round trip");

        if (!(received.value() instanceof AlloyForgeRecipe recipe)) {
            throw new GameTestAssertException("the steel recipe came back as a " + received.value().getClass().getSimpleName());
        }

        AlloyForgeRecipe original = sent.value();
        assertSameIngredient(helper, recipe.getIngredient1(), original.getIngredient1(), "the first ingredient");
        assertSameIngredient(helper, recipe.getIngredient2(), original.getIngredient2(), "the second ingredient");
        helper.assertValueEqual(recipe.getIngredient1().getCount(), 4, "the coal count after a round trip");
        helper.assertValueEqual(recipe.getIngredient2().getCount(), 1, "the iron dust count after a round trip");
        helper.assertTrue(ItemStack.matches(recipe.getResultItem(), original.getResultItem()), "the steel recipe's result became " + recipe.getResultItem());
        helper.assertValueEqual(recipe.getCookTime(), original.getCookTime(), "the steel recipe's cook time after a round trip");
        helper.assertValueEqual(recipe.getExperience(), original.getExperience(), "the steel recipe's experience after a round trip");
        helper.succeed();
    }

    /**
     * JEI's transfer button is given a range of recipe slots and a range of inventory slots. Both
     * have to be the slots they claim to be, or it fills the fuel slot, or pulls from the machine.
     */
    private static void jeiTransfersIntoTheMachineSlots(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        BlockPos forgePos = new BlockPos(2, 1, 4);
        BaseMachineContainer forge = openMenu(helper, player, forgePos, MachinesBlocks.BASIC_ALLOY_FORGE.get());
        assertTransferRanges(helper, player, forge, "the alloy forge");
        AlloyForgeRecipe steel = steelRecipe(helper).value();
        assertTakes(helper, forge.getSlot(0), steel.getIngredient1(), "the alloy forge's first transfer slot");
        assertTakes(helper, forge.getSlot(1), steel.getIngredient2(), "the alloy forge's second transfer slot");

        BlockPos millPos = new BlockPos(6, 1, 4);
        BaseMachineContainer mill = openMenu(helper, player, millPos, MachinesBlocks.BASIC_GRINDING_MILL.get());
        assertTransferRanges(helper, player, mill, "the grinding mill");
        GrindingMillRecipe dust = helper.getLevel().getRecipeManager().getRecipes().stream()
                .map(RecipeHolder::value)
                .filter(GrindingMillRecipe.class::isInstance)
                .map(GrindingMillRecipe.class::cast)
                .findFirst()
                .orElseThrow(() -> new GameTestAssertException("no grinding mill recipe is loaded"));
        assertTakes(helper, mill.getSlot(0), dust.getIngredient(), "the grinding mill's first transfer slot");
        // The category lays out the accepted tools second, so the second transfer slot is the tool slot.
        helper.assertTrue(mill.getSlot(1).mayPlace(new ItemStack(Items.IRON_PICKAXE)), "the grinding mill's second transfer slot does not take a pickaxe");
        helper.succeed();
    }

    /** The count is on every stack JEI draws and moves; one of each would make the steel recipe look like one coal. */
    private static void machineRecipeCountsReachJei(GameTestHelper helper) {
        AlloyForgeRecipe steel = steelRecipe(helper).value();
        List<ItemStack> coal = steel.getIngredient1().getMatchingStacks();
        List<ItemStack> iron = steel.getIngredient2().getMatchingStacks();

        helper.assertTrue(coal.stream().anyMatch(stack -> stack.is(Items.COAL)), "the steel recipe's first slot does not show coal: " + coal);
        helper.assertTrue(coal.stream().allMatch(stack -> stack.getCount() == 4), "the steel recipe's coal is not shown four at a time: " + coal);
        helper.assertTrue(iron.stream().anyMatch(stack -> stack.is(MachinesItems.IRON_DUST.get())), "the steel recipe's second slot does not show iron dust: " + iron);
        helper.assertTrue(iron.stream().allMatch(stack -> stack.getCount() == 1), "the steel recipe's iron dust is not shown one at a time: " + iron);
        helper.succeed();
    }

    private static BaseMachineContainer openMenu(GameTestHelper helper, Player player, BlockPos pos, Block machine) {
        helper.setBlock(pos, machine);
        return (BaseMachineContainer) blockEntity(helper, pos, BaseMachineBlockEntity.class).createMenu(1, player.getInventory(), player);
    }

    private static void assertTransferRanges(GameTestHelper helper, Player player, BaseMachineContainer menu, String what) {
        helper.assertValueEqual(menu.slots.size(), BaseMachineContainer.INVENTORY_START + BaseMachineContainer.INVENTORY_SIZE, what + " menu slots");

        for (int i = 0; i < BaseMachineContainer.TRANSFER_SLOTS; i++) {
            Slot slot = menu.getSlot(i);
            helper.assertFalse(slot.container == player.getInventory(), what + " transfer slot " + i + " is in the player's inventory");
            helper.assertFalse(slot instanceof MachineFuelSlot || slot instanceof MachineResultSlot, what + " transfer slot " + i + " is its fuel or result slot");
        }

        for (int i = BaseMachineContainer.INVENTORY_START; i < BaseMachineContainer.INVENTORY_START + BaseMachineContainer.INVENTORY_SIZE; i++) {
            helper.assertTrue(menu.getSlot(i).container == player.getInventory(), what + " inventory slot " + i + " is not the player's inventory");
        }
    }

    private static void assertTakes(GameTestHelper helper, Slot slot, MachineIngredient ingredient, String what) {
        List<ItemStack> stacks = ingredient.getMatchingStacks();
        helper.assertFalse(stacks.isEmpty(), what + " has an ingredient that matches nothing");
        helper.assertTrue(slot.mayPlace(stacks.getFirst()), what + " does not take " + stacks.getFirst());
    }

    private static void assertSameIngredient(GameTestHelper helper, MachineIngredient received, MachineIngredient sent, String what) {
        List<String> receivedItems = received.getMatchingStacks().stream().map(stack -> BuiltInRegistries.ITEM.getKey(stack.getItem()).toString()).toList();
        List<String> sentItems = sent.getMatchingStacks().stream().map(stack -> BuiltInRegistries.ITEM.getKey(stack.getItem()).toString()).toList();
        helper.assertValueEqual(receivedItems, sentItems, what + "'s items after a round trip");
    }

    @SuppressWarnings("unchecked")
    private static RecipeHolder<AlloyForgeRecipe> steelRecipe(GameTestHelper helper) {
        RecipeHolder<?> found = helper.getLevel().getRecipeManager().byKey(STEEL).orElse(null);

        if (found == null || !(found.value() instanceof AlloyForgeRecipe)) {
            throw new GameTestAssertException("no alloy forge recipe loaded as " + STEEL);
        }

        return (RecipeHolder<AlloyForgeRecipe>) found;
    }
}
