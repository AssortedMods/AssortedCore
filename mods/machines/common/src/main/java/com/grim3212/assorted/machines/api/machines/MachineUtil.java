package com.grim3212.assorted.machines.api.machines;

import com.grim3212.assorted.lib.crafting.SyncedRecipes;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IPlatformHelper;
import com.grim3212.assorted.machines.api.MachinesTags;
import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Stream;

public class MachineUtil {

    public static boolean allowedInGrindingMillToolSlot(ItemStack stack) {
        if (stack.is(MachinesTags.Items.GRINDING_MILL_ALLOWED_TOOLS))
            return true;

        return Services.PLATFORM.isTieredTool(stack, IPlatformHelper.ToolTier.IRON, IPlatformHelper.ToolType.PICKAXE);
    }

    /**
     * Every loaded recipe of one type. Each side of a level has its own recipe manager, the client's
     * filled from what the server sent; without a level, as for JEI, that same set is read from
     * {@link SyncedRecipes}.
     */
    public static <T extends BaseMachineRecipe> Stream<T> recipesOfType(@Nullable Level level, RecipeType<T> recipeType) {
        Collection<RecipeHolder<T>> recipes = level != null
                ? level.getRecipeManager().getAllRecipesFor(recipeType)
                : SyncedRecipes.byType(recipeType);

        return recipes.stream().map(RecipeHolder::value);
    }

    public static boolean isValidAlloyForgeInput(@Nullable Level level, ItemStack stack) {
        return recipesOfType(level, MachinesRecipeTypes.ALLOY_FORGE.get()).anyMatch((recipe) -> recipe.validItem(stack));
    }

    public static boolean isValidGrindingMillInput(@Nullable Level level, ItemStack stack) {
        return recipesOfType(level, MachinesRecipeTypes.GRINDING_MILL.get()).anyMatch((recipe) -> recipe.validItem(stack));
    }
}
