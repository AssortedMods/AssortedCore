package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.lib.test.TestSupport;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.lib.core.inventory.IMenuDataProvider;
import com.grim3212.assorted.lib.core.inventory.MenuData;
import com.grim3212.assorted.machines.common.blocks.blockentity.AlloyForgeBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.GrindingMillBlockEntity;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.MACHINE;

/**
 * Machine menus built on the client, and how they sort a shift click.
 */
final class MenuTests {

    private MenuTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("machine_menus_rebuild_on_the_client", MenuTests::machineMenusRebuildOnTheClient);
        out.accept("machine_menus_shift_click_to_the_right_slots", MenuTests::machineMenusShiftClickToTheRightSlots);
    }

    /** The first slot of the player's own inventory in both menus. */
    private static final int PLAYER_SLOT = 4;

    /**
     * A machine's client menu needs nothing from the server, so it opens as a vanilla menu: the
     * client builds it from the menu type alone, and it matches what the server opened.
     */
    private static void machineMenusRebuildOnTheClient(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        List<Block> machines = List.of(MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.BASIC_GRINDING_MILL.get());

        for (int i = 0; i < machines.size(); i++) {
            Block machine = machines.get(i);
            String name = BuiltInRegistries.BLOCK.getKey(machine).toString();
            BlockPos rel = new BlockPos(2 + i * 4, 1, 4);
            helper.setBlock(rel, machine);
            MenuProvider provider = helper.getBlockState(rel).getMenuProvider(helper.getLevel(), helper.absolutePos(rel));
            if (provider == null) {
                helper.fail(name + " has no menu provider");
                return;
            }
            helper.assertFalse(provider instanceof IMenuDataProvider<?>, name + " provides menu data it does not need");

            AbstractContainerMenu server = provider.createMenu(1, player.getInventory(), player);
            helper.assertFalse(MenuData.hasData(server.getType()), name + " opens a menu type that expects data");

            AbstractContainerMenu client = server.getType().create(1, player.getInventory());
            helper.assertTrue(client.getClass() == server.getClass(), name + " built a " + client.getClass().getSimpleName() + " on the client");
            helper.assertValueEqual(client.slots.size(), server.slots.size(), name + " client menu slots");
        }

        helper.succeed();
    }

    /** Shift clicking from the inventory sorts an item to the slot it belongs in, and a result back out. */
    private static void machineMenusShiftClickToTheRightSlots(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        helper.setBlock(MACHINE, MachinesBlocks.BASIC_GRINDING_MILL.get());
        AbstractContainerMenu mill = helper.getBlockEntity(MACHINE, GrindingMillBlockEntity.class).createMenu(1, player.getInventory(), player);
        assertShiftClick(helper, player, mill, new ItemStack(Items.COPPER_INGOT), 0, "a copper ingot into the grinding mill");
        assertShiftClick(helper, player, mill, new ItemStack(Items.IRON_PICKAXE), 1, "an iron pickaxe into the grinding mill");
        // A fuel no mill recipe takes, as the mill sorts recipe inputs before fuel.
        assertShiftClick(helper, player, mill, new ItemStack(Items.BLAZE_ROD), 2, "a blaze rod into the grinding mill");

        BlockPos forgePos = MACHINE.east(2);
        helper.setBlock(forgePos, MachinesBlocks.BASIC_ALLOY_FORGE.get());
        AlloyForgeBlockEntity forgeEntity = helper.getBlockEntity(forgePos, AlloyForgeBlockEntity.class);
        AbstractContainerMenu forge = forgeEntity.createMenu(2, player.getInventory(), player);
        assertShiftClick(helper, player, forge, new ItemStack(MachinesItems.IRON_DUST.get()), 0, "iron dust into the alloy forge");
        assertShiftClick(helper, player, forge, new ItemStack(Items.COAL), 2, "coal into the alloy forge");

        forgeEntity.getInventory(null).setStackInSlot(3, new ItemStack(MachinesItems.STEEL_INGOT.get(), 3));
        forge.quickMoveStack(player, 3);
        helper.assertTrue(forgeEntity.getInventory(null).getStackInSlot(3).isEmpty(), "shift clicking the forge's result left it in the forge");
        helper.assertValueEqual(TestSupport.countInInventory(player, MachinesItems.STEEL_INGOT.get()), 3, "steel shift clicked into the inventory");
        helper.succeed();
    }

    private static void assertShiftClick(GameTestHelper helper, Player player, AbstractContainerMenu menu, ItemStack stack, int expected, String what) {
        menu.getSlot(PLAYER_SLOT).set(stack.copy());
        menu.quickMoveStack(player, PLAYER_SLOT);
        ItemStack landed = menu.getSlot(expected).getItem();
        helper.assertTrue(landed.is(stack.getItem()), "shift clicking " + what + " put " + landed + " in slot " + expected);
        menu.getSlot(expected).set(ItemStack.EMPTY);
        menu.getSlot(PLAYER_SLOT).set(ItemStack.EMPTY);
    }
}
