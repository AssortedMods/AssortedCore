package com.grim3212.assorted.machines.api.crafting.builders;

import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipe;
import com.grim3212.assorted.machines.api.crafting.MachineIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builds a {@link GrindingMillRecipe} for data generation. See {@link AlloyForgeRecipeBuilder} for
 * why this no longer produces a {@code FinishedRecipe}.
 */
public class GrindingMillRecipeBuilder implements RecipeBuilder {

    private final ItemStackTemplate result;
    private final MachineIngredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    private GrindingMillRecipeBuilder(ItemStackTemplate resultIn, MachineIngredient ingredientIn, float experienceIn, int cookingTimeIn) {
        this.result = resultIn;
        this.ingredient = ingredientIn;
        this.experience = experienceIn;
        this.cookingTime = cookingTimeIn;
    }

    public static GrindingMillRecipeBuilder recipe(MachineIngredient ingredientIn, ItemStackTemplate resultIn, float experienceIn, int cookingTimeIn) {
        return new GrindingMillRecipeBuilder(resultIn, ingredientIn, experienceIn, cookingTimeIn);
    }

    public static GrindingMillRecipeBuilder recipe(Ingredient ingredientIn, ItemStackTemplate resultIn, float experienceIn, int cookingTimeIn) {
        return new GrindingMillRecipeBuilder(resultIn, new MachineIngredient(ingredientIn), experienceIn, cookingTimeIn);
    }

    @Override
    public GrindingMillRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public GrindingMillRecipeBuilder group(@Nullable String groupIn) {
        this.group = groupIn;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.validate(id);

        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        GrindingMillRecipe recipe = new GrindingMillRecipe(this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime);
        output.accept(id, recipe, advancement.build(id.identifier().withPrefix("recipes/")));
    }

    private void validate(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id.identifier());
        }
    }
}
