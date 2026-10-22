package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.lib.test.TestSupport;
import com.grim3212.assorted.machines.common.blocks.BaseMachineBlock;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.AlloyForgeBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.machines.common.inventory.BaseMachineInventory;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/** What a machine shows the world outside its menu: the lit face, a comparator's signal, and how it was placed. */
final class MachineStateTests {

    private MachineStateTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("machine_lights_only_while_burning", MachineStateTests::machineLightsOnlyWhileBurning);
        out.accept("machine_comparator_reads_its_contents", MachineStateTests::machineComparatorReadsItsContents);
        out.accept("machine_faces_and_keeps_the_name_it_was_placed_with", MachineStateTests::machineFacesAndKeepsTheNameItWasPlacedWith);
    }

    /** A stick burns out after one steel smelt, so the forge has to go dark again on its own. */
    private static void machineLightsOnlyWhileBurning(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        BaseMachineInventory inventory = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class).getInventory(null);
        inventory.setStackInSlot(2, new ItemStack(Items.STICK));
        inventory.setStackInSlot(0, new ItemStack(Items.COAL, 4));
        inventory.setStackInSlot(1, new ItemStack(MachinesItems.IRON_DUST.get()));

        helper.startSequence()
                .thenWaitUntil(() -> helper.assertTrue(helper.getBlockState(MACHINE).getValue(BaseMachineBlock.ON), "the forge never lit while burning"))
                .thenWaitUntil(() -> helper.assertFalse(helper.getBlockState(MACHINE).getValue(BaseMachineBlock.ON), "the forge stayed lit after its fuel ran out"))
                .thenSucceed();
    }

    private static void machineComparatorReadsItsContents(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.BASIC_ALLOY_FORGE.get());
        BlockPos absolute = helper.absolutePos(MACHINE);
        helper.assertValueEqual(signal(helper, absolute), 0, "an empty forge's comparator signal");

        helper.getBlockEntity(MACHINE, BaseMachineBlockEntity.class).getInventory(null).setStackInSlot(3, new ItemStack(MachinesItems.STEEL_INGOT.get(), 64));
        helper.assertTrue(signal(helper, absolute) > 0, "a forge holding steel gives a comparator no signal");
        helper.succeed();
    }

    /** Facing the player, as a furnace does, and named after an anvil-renamed item. */
    private static void machineFacesAndKeepsTheNameItWasPlacedWith(GameTestHelper helper) {
        ItemStack named = new ItemStack(MachinesBlocks.BASIC_GRINDING_MILL.get());
        named.set(DataComponents.CUSTOM_NAME, Component.literal("Ore Crusher"));
        ServerPlayer player = TestSupport.survivalPlayer(helper, named);
        // Yaw 0 looks south, so the machine's face turns north toward the player.
        TestSupport.stand(helper, player, MACHINE.south(2).below());
        TestSupport.useOnTopOf(helper, player, MACHINE.below());

        helper.assertBlockPresent(MachinesBlocks.BASIC_GRINDING_MILL.get(), MACHINE);
        Direction facing = helper.getBlockState(MACHINE).getValue(BaseMachineBlock.FACING);
        helper.assertValueEqual(facing, Direction.NORTH, "the placed mill's facing");
        Component name = helper.getBlockEntity(MACHINE, BaseMachineBlockEntity.class).getCustomName();
        helper.assertTrue(name != null && name.getString().equals("Ore Crusher"), "the placed mill is named " + name);
        helper.succeed();
    }

    private static int signal(GameTestHelper helper, BlockPos absolute) {
        return helper.getLevel().getBlockState(absolute).getAnalogOutputSignal(helper.getLevel(), absolute, Direction.NORTH);
    }
}
