package com.grim3212.assorted.ores.data;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.api.OresTags;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.ores.common.items.OresItems;
import com.grim3212.assorted.lib.core.conditions.ConditionalRecipeProvider;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.util.LibCommonTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class OresRecipes extends ConditionalRecipeProvider {

    private final HolderGetter<Item> items;

    public OresRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output, Constants.MOD_ID);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void registerConditions() {
        // This mod always fills the ingot tags, but the gears keep the condition they had in Assorted Machines.
        this.onlyWith(OresTags.Items.INGOTS_TIN, "tin_gear");
        this.onlyWith(OresTags.Items.INGOTS_SILVER, "silver_gear");
        this.onlyWith(OresTags.Items.INGOTS_ALUMINUM, "aluminum_gear");
        this.onlyWith(OresTags.Items.INGOTS_NICKEL, "nickel_gear");
        this.onlyWith(OresTags.Items.INGOTS_PLATINUM, "platinum_gear");
        this.onlyWith(OresTags.Items.INGOTS_LEAD, "lead_gear");
    }

    private void onlyWith(TagKey<Item> tag, String recipe) {
        this.addConditions(itemTagExists(tag), prefix(recipe));
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        storageIngotNugget(OresTags.Items.STORAGE_BLOCKS_TIN, OresTags.Items.INGOTS_TIN, OresTags.Items.NUGGETS_TIN, OresBlocks.TIN_BLOCK.get(), OresItems.TIN_INGOT.get(), OresItems.TIN_NUGGET.get());
        ingotNugget(OresTags.Items.INGOTS_COPPER, OresTags.Items.NUGGETS_COPPER, Items.COPPER_INGOT, OresItems.COPPER_NUGGET.get());
        storageIngotNugget(OresTags.Items.STORAGE_BLOCKS_SILVER, OresTags.Items.INGOTS_SILVER, OresTags.Items.NUGGETS_SILVER, OresBlocks.SILVER_BLOCK.get(), OresItems.SILVER_INGOT.get(), OresItems.SILVER_NUGGET.get());
        storageIngotNugget(OresTags.Items.STORAGE_BLOCKS_ALUMINUM, OresTags.Items.INGOTS_ALUMINUM, OresTags.Items.NUGGETS_ALUMINUM, OresBlocks.ALUMINUM_BLOCK.get(), OresItems.ALUMINUM_INGOT.get(), OresItems.ALUMINUM_NUGGET.get());
        storageIngotNugget(OresTags.Items.STORAGE_BLOCKS_NICKEL, OresTags.Items.INGOTS_NICKEL, OresTags.Items.NUGGETS_NICKEL, OresBlocks.NICKEL_BLOCK.get(), OresItems.NICKEL_INGOT.get(), OresItems.NICKEL_NUGGET.get());
        storageIngotNugget(OresTags.Items.STORAGE_BLOCKS_PLATINUM, OresTags.Items.INGOTS_PLATINUM, OresTags.Items.NUGGETS_PLATINUM, OresBlocks.PLATINUM_BLOCK.get(), OresItems.PLATINUM_INGOT.get(), OresItems.PLATINUM_NUGGET.get());
        storageIngotNugget(OresTags.Items.STORAGE_BLOCKS_LEAD, OresTags.Items.INGOTS_LEAD, OresTags.Items.NUGGETS_LEAD, OresBlocks.LEAD_BLOCK.get(), OresItems.LEAD_INGOT.get(), OresItems.LEAD_NUGGET.get());

        storage(OresTags.Items.RAW_STORAGE_BLOCKS_ALUMINUM, OresTags.Items.RAW_MATERIALS_ALUMINUM, OresBlocks.RAW_ALUMINUM_BLOCK.get(), OresItems.RAW_ALUMINUM.get());
        storage(OresTags.Items.RAW_STORAGE_BLOCKS_LEAD, OresTags.Items.RAW_MATERIALS_LEAD, OresBlocks.RAW_LEAD_BLOCK.get(), OresItems.RAW_LEAD.get());
        storage(OresTags.Items.RAW_STORAGE_BLOCKS_NICKEL, OresTags.Items.RAW_MATERIALS_NICKEL, OresBlocks.RAW_NICKEL_BLOCK.get(), OresItems.RAW_NICKEL.get());
        storage(OresTags.Items.RAW_STORAGE_BLOCKS_PLATINUM, OresTags.Items.RAW_MATERIALS_PLATINUM, OresBlocks.RAW_PLATINUM_BLOCK.get(), OresItems.RAW_PLATINUM.get());
        storage(OresTags.Items.RAW_STORAGE_BLOCKS_SILVER, OresTags.Items.RAW_MATERIALS_SILVER, OresBlocks.RAW_SILVER_BLOCK.get(), OresItems.RAW_SILVER.get());
        storage(OresTags.Items.RAW_STORAGE_BLOCKS_TIN, OresTags.Items.RAW_MATERIALS_TIN, OresBlocks.RAW_TIN_BLOCK.get(), OresItems.RAW_TIN.get());

        gemStorage(OresTags.Items.STORAGE_BLOCKS_RUBY, OresTags.Items.GEMS_RUBY, OresBlocks.RUBY_BLOCK.get(), OresItems.RUBY.get());
        gemStorage(OresTags.Items.STORAGE_BLOCKS_PERIDOT, OresTags.Items.GEMS_PERIDOT, OresBlocks.PERIDOT_BLOCK.get(), OresItems.PERIDOT.get());
        gemStorage(OresTags.Items.STORAGE_BLOCKS_SAPPHIRE, OresTags.Items.GEMS_SAPPHIRE, OresBlocks.SAPPHIRE_BLOCK.get(), OresItems.SAPPHIRE.get());
        gemStorage(OresTags.Items.STORAGE_BLOCKS_TOPAZ, OresTags.Items.GEMS_TOPAZ, OresBlocks.TOPAZ_BLOCK.get(), OresItems.TOPAZ.get());

        blastingSmelting(OresTags.Items.ORES_TIN, OresItems.TIN_INGOT.get(), 0.5f);
        blastingSmelting(OresTags.Items.ORES_SILVER, OresItems.SILVER_INGOT.get(), 1.0f);
        blastingSmelting(OresTags.Items.ORES_ALUMINUM, OresItems.ALUMINUM_INGOT.get(), 0.7f);
        blastingSmelting(OresTags.Items.ORES_NICKEL, OresItems.NICKEL_INGOT.get(), 0.7f);
        blastingSmelting(OresTags.Items.ORES_PLATINUM, OresItems.PLATINUM_INGOT.get(), 1.5f);
        blastingSmelting(OresTags.Items.ORES_LEAD, OresItems.LEAD_INGOT.get(), 1.0f);
        blastingSmelting(OresTags.Items.ORES_RUBY, OresItems.RUBY.get(), 1.0f);
        blastingSmelting(OresTags.Items.ORES_PERIDOT, OresItems.PERIDOT.get(), 1.0f);
        blastingSmelting(OresTags.Items.ORES_SAPPHIRE, OresItems.SAPPHIRE.get(), 1.0f);
        blastingSmelting(OresTags.Items.ORES_TOPAZ, OresItems.TOPAZ.get(), 1.0f);

        rawOreBlastingSmelting(OresTags.Items.RAW_MATERIALS_TIN, OresItems.TIN_INGOT.get(), 0.5f);
        rawOreBlastingSmelting(OresTags.Items.RAW_MATERIALS_SILVER, OresItems.SILVER_INGOT.get(), 1.0f);
        rawOreBlastingSmelting(OresTags.Items.RAW_MATERIALS_ALUMINUM, OresItems.ALUMINUM_INGOT.get(), 0.7f);
        rawOreBlastingSmelting(OresTags.Items.RAW_MATERIALS_NICKEL, OresItems.NICKEL_INGOT.get(), 0.7f);
        rawOreBlastingSmelting(OresTags.Items.RAW_MATERIALS_PLATINUM, OresItems.PLATINUM_INGOT.get(), 1.5f);
        rawOreBlastingSmelting(OresTags.Items.RAW_MATERIALS_LEAD, OresItems.LEAD_INGOT.get(), 1.0f);

        rawStorageBlastingSmelting(OresTags.Items.RAW_STORAGE_BLOCKS_TIN, OresBlocks.TIN_BLOCK.get(), 1.0f);
        rawStorageBlastingSmelting(OresTags.Items.RAW_STORAGE_BLOCKS_SILVER, OresBlocks.SILVER_BLOCK.get(), 2.0f);
        rawStorageBlastingSmelting(OresTags.Items.RAW_STORAGE_BLOCKS_ALUMINUM, OresBlocks.ALUMINUM_BLOCK.get(), 1.4f);
        rawStorageBlastingSmelting(OresTags.Items.RAW_STORAGE_BLOCKS_NICKEL, OresBlocks.NICKEL_BLOCK.get(), 1.4f);
        rawStorageBlastingSmelting(OresTags.Items.RAW_STORAGE_BLOCKS_PLATINUM, OresBlocks.PLATINUM_BLOCK.get(), 3.0f);
        rawStorageBlastingSmelting(OresTags.Items.RAW_STORAGE_BLOCKS_LEAD, OresBlocks.LEAD_BLOCK.get(), 1.0f);

        // Assorted Machines grinds the ores into these dusts, see OresMachineRecipes.
        ingotDust(OresTags.Items.DUSTS_TIN, OresItems.TIN_INGOT.get());
        ingotDust(OresTags.Items.DUSTS_SILVER, OresItems.SILVER_INGOT.get());
        ingotDust(OresTags.Items.DUSTS_ALUMINUM, OresItems.ALUMINUM_INGOT.get());
        ingotDust(OresTags.Items.DUSTS_NICKEL, OresItems.NICKEL_INGOT.get());
        ingotDust(OresTags.Items.DUSTS_PLATINUM, OresItems.PLATINUM_INGOT.get());
        ingotDust(OresTags.Items.DUSTS_LEAD, OresItems.LEAD_INGOT.get());

        gear(OresTags.Items.INGOTS_TIN, OresItems.TIN_GEAR.get());
        gear(OresTags.Items.INGOTS_SILVER, OresItems.SILVER_GEAR.get());
        gear(OresTags.Items.INGOTS_ALUMINUM, OresItems.ALUMINUM_GEAR.get());
        gear(OresTags.Items.INGOTS_NICKEL, OresItems.NICKEL_GEAR.get());
        gear(OresTags.Items.INGOTS_PLATINUM, OresItems.PLATINUM_GEAR.get());
        gear(OresTags.Items.INGOTS_LEAD, OresItems.LEAD_GEAR.get());
    }

    private void gear(TagKey<Item> material, ItemLike gear) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, gear).define('M', material).define('S', LibCommonTags.Items.RODS_WOODEN).pattern(" M ").pattern("MSM").pattern(" M ").unlockedBy("has_material", has(material)).save(this.output);
    }

    private void ingotDust(TagKey<Item> dust, ItemLike ingot) {
        SimpleCookingRecipeBuilder.blasting(this.tag(dust), RecipeCategory.MISC, CookingBookCategory.MISC, ingot, 0.1F, 100).unlockedBy("has_dust", has(dust)).save(this.output, key(dust.location().getPath() + "_blasting"));
        SimpleCookingRecipeBuilder.smelting(this.tag(dust), RecipeCategory.MISC, CookingBookCategory.MISC, ingot, 0.1F, 200).unlockedBy("has_dust", has(dust)).save(this.output, key(dust.location().getPath() + "_smelting"));
    }

    private void rawStorageBlastingSmelting(TagKey<Item> rawStorage, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder.blasting(this.tag(rawStorage), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 100).unlockedBy("has_storage", has(rawStorage)).save(this.output, key(getKeyPath(result.asItem()) + "_blasting_raw_storage"));
        SimpleCookingRecipeBuilder.smelting(this.tag(rawStorage), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 200).unlockedBy("has_storage", has(rawStorage)).save(this.output, key(getKeyPath(result.asItem()) + "_smelting_raw_storage"));
    }

    private void rawOreBlastingSmelting(TagKey<Item> ore, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder.blasting(this.tag(ore), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 100).unlockedBy("has_ore", has(ore)).save(this.output, key(getKeyPath(result.asItem()) + "_blasting_raw_ore"));
        SimpleCookingRecipeBuilder.smelting(this.tag(ore), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 200).unlockedBy("has_ore", has(ore)).save(this.output, key(getKeyPath(result.asItem()) + "_smelting_raw_ore"));
    }

    private void blastingSmelting(TagKey<Item> ore, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder.blasting(this.tag(ore), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 100).unlockedBy("has_ore", has(ore)).save(this.output, key(getKeyPath(result.asItem()) + "_blasting"));
        SimpleCookingRecipeBuilder.smelting(this.tag(ore), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 200).unlockedBy("has_ore", has(ore)).save(this.output, key(getKeyPath(result.asItem()) + "_smelting"));
    }

    private void gemStorage(TagKey<Item> storageBlockTag, TagKey<Item> gemTag, ItemLike storageBlock, ItemLike gem) {
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, gem, 9).requires(this.tag(storageBlockTag)).unlockedBy("has_gem", has(storageBlockTag)).save(this.output, key(getKeyPath(gem.asItem()) + "_storage_block"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, storageBlock, 1).requires(this.tag(gemTag), 9).unlockedBy("has_gem", has(gemTag)).save(this.output);
    }

    private void storage(TagKey<Item> storageBlockTag, TagKey<Item> nonStoreTag, ItemLike storageBlock, ItemLike nonStore) {
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, nonStore, 9).requires(this.tag(storageBlockTag)).unlockedBy("has_storage_item", has(nonStoreTag)).save(this.output, key(getKeyPath(nonStore.asItem()) + "_storage_block"));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, storageBlock, 1).requires(this.tag(nonStoreTag), 9).unlockedBy("has_storage_item", has(nonStoreTag)).save(this.output);
    }

    private void storageIngotNugget(TagKey<Item> storageBlockTag, TagKey<Item> ingotTag, TagKey<Item> nuggetTag, ItemLike storageBlock, ItemLike ingot, ItemLike nugget) {
        storage(storageBlockTag, ingotTag, storageBlock, ingot);
        ingotNugget(ingotTag, nuggetTag, ingot, nugget);
    }

    private void ingotNugget(TagKey<Item> ingotTag, TagKey<Item> nuggetTag, ItemLike ingot, ItemLike nugget) {
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, nugget, 9).requires(this.tag(ingotTag)).unlockedBy("has_ingot", has(ingotTag)).save(this.output);
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ingot, 1).requires(this.tag(nuggetTag), 9).unlockedBy("has_ingot", has(ingotTag)).save(this.output, key(getKeyPath(ingot.asItem()) + "_nuggets"));
    }

    public String getKeyPath(Item i) {
        return Services.PLATFORM.getRegistry(Registries.ITEM).getRegistryName(i).getPath();
    }

    /**
     * Recipe providers are not data providers any more - a {@link RecipeProvider.Runner} owns the
     * file writing and builds a fresh provider around the {@link RecipeOutput} it hands out. This is
     * what the loader datagen entry points register.
     */
    public static class Runner extends ConditionalRecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries, Constants.MOD_ID);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new OresRecipes(registries, output);
        }

        @Override
        public String getName() {
            return "Recipes: " + Constants.MOD_ID;
        }
    }

    /**
     * Recipes are addressed by {@code ResourceKey<Recipe<?>>} rather than a raw id now.
     */
    private static ResourceKey<Recipe<?>> key(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, path));
    }
}
