package com.grim3212.assorted.machines.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Machines. Tests that run the game forward use the EXPERT tier
 * so a smelt fits inside {@code max_ticks}. The tests live in the {@code *Tests} classes; this only
 * lists them.
 */
public final class MachinesGameTests {

    private MachinesGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        MachineTests.register(out);
        MenuTests.register(out);
        RecipeBookTests.register(out);
        MetalTests.register(out);
        AssetTests.register(out);
        ManualLinkTests.register(out);
        CrossLoaderDataTests.register(out);
        AliasTests.register(out);
        FamilyTests.register(out);
    }
}
