package com.grim3212.assorted.ores;

import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.ores.common.handlers.OresCreativeItems;
import com.grim3212.assorted.ores.common.items.OresItems;
import com.grim3212.assorted.ores.common.worldgen.OresBiomeModifiers;

public class OresCommonMod {

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        OresBlocks.init();
        OresItems.init();
        OresBiomeModifiers.init();
        OresCreativeItems.init();

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Family.ID, Constants.MOD_ID);
    }
}
