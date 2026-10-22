package com.grim3212.assorted.ores.gametest;

import com.grim3212.assorted.ores.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Which pickaxe gets something out of each ore and metal block, as the tier tags promise, and that nothing softer does. */
final class MiningTierTests {

    /** In order of tier, so the one below the needed tier is the index before it. */
    private static final List<Item> PICKAXES = List.of(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE);

    private MiningTierTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("ores_need_the_right_pickaxe", MiningTierTests::oresNeedTheRightPickaxe);
    }

    private static void oresNeedTheRightPickaxe(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        int checked = 0;

        for (Block block : BuiltInRegistries.BLOCK) {
            BlockState state = block.defaultBlockState();
            if (!Constants.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()) || !state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                continue;
            }
            checked++;
            String name = BuiltInRegistries.BLOCK.getKey(block).toString();
            int needed = state.is(BlockTags.NEEDS_DIAMOND_TOOL) ? 3 : state.is(BlockTags.NEEDS_IRON_TOOL) ? 2 : state.is(BlockTags.NEEDS_STONE_TOOL) ? 1 : 0;

            if (!state.requiresCorrectToolForDrops()) {
                problems.add(name + " drops for any tool, even a bare hand");
            }
            if (!new ItemStack(PICKAXES.get(needed)).isCorrectToolForDrops(state)) {
                problems.add(name + " gives nothing to a " + BuiltInRegistries.ITEM.getKey(PICKAXES.get(needed)).getPath());
            }
            if (needed > 0 && new ItemStack(PICKAXES.get(needed - 1)).isCorrectToolForDrops(state)) {
                problems.add(name + " gives something to a " + BuiltInRegistries.ITEM.getKey(PICKAXES.get(needed - 1)).getPath());
            }
        }

        helper.assertTrue(checked > 0, "no Assorted Ores block is mineable with a pickaxe");
        helper.assertTrue(problems.isEmpty(), String.join("\n", problems));
        helper.succeed();
    }
}
