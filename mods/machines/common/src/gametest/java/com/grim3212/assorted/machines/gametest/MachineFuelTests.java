package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.AlloyForgeBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.GrindingMillBlockEntity;
import com.grim3212.assorted.machines.common.inventory.BaseMachineInventory;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/** What a machine burns and what its slots let in, which 26.3 moved onto the cooking_fuel component and the crafting remainder template. */
final class MachineFuelTests {

    private MachineFuelTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("lava_bucket_fuel_leaves_a_bucket", MachineFuelTests::lavaBucketFuelLeavesABucket);
        out.accept("machine_slots_turn_away_the_wrong_items", MachineFuelTests::machineSlotsTurnAwayTheWrongItems);
    }

    private static void lavaBucketFuelLeavesABucket(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        AlloyForgeBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);
        BaseMachineInventory inventory = forge.getInventory(null);
        inventory.setStackInSlot(2, new ItemStack(Items.LAVA_BUCKET));
        inventory.setStackInSlot(0, new ItemStack(Items.COAL, 4));
        inventory.setStackInSlot(1, new ItemStack(MachinesItems.IRON_DUST.get()));

        helper.succeedWhen(() -> {
            ItemStack fuel = inventory.getStackInSlot(2);
            helper.assertTrue(fuel.is(Items.BUCKET), "burning a lava bucket left " + fuel + " in the fuel slot, not an empty bucket");
        });
    }

    /** Through a hopper's side, as a player's automation reaches it; the menu's own slots ask the same handler. */
    private static void machineSlotsTurnAwayTheWrongItems(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.BASIC_GRINDING_MILL.get());
        GrindingMillBlockEntity mill = helper.getBlockEntity(MACHINE, GrindingMillBlockEntity.class);
        BaseMachineInventory side = mill.getInventory(Direction.NORTH);
        BaseMachineInventory all = mill.getInventory(null);

        refuses(helper, all, 2, new ItemStack(Items.DIRT), "the grinding mill's fuel slot");
        refuses(helper, all, 0, new ItemStack(Items.DIRT), "the grinding mill's input");
        refuses(helper, all, 1, new ItemStack(Items.STONE_PICKAXE), "the grinding mill's tool slot");
        refuses(helper, all, 3, new ItemStack(Items.COPPER_INGOT), "the grinding mill's output");
        helper.assertTrue(side.insertItem(2, new ItemStack(Items.COAL), true).isEmpty(), "a hopper on the side cannot fuel the grinding mill");

        helper.setBlock(MACHINE.east(2), MachinesBlocks.BASIC_ALLOY_FORGE.get());
        BaseMachineInventory forge = helper.getBlockEntity(MACHINE.east(2), AlloyForgeBlockEntity.class).getInventory(null);
        refuses(helper, forge, 2, new ItemStack(Items.DIRT), "the alloy forge's fuel slot");
        refuses(helper, forge, 0, new ItemStack(Items.DIRT), "the alloy forge's input");
        refuses(helper, forge, 3, new ItemStack(MachinesItems.STEEL_INGOT.get()), "the alloy forge's output");

        helper.succeed();
    }

    private static void refuses(GameTestHelper helper, BaseMachineInventory inventory, int slot, ItemStack stack, String what) {
        helper.assertFalse(inventory.insertItem(slot, stack.copy(), true).isEmpty(), what + " took " + stack);
    }
}
