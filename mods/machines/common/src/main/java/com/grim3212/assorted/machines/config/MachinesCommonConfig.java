package com.grim3212.assorted.machines.config;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.lib.config.ConfigurationType;
import com.grim3212.assorted.lib.config.IConfigurationBuilder;
import com.grim3212.assorted.lib.platform.Services;

import java.util.function.Supplier;

public class MachinesCommonConfig {

    public final Supplier<Boolean> grindingMillHasBreakSound;

    public MachinesCommonConfig() {
        final IConfigurationBuilder builder = Services.CONFIG.createBuilder(ConfigurationType.NOT_SYNCED, Constants.MOD_ID + "-common");

        grindingMillHasBreakSound = builder.defineBoolean("general.grindingMillHasBreakSound", true, "Set to true if you want the grinding mill to play the break sound when a block is broken.");

        builder.setup();
    }
}
