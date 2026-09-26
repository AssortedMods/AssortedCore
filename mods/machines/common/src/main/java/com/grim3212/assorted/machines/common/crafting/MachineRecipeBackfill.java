package com.grim3212.assorted.machines.common.crafting;

import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO(11.0.0): drop this and its two login hooks. It only exists for saves made before 10.1.0.
 * <p>
 * Unlocks the machine recipes a player already earned before they were in the recipe book. They
 * were {@code isSpecial} until 10.1.0, so {@code ServerRecipeBook} dropped them, and
 * {@code PlayerAdvancements} only grants a reward on the transition to done - so their recipe
 * advancement will never pay out again, and re-collecting the ingredients does nothing.
 */
public final class MachineRecipeBackfill {

    private MachineRecipeBackfill() {
    }

    /**
     * Cheap enough to run on every join, and idempotent through {@link ServerRecipeBook#contains}.
     * It does mean a recipe taken back with {@code /recipe take} returns on the next login.
     */
    public static void award(ServerPlayer player) {
        ServerLevel level = player.level();
        ServerAdvancementManager advancements = level.getServer().getAdvancements();
        ServerRecipeBook book = player.getRecipeBook();
        List<RecipeHolder<?>> earned = new ArrayList<>();

        for (RecipeHolder<?> holder : level.recipeAccess().getRecipes()) {
            if (!(holder.value() instanceof BaseMachineRecipe) || book.contains(holder.id())) {
                continue;
            }

            // The id the recipe builders give the generated advancement.
            AdvancementHolder advancement = advancements.get(holder.id().identifier().withPrefix("recipes/"));
            if (advancement != null && player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                earned.add(holder);
            }
        }

        if (!earned.isEmpty()) {
            player.awardRecipes(earned);
        }
    }
}
