package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.GrindingMillBlockEntity;
import com.grim3212.assorted.machines.common.inventory.BaseMachineInventory;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/** The mill's pickaxe as a cost: each grind wears it, and a broken one stops the mill. */
final class GrindingMillToolTests {

    private GrindingMillToolTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("grinding_mill_wears_out_its_tool", GrindingMillToolTests::grindingMillWearsOutItsTool);
    }

    /** One use left, two ingots: the first grinds and breaks the pickaxe, the second waits for a new one. */
    private static void grindingMillWearsOutItsTool(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_GRINDING_MILL.get());
        GrindingMillBlockEntity mill = helper.getBlockEntity(MACHINE, GrindingMillBlockEntity.class);
        BaseMachineInventory inventory = mill.getInventory(null);

        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        pickaxe.setDamageValue(pickaxe.getMaxDamage() - 1);
        inventory.setStackInSlot(1, pickaxe);
        inventory.setStackInSlot(2, new ItemStack(Items.COAL, 8));
        inventory.setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 2));

        for (int tick = 0; tick < 1000; tick++) {
            mill.tick();
        }

        helper.assertTrue(inventory.getStackInSlot(1).isEmpty(), "the pickaxe with one use left was " + inventory.getStackInSlot(1) + " after grinding");
        ItemStack dust = inventory.getStackInSlot(3);
        helper.assertTrue(dust.is(MachinesItems.COPPER_DUST.get()) && dust.getCount() == 1, "the mill made " + dust + " with one use of its pickaxe");
        helper.assertValueEqual(inventory.getStackInSlot(0).getCount(), 1, "copper ingots left once the pickaxe broke");
        helper.succeed();
    }
}
