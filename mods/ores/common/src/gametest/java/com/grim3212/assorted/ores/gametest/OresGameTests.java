package com.grim3212.assorted.ores.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Ores. The tests live in the {@code *Tests} classes; this only
 * lists them.
 */
public final class OresGameTests {

    private OresGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        OreTests.register(out);
        DustAndGearTests.register(out);
        AssetTests.register(out);
        ManualLinkTests.register(out);
        CrossLoaderDataTests.register(out);
        AliasTests.register(out);
        FamilyTests.register(out);
    }
}
