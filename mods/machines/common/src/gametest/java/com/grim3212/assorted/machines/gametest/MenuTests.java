package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.lib.core.inventory.IMenuDataProvider;
import com.grim3212.assorted.lib.core.inventory.MenuData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Machine menus built on the client.
 */
final class MenuTests {

    private MenuTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("machine_menus_rebuild_on_the_client", MenuTests::machineMenusRebuildOnTheClient);
    }

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
}
