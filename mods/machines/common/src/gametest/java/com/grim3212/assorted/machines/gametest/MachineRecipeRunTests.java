package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipe;
import com.grim3212.assorted.machines.api.crafting.MachineIngredient;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.machines.common.inventory.BaseMachineInventory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/** Every loaded machine recipe cooked by a real machine, so a recipe that loads but never runs fails here rather than in a player's world. */
final class MachineRecipeRunTests {

    /** Ticks a recipe gets before it counts as never finishing, well past the slowest expert cook. */
    private static final int MAX_TICKS = 2000;

    private MachineRecipeRunTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("every_machine_recipe_runs_in_a_machine", MachineRecipeRunTests::everyMachineRecipeRunsInAMachine);
    }

    /** Ticked by hand rather than by the level, so dozens of recipes fit in one test; an Assorted Ores ore grinds into two dusts. */
    private static void everyMachineRecipeRunsInAMachine(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        int ran = 0;

        for (RecipeHolder<?> holder : helper.getLevel().recipeAccess().getRecipes()) {
            if (!(holder.value() instanceof BaseMachineRecipe recipe)) {
                continue;
            }
            ran++;
            String id = holder.id().identifier().toString();
            boolean mill = recipe instanceof GrindingMillRecipe;

            helper.setBlock(MACHINE, Blocks.AIR);
            helper.setBlock(MACHINE, mill ? MachinesBlocks.EXPERT_GRINDING_MILL.get() : MachinesBlocks.EXPERT_ALLOY_FORGE.get());
            BaseMachineBlockEntity machine = helper.getBlockEntity(MACHINE, BaseMachineBlockEntity.class);
            BaseMachineInventory inventory = machine.getInventory(null);

            if (mill) {
                inventory.setStackInSlot(1, new ItemStack(Items.IRON_PICKAXE));
            }
            inventory.setStackInSlot(machine.fuelSlot(), new ItemStack(Items.COAL_BLOCK, 64));

            List<MachineIngredient> ingredients = recipe.machineIngredients();
            ItemStack firstInput = ItemStack.EMPTY;
            for (int i = 0; i < ingredients.size(); i++) {
                MachineIngredient ingredient = ingredients.get(i);
                ItemStack input = ingredient.getBaseIngredient().items().findFirst()
                        .map(item -> new ItemStack(item, ingredient.getCount()))
                        .orElse(ItemStack.EMPTY);
                if (input.isEmpty()) {
                    problems.add(id + " has an ingredient that matches nothing");
                }
                if (i == 0) {
                    firstInput = input;
                }
                // Through the inventory handler, which is what sets the cook time.
                inventory.setStackInSlot(machine.inputSlots().get(i), input);
            }

            for (int tick = 0; tick < MAX_TICKS && inventory.getStackInSlot(machine.outputSlot()).isEmpty(); tick++) {
                machine.tick();
            }

            ItemStack made = inventory.getStackInSlot(machine.outputSlot());
            ItemStack expected = recipe.getResultItem();
            if (!ItemStack.isSameItemSameComponents(made, expected) || made.getCount() != expected.getCount()) {
                problems.add(id + " made " + made + " instead of " + expected);
            } else if (mill && isOresOre(firstInput) && made.getCount() != 2) {
                problems.add(id + " grinds an ore into " + made.getCount() + " " + BuiltInRegistries.ITEM.getKey(made.getItem()) + ", not two");
            }
        }

        helper.assertTrue(ran > 0, "no machine recipes are loaded");
        helper.assertTrue(problems.isEmpty(), "machine recipes that do not run:\n  " + String.join("\n  ", problems));
        helper.succeed();
    }

    private static boolean isOresOre(ItemStack stack) {
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return id.getNamespace().equals("assortedores") && id.getPath().endsWith("_ore");
    }
}
