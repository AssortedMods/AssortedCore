package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.lib.test.TestSupport;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.AlloyForgeBlockEntity;
import com.grim3212.assorted.machines.common.inventory.BaseMachineInventory;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/** The experience a machine banks as it cooks, paid out like a furnace's: to whoever takes the result, or onto the floor when it is broken. */
final class MachineExperienceTests {

    private MachineExperienceTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("taking_a_machine_result_gives_experience", MachineExperienceTests::takingAMachineResultGivesExperience);
        out.accept("breaking_a_machine_drops_its_experience", MachineExperienceTests::breakingAMachineDropsItsExperience);
    }

    private static void takingAMachineResultGivesExperience(GameTestHelper helper) {
        AlloyForgeBlockEntity forge = twoSteelSmelts(helper);
        ServerPlayer player = TestSupport.survivalPlayer(helper);
        TestSupport.stand(helper, player, MACHINE.south(2).below());

        AbstractContainerMenu menu = forge.createMenu(1, player.getInventory(), player);
        menu.quickMoveStack(player, 3);
        helper.assertValueEqual(TestSupport.countInInventory(player, MachinesItems.STEEL_INGOT.get()), 2, "steel taken by shift click");
        helper.assertTrue(orbs(helper, player.blockPosition()) > 0, "taking two steel ingots out of the forge gave no experience");
        helper.succeed();
    }

    private static void breakingAMachineDropsItsExperience(GameTestHelper helper) {
        twoSteelSmelts(helper);
        helper.destroyBlock(MACHINE);
        helper.assertTrue(orbs(helper, helper.absolutePos(MACHINE)) > 0, "breaking a forge that smelted two steel ingots dropped no experience");
        helper.succeed();
    }

    /** Steel is worth half a point, so two smelts bank at least one whole point. */
    private static AlloyForgeBlockEntity twoSteelSmelts(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        AlloyForgeBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);
        BaseMachineInventory inventory = forge.getInventory(null);
        inventory.setStackInSlot(2, new ItemStack(Items.COAL_BLOCK, 4));
        inventory.setStackInSlot(0, new ItemStack(Items.COAL, 8));
        inventory.setStackInSlot(1, new ItemStack(MachinesItems.IRON_DUST.get(), 2));

        for (int tick = 0; tick < 2000 && inventory.getStackInSlot(3).getCount() < 2; tick++) {
            forge.tick();
        }
        helper.assertValueEqual(inventory.getStackInSlot(3).getCount(), 2, "steel ingots smelted");
        return forge;
    }

    private static int orbs(GameTestHelper helper, BlockPos around) {
        return helper.getLevel().getEntitiesOfClass(ExperienceOrb.class, new AABB(around).inflate(3.0D)).stream()
                .mapToInt(ExperienceOrb::getValue)
                .sum();
    }
}
