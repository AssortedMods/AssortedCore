package com.grim3212.assorted.machines.common.crafting;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;

/** A recipe book tab per machine. Registered on both sides: a {@code RecipeDisplayEntry} is sent by category. */
public class MachinesRecipeBookCategories {

    public static final RegistryProvider<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = RegistryProvider.create(Registries.RECIPE_BOOK_CATEGORY, Constants.MOD_ID);

    public static final IRegistryObject<RecipeBookCategory> ALLOY_FORGE = RECIPE_BOOK_CATEGORIES.register("alloy_forge", RecipeBookCategory::new);
    public static final IRegistryObject<RecipeBookCategory> GRINDING_MILL = RECIPE_BOOK_CATEGORIES.register("grinding_mill", RecipeBookCategory::new);

    public static void init() {
    }
}
