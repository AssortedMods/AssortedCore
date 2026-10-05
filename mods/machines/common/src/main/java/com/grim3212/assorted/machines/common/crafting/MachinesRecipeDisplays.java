package com.grim3212.assorted.machines.common.crafting;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.crafting.MachineRecipeDisplay;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

/** Registered on both sides: a {@code RecipeDisplay} reaches a client by its registered id. */
public class MachinesRecipeDisplays {

    public static final RegistryProvider<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = RegistryProvider.create(Registries.RECIPE_DISPLAY, Constants.MOD_ID);

    public static final IRegistryObject<RecipeDisplay.Type<MachineRecipeDisplay>> MACHINE = RECIPE_DISPLAYS.register("machine", () -> MachineRecipeDisplay.TYPE);

    public static void init() {
    }
}
