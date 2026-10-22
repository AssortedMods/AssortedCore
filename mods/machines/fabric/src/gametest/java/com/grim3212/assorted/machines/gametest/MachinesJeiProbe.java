package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.machines.Constants;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

/** A test-only JEI plugin, as a plugin is the only way JEI hands out its runtime to read back what it shows. */
public class MachinesJeiProbe implements IModPlugin {

    @Nullable
    static volatile IJeiRuntime runtime;

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gametest_probe");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }

    @Override
    public void onRuntimeUnavailable() {
        runtime = null;
    }
}
