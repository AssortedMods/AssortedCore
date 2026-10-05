package com.grim3212.assorted.ores;

import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.ores.common.handlers.OresCreativeItems;
import com.grim3212.assorted.ores.common.items.OresItems;
import com.grim3212.assorted.ores.common.worldgen.OresBiomeModifiers;
import net.minecraft.resources.Identifier;

public class OresCommonMod {

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");
        Families.join(Constants.MOD_ID, Constants.FAMILY_ID)
                .icon(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "platinum_ore"), 20)
                .manualOrder(20);

        OresBlocks.init();
        OresItems.init();
        OresBiomeModifiers.init();
        OresCreativeItems.init();

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Constants.FAMILY_ID, Constants.MOD_ID);
    }
}
