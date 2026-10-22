package com.grim3212.assorted.ores.gametest;

import com.grim3212.assorted.lib.test.ClientChecks;
import com.grim3212.assorted.ores.Constants;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;

/** What only a client shows: every ore, raw ore and metal drawn with its own art, and the manual pages that name them. */
public class OresClientGameTests implements FabricClientGameTest {

    @Override
    public void runTest(ClientGameTestContext context) {
        // Inside a world, as recipes and tags reach the manual from one.
        try (TestSingleplayerContext world = context.worldBuilder().create()) {
            ClientChecks.assertDrawsRight(context, Constants.MOD_ID);
        }
    }
}
