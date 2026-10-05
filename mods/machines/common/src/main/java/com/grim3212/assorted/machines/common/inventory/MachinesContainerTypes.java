package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class MachinesContainerTypes {
    public static final RegistryProvider<MenuType<?>> MENU_TYPES = RegistryProvider.create(Registries.MENU, Constants.MOD_ID);

    public static final IRegistryObject<MenuType<AlloyForgeContainer>> ALLOY_FORGE = MENU_TYPES.register("alloy_forge", () -> Services.PLATFORM.createMenuType(AlloyForgeContainer::new));
    public static final IRegistryObject<MenuType<GrindingMillContainer>> GRINDING_MILL = MENU_TYPES.register("grinding_mill", () -> Services.PLATFORM.createMenuType(GrindingMillContainer::new));

    public static void init() {
    }


}
