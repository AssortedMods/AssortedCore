package com.grim3212.assorted.machines;

import com.grim3212.assorted.machines.client.MachinesClient;
import net.fabricmc.api.ClientModInitializer;

public class AssortedMachinesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MachinesClient.init();
    }
}
