package com.grim3212.assorted.machines.api.machines;

import com.grim3212.assorted.machines.api.MachinesTags;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.grim3212.assorted.lib.crafting.SyncedRecipes;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IPlatformHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MachineUtil {

    public static boolean allowedInGrindingMillToolSlot(ItemStack stack) {
        if (stack.is(MachinesTags.Items.GRINDING_MILL_ALLOWED_TOOLS))
            return true;

        return Services.PLATFORM.isTieredTool(stack, IPlatformHelper.ToolTier.IRON, IPlatformHelper.ToolType.PICKAXE);
    }

    /**
     * Every loaded recipe of one type. The recipe manager only exists on the server, so the client
     * reads what the server sent it; the machine types are asked for in {@code MachinesCommonMod}.
     *
     * @see SyncedRecipes
     */
    @SuppressWarnings("unchecked")
    public static <T extends Recipe<?>> Stream<T> recipesOfType(@Nullable Level level, RecipeType<T> recipeType) {
        Stream<RecipeHolder<?>> recipes = level instanceof ServerLevel serverLevel
                ? serverLevel.recipeAccess().getRecipes().stream()
                : SyncedRecipes.recipes().values().stream();

        return recipes.map(RecipeHolder::value).filter((recipe) -> recipe.getType() == recipeType).map((recipe) -> (T) recipe);
    }

    public static boolean isValidAlloyForgeInput(@Nullable Level level, ItemStack stack) {
        return recipesOfType(level, MachinesRecipeTypes.ALLOY_FORGE.get()).anyMatch((recipe) -> recipe.validItem(stack));
    }

    public static boolean isValidGrindingMillInput(@Nullable Level level, ItemStack stack) {
        return recipesOfType(level, MachinesRecipeTypes.GRINDING_MILL.get()).anyMatch((recipe) -> recipe.validItem(stack));
    }
}
