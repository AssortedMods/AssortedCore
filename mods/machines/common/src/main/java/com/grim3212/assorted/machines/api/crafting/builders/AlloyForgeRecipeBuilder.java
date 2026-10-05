package com.grim3212.assorted.machines.api.crafting.builders;

import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipe;
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

/** Builds an {@link AlloyForgeRecipe} for data generation; the recipe's codec writes the json. */
public class AlloyForgeRecipeBuilder implements RecipeBuilder {

    private final ItemStackTemplate result;
    private final MachineIngredient ingredient1;
    private final MachineIngredient ingredient2;
    private final float experience;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    private AlloyForgeRecipeBuilder(ItemStackTemplate resultIn, MachineIngredient ingredient1In, MachineIngredient ingredient2In, float experienceIn, int cookingTimeIn) {
        this.result = resultIn;
        this.ingredient1 = ingredient1In;
        this.ingredient2 = ingredient2In;
        this.experience = experienceIn;
        this.cookingTime = cookingTimeIn;
    }

    public static AlloyForgeRecipeBuilder recipe(MachineIngredient ingredient1In, MachineIngredient ingredient2In, ItemStackTemplate resultIn, float experienceIn, int cookingTimeIn) {
        return new AlloyForgeRecipeBuilder(resultIn, ingredient1In, ingredient2In, experienceIn, cookingTimeIn);
    }

    public static AlloyForgeRecipeBuilder recipe(Ingredient ingredient1In, Ingredient ingredient2In, ItemStackTemplate resultIn, float experienceIn, int cookingTimeIn) {
        return new AlloyForgeRecipeBuilder(resultIn, new MachineIngredient(ingredient1In), new MachineIngredient(ingredient2In), experienceIn, cookingTimeIn);
    }

    @Override
    public AlloyForgeRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public AlloyForgeRecipeBuilder group(@Nullable String groupIn) {
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

        AlloyForgeRecipe recipe = new AlloyForgeRecipe(this.group == null ? "" : this.group, this.ingredient1, this.ingredient2, this.result, this.experience, this.cookingTime);
        output.accept(id, recipe, advancement.build(id.identifier().withPrefix("recipes/")));
    }

    private void validate(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id.identifier());
        }
    }
}
