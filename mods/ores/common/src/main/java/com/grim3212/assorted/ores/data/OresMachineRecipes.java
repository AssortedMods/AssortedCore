package com.grim3212.assorted.ores.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.grim3212.assorted.lib.data.CrossLoaderData;
import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.api.OresTags;
import com.grim3212.assorted.ores.common.items.OresItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Assorted Machines grinding mill recipes for the gem ores, written as json since this mod does not build against
 * Machines. Each only loads when Machines is installed.
 */
public class OresMachineRecipes implements DataProvider {

    private static final String MACHINES = "assortedmachines";

    private final PackOutput.PathProvider recipes;
    private final PackOutput.PathProvider advancements;

    public OresMachineRecipes(PackOutput output) {
        this.recipes = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
        this.advancements = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancement");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        CachedOutput bothLoaders = CrossLoaderData.wrap(output);
        List<CompletableFuture<?>> writes = new ArrayList<>();

        // Two gems for one ore, as the grinding mill gives for diamond. Named as they were in Assorted Core.
        grind(bothLoaders, writes, OresTags.Items.ORES_RUBY, OresItems.RUBY.getId());
        grind(bothLoaders, writes, OresTags.Items.ORES_PERIDOT, OresItems.PERIDOT.getId());
        grind(bothLoaders, writes, OresTags.Items.ORES_SAPPHIRE, OresItems.SAPPHIRE.getId());
        grind(bothLoaders, writes, OresTags.Items.ORES_TOPAZ, OresItems.TOPAZ.getId());

        return CompletableFuture.allOf(writes.toArray(CompletableFuture[]::new));
    }

    private void grind(CachedOutput output, List<CompletableFuture<?>> writes, TagKey<Item> ore, Identifier gem) {
        String oreTag = "#" + ore.location();
        writes.add(DataProvider.saveStable(output, grindingMill(oreTag, gem, 2, 0.3F), this.recipes.json(gem)));
        writes.add(DataProvider.saveStable(output, unlockAdvancement(gem, oreTag), this.advancements.json(gem.withPrefix("recipes/"))));
    }

    private static JsonObject grindingMill(String oreTag, Identifier result, int count, float experience) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("ingredient", oreTag);

        JsonObject output = new JsonObject();
        output.addProperty("count", count);
        output.addProperty("id", result.toString());

        JsonObject recipe = new JsonObject();
        recipe.add(CrossLoaderData.NEOFORGE_CONDITIONS, machinesLoaded());
        recipe.addProperty("type", MACHINES + ":grinding_mill");
        recipe.addProperty("cookingtime", 600);
        recipe.addProperty("experience", experience);
        recipe.add("ingredient", ingredient);
        recipe.add("result", output);
        return recipe;
    }

    /** What the Machines recipe builders give a machine recipe: unlocked by holding the ingredient. */
    private static JsonObject unlockAdvancement(Identifier recipe, String ingredient) {
        JsonObject item = new JsonObject();
        item.addProperty("items", ingredient);
        JsonArray items = new JsonArray();
        items.add(item);
        JsonObject hasIngredient = criterion("minecraft:inventory_changed", "items", items);

        JsonObject hasRecipe = criterion("minecraft:recipe_unlocked", "recipe", new JsonPrimitive(recipe.toString()));

        JsonObject criteria = new JsonObject();
        criteria.add("has_ingredient", hasIngredient);
        criteria.add("has_the_recipe", hasRecipe);

        JsonArray anyOf = new JsonArray();
        anyOf.add("has_the_recipe");
        anyOf.add("has_ingredient");
        JsonArray requirements = new JsonArray();
        requirements.add(anyOf);

        JsonArray unlocked = new JsonArray();
        unlocked.add(recipe.toString());
        JsonObject rewards = new JsonObject();
        rewards.add("recipes", unlocked);

        JsonObject advancement = new JsonObject();
        advancement.add(CrossLoaderData.NEOFORGE_CONDITIONS, machinesLoaded());
        advancement.addProperty("parent", "minecraft:recipes/root");
        advancement.add("criteria", criteria);
        advancement.add("requirements", requirements);
        advancement.add("rewards", rewards);
        return advancement;
    }

    private static JsonObject criterion(String trigger, String key, JsonElement value) {
        JsonObject conditions = new JsonObject();
        conditions.add(key, value);
        JsonObject criterion = new JsonObject();
        criterion.add("conditions", conditions);
        criterion.addProperty("trigger", trigger);
        return criterion;
    }

    private static JsonArray machinesLoaded() {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", MACHINES);
        JsonArray conditions = new JsonArray();
        conditions.add(condition);
        return conditions;
    }

    @Override
    public String getName() {
        return "Assorted Machines recipes: " + Constants.MOD_ID;
    }
}
