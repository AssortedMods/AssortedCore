package com.grim3212.assorted.machines.common.crafting;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipeSerializer;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipeSerializer;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MachinesRecipeSerializers {

    // Other released mods ship recipes typed assortedcore:alloy_forge and assortedcore:grinding_mill.
    public static final RegistryProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistryProvider.create(Registries.RECIPE_SERIALIZER, Constants.MOD_ID).aliasFrom(Family.ID);

    public static final IRegistryObject<RecipeSerializer<?>> ALLOY_FORGE = RECIPE_SERIALIZERS.register("alloy_forge", () -> AlloyForgeRecipeSerializer.INSTANCE);
    public static final IRegistryObject<RecipeSerializer<?>> GRINDING_MILL = RECIPE_SERIALIZERS.register("grinding_mill", () -> GrindingMillRecipeSerializer.INSTANCE);

    public static void init() {
    }
}
