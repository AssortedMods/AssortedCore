package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.api.MachinesTags;
import com.grim3212.assorted.machines.api.machines.MachineUtil;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.AlloyForgeBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.GrindingMillBlockEntity;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.*;

/**
 * The alloy forge and grinding mill: recipes, tool slot, tiers, hoppers, drops and save/load.
 */
final class MachineTests {

    private MachineTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("alloy_forge_makes_steel", MachineTests::alloyForgeMakesSteel);
        out.accept("alloy_forge_makes_bronze", MachineTests::alloyForgeMakesBronze);
        out.accept("grinding_mill_makes_dust", MachineTests::grindingMillMakesDust);
        out.accept("grinding_mill_grinds_tin_ore", MachineTests::grindingMillGrindsTinOre);
        out.accept("grinding_mill_tool_slot", MachineTests::grindingMillToolSlot);
        out.accept("machine_drops_contents", MachineTests::machineDropsContents);
        out.accept("hoppers_feed_and_empty_machine", MachineTests::hoppersFeedAndEmptyMachine);
        out.accept("machine_recipes_craft", MachineTests::machineRecipesCraft);
        out.accept("machine_tiers_get_faster", MachineTests::machineTiersGetFaster);
        out.accept("machine_keeps_contents_across_reload", MachineTests::machineKeepsContentsAcrossReload);
    }

    /** 4 coal + 1 iron dust -> 1 steel ingot, the one alloy that needs nothing from Assorted Ores. */
    private static void alloyForgeMakesSteel(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        AlloyForgeBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);

        // Through the inventory handler, not getItems(): only the handler sets cookTimeTotal.
        loadSteel(forge);

        helper.succeedWhen(() -> {
            ItemStack result = forge.getInventory(null).getStackInSlot(3);
            helper.assertTrue(result.is(MachinesItems.STEEL_INGOT.get()), "alloy forge did not produce steel");
            helper.assertValueEqual(result.getCount(), 1, "steel ingot count");
        });
    }

    /** 3 copper dust + 1 tin dust -> 4 bronze ingots, once Assorted Ores or another mod adds tin dust. */
    private static void alloyForgeMakesBronze(GameTestHelper helper) {
        Optional<ItemStack> tinDust = anyIn(helper, MachinesTags.Items.DUSTS_TIN);
        if (tinDust.isEmpty()) {
            helper.succeed();
            return;
        }

        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        AlloyForgeBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);

        forge.getInventory(null).setStackInSlot(0, new ItemStack(MachinesItems.COPPER_DUST.get(), 3));
        forge.getInventory(null).setStackInSlot(1, tinDust.get());
        forge.getInventory(null).setStackInSlot(2, new ItemStack(Items.COAL, 8));

        helper.succeedWhen(() -> {
            ItemStack result = forge.getInventory(null).getStackInSlot(3);
            helper.assertTrue(result.is(MachinesItems.BRONZE_INGOT.get()), "alloy forge did not produce bronze");
            helper.assertValueEqual(result.getCount(), 4, "bronze ingot count");
        });
    }

    /** A copper ingot plus a pickaxe in the tool slot grinds to copper dust. */
    private static void grindingMillMakesDust(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_GRINDING_MILL.get());
        GrindingMillBlockEntity mill = helper.getBlockEntity(MACHINE, GrindingMillBlockEntity.class);

        mill.getInventory(null).setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 1));
        mill.getInventory(null).setStackInSlot(1, new ItemStack(Items.IRON_PICKAXE));
        mill.getInventory(null).setStackInSlot(2, new ItemStack(Items.COAL, 8));

        helper.succeedWhen(() -> helper.assertTrue(
                mill.getInventory(null).getStackInSlot(3).is(MachinesItems.COPPER_DUST.get()),
                "grinding mill did not produce copper dust"));
    }

    /** With Assorted Ores installed too, its tin ore grinds into two of its tin dust through its own recipe. */
    private static void grindingMillGrindsTinOre(GameTestHelper helper) {
        Optional<Item> ore = BuiltInRegistries.ITEM.getOptional(Identifier.fromNamespaceAndPath("assortedores", "tin_ore"));
        Optional<Item> dust = BuiltInRegistries.ITEM.getOptional(Identifier.fromNamespaceAndPath("assortedores", "tin_dust"));
        if (ore.isEmpty() || dust.isEmpty()) {
            helper.succeed();
            return;
        }

        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_GRINDING_MILL.get());
        GrindingMillBlockEntity mill = helper.getBlockEntity(MACHINE, GrindingMillBlockEntity.class);

        mill.getInventory(null).setStackInSlot(0, new ItemStack(ore.get(), 1));
        mill.getInventory(null).setStackInSlot(1, new ItemStack(Items.IRON_PICKAXE));
        mill.getInventory(null).setStackInSlot(2, new ItemStack(Items.COAL, 8));

        helper.succeedWhen(() -> {
            ItemStack result = mill.getInventory(null).getStackInSlot(3);
            helper.assertTrue(result.is(dust.get()), "grinding mill made " + result + " from tin ore instead of tin dust");
            helper.assertValueEqual(result.getCount(), 2, "tin dust count");
        });
    }

    /** The tool slot takes iron and above, and nothing softer. */
    private static void grindingMillToolSlot(GameTestHelper helper) {
        helper.assertTrue(MachineUtil.allowedInGrindingMillToolSlot(new ItemStack(Items.IRON_PICKAXE)),
                "iron pickaxe rejected by the grinding mill tool slot");
        helper.assertTrue(MachineUtil.allowedInGrindingMillToolSlot(new ItemStack(Items.NETHERITE_PICKAXE)),
                "netherite pickaxe rejected by the grinding mill tool slot");
        helper.assertFalse(MachineUtil.allowedInGrindingMillToolSlot(new ItemStack(Items.WOODEN_PICKAXE)),
                "wooden pickaxe accepted by the grinding mill tool slot");
        helper.assertFalse(MachineUtil.allowedInGrindingMillToolSlot(new ItemStack(Items.IRON_SWORD)),
                "iron sword accepted by the grinding mill tool slot");
        helper.succeed();
    }

    /** Breaking a machine spills what was inside it - this regressed once already. */
    private static void machineDropsContents(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.BASIC_ALLOY_FORGE.get());
        BaseMachineBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);
        forge.getInventory(null).setStackInSlot(0, new ItemStack(MachinesItems.COPPER_DUST.get(), 2));

        helper.destroyBlock(MACHINE);
        helper.succeedWhen(() -> helper.assertItemEntityPresent(MachinesItems.COPPER_DUST.get(), MACHINE, 3.0D));
    }

    /**
     * A hopper above feeds an ingredient in and a hopper below takes the result out, through each
     * loader's sided inventory. Only the DOWN face exposes the output slot.
     */
    private static void hoppersFeedAndEmptyMachine(GameTestHelper helper) {
        BlockPos above = MACHINE.above();
        BlockPos below = MACHINE.below();

        helper.setBlock(MACHINE, MachinesBlocks.BASIC_ALLOY_FORGE.get());
        helper.setBlock(above, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.DOWN));
        helper.setBlock(below, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.DOWN));

        helper.getBlockEntity(above, HopperBlockEntity.class)
                .setItem(0, new ItemStack(MachinesItems.IRON_DUST.get(), 1));

        BaseMachineBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);
        forge.getInventory(null).setStackInSlot(3, new ItemStack(MachinesItems.BRONZE_INGOT.get(), 1));

        helper.succeedWhen(() -> {
            helper.assertTrue(forge.getInventory(null).getStackInSlot(0).is(MachinesItems.IRON_DUST.get()),
                    "hopper above did not insert into the machine");
            helper.assertContainerContains(below, MachinesItems.BRONZE_INGOT.get());
        });
    }

    /**
     * The machine core and both machine lines through every tier. The aluminum core and the expert tier need
     * aluminum and platinum, so they are only checked when Assorted Ores or another mod adds them.
     */
    private static void machineRecipesCraft(GameTestHelper helper) {
        ItemStack iron = new ItemStack(Items.IRON_INGOT);
        ItemStack ironGear = new ItemStack(MachinesItems.IRON_GEAR.get());
        ItemStack steel = new ItemStack(MachinesItems.STEEL_INGOT.get());
        ItemStack invar = new ItemStack(MachinesItems.INVAR_INGOT.get());
        ItemStack electrum = new ItemStack(MachinesItems.ELECTRUM_INGOT.get());
        Optional<ItemStack> aluminum = anyIn(helper, MachinesTags.Items.INGOTS_ALUMINUM);
        Optional<ItemStack> platinum = anyIn(helper, MachinesTags.Items.INGOTS_PLATINUM);

        ItemStack copperGear = new ItemStack(MachinesItems.COPPER_GEAR.get());
        assertCrafts(helper, 3, 3, ring(iron, new ItemStack(Items.COPPER_INGOT), copperGear),
                MachinesBlocks.MACHINE_CORE.get().asItem(), 1, "machine core from copper");
        aluminum.ifPresent(ingot -> assertCrafts(helper, 3, 3, ring(iron, ingot, copperGear),
                MachinesBlocks.MACHINE_CORE.get().asItem(), 1, "machine core"));

        ItemStack core = new ItemStack(MachinesBlocks.MACHINE_CORE.get());
        assertCrafts(helper, 3, 3, List.of(iron, new ItemStack(Items.IRON_PICKAXE), iron,
                        ironGear, core, ironGear,
                        iron, new ItemStack(Items.FURNACE), iron),
                MachinesBlocks.BASIC_GRINDING_MILL.get().asItem(), 1, "basic grinding mill");
        assertCrafts(helper, 3, 3, List.of(iron, iron, iron,
                        new ItemStack(Items.BLAST_FURNACE), core, new ItemStack(Items.BLAST_FURNACE),
                        iron, iron, iron),
                MachinesBlocks.BASIC_ALLOY_FORGE.get().asItem(), 1, "basic alloy forge");

        Block[][] lines = {
                {MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(),
                        MachinesBlocks.ADVANCED_GRINDING_MILL.get(), MachinesBlocks.EXPERT_GRINDING_MILL.get()},
                {MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(),
                        MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachinesBlocks.EXPERT_ALLOY_FORGE.get()}};

        for (Block[] line : lines) {
            assertCrafts(helper, 3, 3, ring(steel, steel, new ItemStack(line[0])), line[1].asItem(), 1,
                    String.valueOf(BuiltInRegistries.BLOCK.getKey(line[1])));
            assertCrafts(helper, 3, 3, ring(invar, electrum, new ItemStack(line[1])), line[2].asItem(), 1,
                    String.valueOf(BuiltInRegistries.BLOCK.getKey(line[2])));
            platinum.ifPresent(ingot -> assertCrafts(helper, 3, 3, cross(ingot, new ItemStack(line[2])), line[3].asItem(), 1,
                    String.valueOf(BuiltInRegistries.BLOCK.getKey(line[3]))));
        }

        helper.succeed();
    }

    /**
     * Each tier cooks faster than the one below. {@code getCookTime()} is what {@code tick()}
     * counts up to, so checking it shrinks avoids running every machine to completion.
     */
    private static void machineTiersGetFaster(GameTestHelper helper) {
        Block[] mills = {MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(),
                MachinesBlocks.ADVANCED_GRINDING_MILL.get(), MachinesBlocks.EXPERT_GRINDING_MILL.get()};
        Block[] forges = {MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(),
                MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachinesBlocks.EXPERT_ALLOY_FORGE.get()};

        int[] millTimes = new int[4];
        int[] forgeTimes = new int[4];

        for (int tier = 0; tier < 4; tier++) {
            BlockPos millPos = new BlockPos(1 + tier * 2, 1, 1);
            helper.setBlock(millPos, mills[tier]);
            GrindingMillBlockEntity mill = helper.getBlockEntity(millPos, GrindingMillBlockEntity.class);
            mill.getInventory(null).setStackInSlot(1, new ItemStack(Items.IRON_PICKAXE));
            mill.getInventory(null).setStackInSlot(0, new ItemStack(Items.COPPER_INGOT));
            millTimes[tier] = mill.getCookTime();

            BlockPos forgePos = new BlockPos(1 + tier * 2, 1, 3);
            helper.setBlock(forgePos, forges[tier]);
            AlloyForgeBlockEntity forge = helper.getBlockEntity(forgePos, AlloyForgeBlockEntity.class);
            loadSteel(forge);
            forgeTimes[tier] = forge.getCookTime();
        }

        helper.assertTrue(millTimes[0] > 0, "the basic grinding mill has no cook time at all");
        helper.assertTrue(forgeTimes[0] > 0, "the basic alloy forge has no cook time at all");

        for (int tier = 1; tier < 4; tier++) {
            helper.assertTrue(millTimes[tier] < millTimes[tier - 1],
                    "grinding mill tier " + tier + " cooks in " + millTimes[tier] + ", no faster than " + millTimes[tier - 1]);
            helper.assertTrue(forgeTimes[tier] < forgeTimes[tier - 1],
                    "alloy forge tier " + tier + " cooks in " + forgeTimes[tier] + ", no faster than " + forgeTimes[tier - 1]);
        }

        helper.succeed();
    }

    /**
     * A machine caught mid-smelt survives being written out and read back: its items, its burn time
     * and its cook progress all come back. It is run forward first so the progress being saved is
     * real rather than poked into the fields.
     */
    private static void machineKeepsContentsAcrossReload(GameTestHelper helper) {
        helper.setBlock(MACHINE, MachinesBlocks.EXPERT_ALLOY_FORGE.get());
        AlloyForgeBlockEntity forge = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);
        loadSteel(forge);

        helper.runAfterDelay(20L, () -> {
            AlloyForgeBlockEntity running = helper.getBlockEntity(MACHINE, AlloyForgeBlockEntity.class);
            RegistryAccess registries = helper.getLevel().registryAccess();
            BlockPos absolute = helper.absolutePos(MACHINE);
            BlockState state = helper.getLevel().getBlockState(absolute);

            CompoundTag saved = running.saveWithFullMetadata(registries);
            helper.assertTrue(saved.getIntOr("BurnTime", 0) > 0, "the machine was not burning, so the reload would prove nothing");
            helper.assertTrue(saved.getIntOr("CookTime", 0) > 0, "the machine had no cook progress, so the reload would prove nothing");

            BlockEntity reloaded = BlockEntity.loadStatic(absolute, state, saved, registries);
            helper.assertTrue(reloaded instanceof AlloyForgeBlockEntity, "the saved machine did not load back as an alloy forge");

            AlloyForgeBlockEntity loaded = (AlloyForgeBlockEntity) reloaded;
            helper.assertValueEqual(loaded.getItems().size(), running.getItems().size(), "reloaded machine slot count");
            for (int slot = 0; slot < running.getItems().size(); slot++) {
                helper.assertTrue(ItemStack.matches(running.getItems().get(slot), loaded.getItems().get(slot)),
                        "slot " + slot + " did not survive the reload: " + running.getItems().get(slot) + " became " + loaded.getItems().get(slot));
            }
            helper.assertTrue(saved.equals(loaded.saveWithFullMetadata(registries)),
                    "the machine's saved data changed across a save/load round trip");

            helper.succeed();
        });
    }

    /** A steel smelt with fuel to spare. */
    private static void loadSteel(AlloyForgeBlockEntity forge) {
        forge.getInventory(null).setStackInSlot(0, new ItemStack(Items.COAL, 4));
        forge.getInventory(null).setStackInSlot(1, new ItemStack(MachinesItems.IRON_DUST.get(), 1));
        forge.getInventory(null).setStackInSlot(2, new ItemStack(Items.COAL, 8));
    }
}
