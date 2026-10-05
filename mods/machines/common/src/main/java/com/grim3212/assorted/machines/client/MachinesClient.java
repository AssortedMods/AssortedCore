package com.grim3212.assorted.machines.client;

import com.grim3212.assorted.machines.client.screen.AlloyForgeScreen;
import com.grim3212.assorted.machines.client.screen.GrindingMillScreen;
import com.grim3212.assorted.machines.common.inventory.MachinesContainerTypes;
import com.grim3212.assorted.lib.platform.ClientServices;
import com.grim3212.assorted.lib.platform.Services;

public class MachinesClient {
    public static void init() {
        ClientServices.CLIENT.registerScreen(MachinesContainerTypes.ALLOY_FORGE::get, AlloyForgeScreen::new);
        ClientServices.CLIENT.registerScreen(MachinesContainerTypes.GRINDING_MILL::get, GrindingMillScreen::new);
    }
}
