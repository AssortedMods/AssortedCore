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
 * Assorted Machines grinding mill recipes for this mod's metals and gems, written as json since this mod does not
 * build against Machines. Each only loads when Machines is installed.
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

        // Two dusts for an ore or a raw chunk and one for an ingot, as the grinding mill gives for iron. Named as in Assorted Machines.
        dusts(bothLoaders, writes, OresTags.Items.ORES_TIN, OresTags.Items.RAW_MATERIALS_TIN, OresTags.Items.INGOTS_TIN, OresItems.TIN_DUST.getId(), 0.2F);
        dusts(bothLoaders, writes, OresTags.Items.ORES_SILVER, OresTags.Items.RAW_MATERIALS_SILVER, OresTags.Items.INGOTS_SILVER, OresItems.SILVER_DUST.getId(), 0.4F);
        dusts(bothLoaders, writes, OresTags.Items.ORES_ALUMINUM, OresTags.Items.RAW_MATERIALS_ALUMINUM, OresTags.Items.INGOTS_ALUMINUM, OresItems.ALUMINUM_DUST.getId(), 0.2F);
        dusts(bothLoaders, writes, OresTags.Items.ORES_NICKEL, OresTags.Items.RAW_MATERIALS_NICKEL, OresTags.Items.INGOTS_NICKEL, OresItems.NICKEL_DUST.getId(), 0.2F);
        dusts(bothLoaders, writes, OresTags.Items.ORES_PLATINUM, OresTags.Items.RAW_MATERIALS_PLATINUM, OresTags.Items.INGOTS_PLATINUM, OresItems.PLATINUM_DUST.getId(), 0.5F);
        dusts(bothLoaders, writes, OresTags.Items.ORES_LEAD, OresTags.Items.RAW_MATERIALS_LEAD, OresTags.Items.INGOTS_LEAD, OresItems.LEAD_DUST.getId(), 0.2F);

        return CompletableFuture.allOf(writes.toArray(CompletableFuture[]::new));
    }

    private void grind(CachedOutput output, List<CompletableFuture<?>> writes, TagKey<Item> ore, Identifier gem) {
        grind(output, writes, gem, ore, gem, 2, 0.3F, 600);
    }

    private void dusts(CachedOutput output, List<CompletableFuture<?>> writes, TagKey<Item> ore, TagKey<Item> raw, TagKey<Item> ingot, Identifier dust, float experience) {
        grind(output, writes, dust, ore, dust, 2, experience, 600);
        grind(output, writes, dust.withSuffix("_from_raw_ore"), raw, dust, 2, 0.0F, 300);
        grind(output, writes, dust.withSuffix("_from_ingot"), ingot, dust, 1, 0.0F, 300);
    }

    private void grind(CachedOutput output, List<CompletableFuture<?>> writes, Identifier id, TagKey<Item> input, Identifier result, int count, float experience, int cookTime) {
        String inputTag = "#" + input.location();
        writes.add(DataProvider.saveStable(output, grindingMill(inputTag, result, count, experience, cookTime), this.recipes.json(id)));
        writes.add(DataProvider.saveStable(output, unlockAdvancement(id, inputTag), this.advancements.json(id.withPrefix("recipes/"))));
    }

    /** Leaves out a count of one and no experience, as the Machines recipe codec does. */
    private static JsonObject grindingMill(String inputTag, Identifier result, int count, float experience, int cookTime) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("ingredient", inputTag);

        JsonObject output = new JsonObject();
        if (count != 1) {
            output.addProperty("count", count);
        }
        output.addProperty("id", result.toString());

        JsonObject recipe = new JsonObject();
        recipe.add(CrossLoaderData.NEOFORGE_CONDITIONS, machinesLoaded());
        recipe.addProperty("type", MACHINES + ":grinding_mill");
        recipe.addProperty("cookingtime", cookTime);
        if (experience != 0.0F) {
            recipe.addProperty("experience", experience);
        }
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
