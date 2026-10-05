package com.grim3212.assorted.machines;

import com.grim3212.assorted.lib.crafting.SyncedRecipes;
import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipeSerializer;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipeSerializer;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.MachinesBlockEntityTypes;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeBookCategories;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeDisplays;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeSerializers;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.grim3212.assorted.machines.common.handlers.MachinesCreativeItems;
import com.grim3212.assorted.machines.common.inventory.MachinesContainerTypes;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import com.grim3212.assorted.machines.config.MachinesCommonConfig;
import net.minecraft.resources.Identifier;

public class MachinesCommonMod {

    public static final MachinesCommonConfig COMMON_CONFIG = new MachinesCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");
        Families.join(Constants.MOD_ID, Constants.FAMILY_ID)
                .icon(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "machine_core"), 10)
                .manualOrder(20);

        MachinesBlocks.init();
        MachinesItems.init();
        MachinesBlockEntityTypes.init();
        MachinesRecipeSerializers.init();
        MachinesRecipeTypes.init();
        MachinesRecipeBookCategories.init();
        MachinesRecipeDisplays.init();
        MachinesContainerTypes.init();
        MachinesCreativeItems.init();

        // The machine screens, JEI and the manual all read whole recipes on the client.
        SyncedRecipes.require(MachinesRecipeTypes.ALLOY_FORGE, AlloyForgeRecipeSerializer.INSTANCE);
        SyncedRecipes.require(MachinesRecipeTypes.GRINDING_MILL, GrindingMillRecipeSerializer.INSTANCE);

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Constants.FAMILY_ID, Constants.MOD_ID);
    }
}
