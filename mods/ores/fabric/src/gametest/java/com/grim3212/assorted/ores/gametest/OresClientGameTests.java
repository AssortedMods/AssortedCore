package com.grim3212.assorted.ores.gametest;

import com.grim3212.assorted.lib.test.ClientModelCheck;
import com.grim3212.assorted.lib.test.ManualCheck;
import com.grim3212.assorted.ores.Constants;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;

import java.util.ArrayList;
import java.util.List;

/** What only a client shows: every ore, raw ore and metal drawn with its own art, and the manual pages that name them. */
public class OresClientGameTests implements FabricClientGameTest {

    @Override
    public void runTest(ClientGameTestContext context) {
        // Inside a world, as recipes and tags reach the manual from one.
        try (TestSingleplayerContext world = context.worldBuilder().create()) {
            context.waitFor(client -> ManualCheck.recipesHaveSynced(), 200);
            List<String> problems = context.computeOnClient(client -> {
                List<String> found = new ArrayList<>(ClientModelCheck.problems(Constants.MOD_ID));
                found.addAll(ManualCheck.problems());
                return found;
            });
            if (!problems.isEmpty()) {
                throw new AssertionError("Assorted Ores does not draw right:\n  " + String.join("\n  ", problems));
            }
        }
    }
}
