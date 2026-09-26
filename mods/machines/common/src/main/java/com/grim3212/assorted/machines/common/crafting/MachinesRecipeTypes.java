package com.grim3212.assorted.machines.common.crafting;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipe;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipe;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class MachinesRecipeTypes {

    // Other released mods ship recipes typed assortedcore:alloy_forge and assortedcore:grinding_mill.
    public static final RegistryProvider<RecipeType<?>> RECIPE_TYPES = RegistryProvider.create(Registries.RECIPE_TYPE, Constants.MOD_ID).aliasFrom(Family.ID);

    public static final IRegistryObject<RecipeType<AlloyForgeRecipe>> ALLOY_FORGE = RECIPE_TYPES.register("alloy_forge", () -> createRecipeType(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "alloy_forge")));
    public static final IRegistryObject<RecipeType<GrindingMillRecipe>> GRINDING_MILL = RECIPE_TYPES.register("grinding_mill", () -> createRecipeType(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "grinding_mill")));

    static <T extends Recipe<?>> RecipeType<T> createRecipeType(Identifier location) {
        return new RecipeType<T>() {
            @Override
            public String toString() {
                return location.toString();
            }
        };
    }

    public static void init() {
    }
}
