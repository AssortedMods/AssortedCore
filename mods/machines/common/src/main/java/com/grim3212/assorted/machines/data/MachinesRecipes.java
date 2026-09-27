package com.grim3212.assorted.machines.data;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.MachinesTags;
import com.grim3212.assorted.machines.api.crafting.MachineIngredient;
import com.grim3212.assorted.machines.api.crafting.builders.AlloyForgeRecipeBuilder;
import com.grim3212.assorted.machines.api.crafting.builders.GrindingMillRecipeBuilder;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.items.MachinesItems;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class MachinesRecipes extends ConditionalRecipeProvider {

    private final HolderGetter<Item> items;

    public MachinesRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output, Constants.MOD_ID);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void registerConditions() {
        // The ore metals and their dusts come from Assorted Ores or another mod, and a recipe naming a tag no pack declares fails to load.
        this.onlyWith(MachinesTags.Items.INGOTS_ALUMINUM, "machine_core");
        this.onlyWith(MachinesTags.Items.INGOTS_PLATINUM, "expert_alloy_forge", "expert_grinding_mill");
        this.onlyWith(MachinesTags.Items.DUSTS_TIN, "bronze_ingot");
        this.onlyWith(MachinesTags.Items.DUSTS_SILVER, "electrum_ingot");
        this.onlyWith(MachinesTags.Items.DUSTS_NICKEL, "invar_ingot");
    }

    private void onlyWith(TagKey<Item> tag, String... recipes) {
        for (String recipe : recipes) {
            this.addConditions(itemTagExists(tag), prefix(recipe));
        }
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        storageIngotNugget(MachinesTags.Items.STORAGE_BLOCKS_BRONZE, MachinesTags.Items.INGOTS_BRONZE, MachinesTags.Items.NUGGETS_BRONZE, MachinesBlocks.BRONZE_BLOCK.get(), MachinesItems.BRONZE_INGOT.get(), MachinesItems.BRONZE_NUGGET.get());
        storageIngotNugget(MachinesTags.Items.STORAGE_BLOCKS_ELECTRUM, MachinesTags.Items.INGOTS_ELECTRUM, MachinesTags.Items.NUGGETS_ELECTRUM, MachinesBlocks.ELECTRUM_BLOCK.get(), MachinesItems.ELECTRUM_INGOT.get(), MachinesItems.ELECTRUM_NUGGET.get());
        storageIngotNugget(MachinesTags.Items.STORAGE_BLOCKS_INVAR, MachinesTags.Items.INGOTS_INVAR, MachinesTags.Items.NUGGETS_INVAR, MachinesBlocks.INVAR_BLOCK.get(), MachinesItems.INVAR_INGOT.get(), MachinesItems.INVAR_NUGGET.get());
        storageIngotNugget(MachinesTags.Items.STORAGE_BLOCKS_STEEL, MachinesTags.Items.INGOTS_STEEL, MachinesTags.Items.NUGGETS_STEEL, MachinesBlocks.STEEL_BLOCK.get(), MachinesItems.STEEL_INGOT.get(), MachinesItems.STEEL_NUGGET.get());

        ingotDust(MachinesTags.Items.DUSTS_COPPER, Items.COPPER_INGOT);
        ingotDust(MachinesTags.Items.DUSTS_BRONZE, MachinesItems.BRONZE_INGOT.get());
        ingotDust(MachinesTags.Items.DUSTS_ELECTRUM, MachinesItems.ELECTRUM_INGOT.get());
        ingotDust(MachinesTags.Items.DUSTS_INVAR, MachinesItems.INVAR_INGOT.get());
        ingotDust(MachinesTags.Items.DUSTS_STEEL, MachinesItems.STEEL_INGOT.get());
        ingotDust(MachinesTags.Items.DUSTS_IRON, Items.IRON_INGOT);
        ingotDust(MachinesTags.Items.DUSTS_GOLD, Items.GOLD_INGOT);

        gear(MachinesTags.Items.INGOTS_COPPER, MachinesItems.COPPER_GEAR.get());
        gear(MachinesTags.Items.INGOTS_BRONZE, MachinesItems.BRONZE_GEAR.get());
        gear(MachinesTags.Items.INGOTS_ELECTRUM, MachinesItems.ELECTRUM_GEAR.get());
        gear(MachinesTags.Items.INGOTS_INVAR, MachinesItems.INVAR_GEAR.get());
        gear(MachinesTags.Items.INGOTS_STEEL, MachinesItems.STEEL_GEAR.get());
        gear(LibCommonTags.Items.INGOTS_IRON, MachinesItems.IRON_GEAR.get());
        gear(LibCommonTags.Items.INGOTS_GOLD, MachinesItems.GOLD_GEAR.get());

        alloy(ItemTags.COALS, 4, MachinesTags.Items.DUSTS_IRON, 1, new ItemStackTemplate(MachinesItems.STEEL_INGOT.get(), 1), 0.5F, 800);
        alloy(MachinesTags.Items.DUSTS_COPPER, 3, MachinesTags.Items.DUSTS_TIN, 1, new ItemStackTemplate(MachinesItems.BRONZE_INGOT.get(), 4), 0.5F);
        alloy(MachinesTags.Items.DUSTS_IRON, 2, MachinesTags.Items.DUSTS_NICKEL, 1, new ItemStackTemplate(MachinesItems.INVAR_INGOT.get(), 3), 0.7F, 500);
        alloy(MachinesTags.Items.DUSTS_SILVER, MachinesTags.Items.DUSTS_GOLD, new ItemStackTemplate(MachinesItems.ELECTRUM_INGOT.get(), 2), 0.7F);

        // Assorted Ores' metals and gems grind in Assorted Ores, which owns their dusts and gems.
        grinding(MachinesTags.Items.ORES_COPPER, new ItemStackTemplate(MachinesItems.COPPER_DUST.get(), 2), 0.2F, 600);

        grinding(LibCommonTags.Items.ORES_GOLD, new ItemStackTemplate(MachinesItems.GOLD_DUST.get(), 2), 0.2F, 600);
        grinding(LibCommonTags.Items.ORES_IRON, new ItemStackTemplate(MachinesItems.IRON_DUST.get(), 2), 0.2F, 600);
        grinding(LibCommonTags.Items.ORES_DIAMOND, new ItemStackTemplate(Items.DIAMOND, 2), 0.3F, 600);
        grinding(LibCommonTags.Items.ORES_EMERALD, new ItemStackTemplate(Items.EMERALD, 2), 0.3F, 600);
        grinding(LibCommonTags.Items.ORES_COAL, new ItemStackTemplate(Items.COAL, 3), 0.1F, 600);
        grinding(LibCommonTags.Items.ORES_REDSTONE, new ItemStackTemplate(Items.REDSTONE, 5), 0.2F, 600);
        grinding(LibCommonTags.Items.ORES_LAPIS, new ItemStackTemplate(Items.LAPIS_LAZULI, 5), 0.2F, 600);
        grinding(LibCommonTags.Items.ORES_QUARTZ, new ItemStackTemplate(Items.QUARTZ, 2), 0.2F, 600);

        grindingDustFromIngot(LibCommonTags.Items.INGOTS_GOLD, new ItemStackTemplate(MachinesItems.GOLD_DUST.get(), 1), 0.0F, 300);
        grindingDustFromIngot(LibCommonTags.Items.INGOTS_IRON, new ItemStackTemplate(MachinesItems.IRON_DUST.get(), 1), 0.0F, 300);

        grindingDustFromIngot(MachinesTags.Items.INGOTS_COPPER, new ItemStackTemplate(MachinesItems.COPPER_DUST.get(), 1), 0.0F, 300);
        grindingDustFromIngot(MachinesTags.Items.INGOTS_BRONZE, new ItemStackTemplate(MachinesItems.BRONZE_DUST.get(), 1), 0.0F, 300);
        grindingDustFromIngot(MachinesTags.Items.INGOTS_ELECTRUM, new ItemStackTemplate(MachinesItems.ELECTRUM_DUST.get(), 1), 0.0F, 300);
        grindingDustFromIngot(MachinesTags.Items.INGOTS_STEEL, new ItemStackTemplate(MachinesItems.STEEL_DUST.get(), 1), 0.0F, 300);
        grindingDustFromIngot(MachinesTags.Items.INGOTS_INVAR, new ItemStackTemplate(MachinesItems.INVAR_DUST.get(), 1), 0.0F, 300);

        grindingDustFromRawOre(MachinesTags.Items.RAW_MATERIALS_GOLD, new ItemStackTemplate(MachinesItems.GOLD_DUST.get(), 2), 0.0F, 300);
        grindingDustFromRawOre(MachinesTags.Items.RAW_MATERIALS_IRON, new ItemStackTemplate(MachinesItems.IRON_DUST.get(), 2), 0.0F, 300);
        grindingDustFromRawOre(MachinesTags.Items.RAW_MATERIALS_COPPER, new ItemStackTemplate(MachinesItems.COPPER_DUST.get(), 2), 0.0F, 300);

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.MACHINE_CORE.get()).define('A', MachinesTags.Items.INGOTS_ALUMINUM).define('C', MachinesTags.Items.GEARS_COPPER).define('I', LibCommonTags.Items.INGOTS_IRON).pattern("IAI").pattern("ACA").pattern("IAI").unlockedBy("has_iron", has(LibCommonTags.Items.INGOTS_IRON)).save(this.output);
        // The aluminum core needs Assorted Ores or another mod, so copper keeps the machines within reach without one.
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.MACHINE_CORE.get()).define('A', MachinesTags.Items.INGOTS_COPPER).define('C', MachinesTags.Items.GEARS_COPPER).define('I', LibCommonTags.Items.INGOTS_IRON).pattern("IAI").pattern("ACA").pattern("IAI").unlockedBy("has_iron", has(LibCommonTags.Items.INGOTS_IRON)).save(this.output, key("machine_core_from_copper"));
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.BASIC_ALLOY_FORGE.get()).define('X', MachinesBlocks.MACHINE_CORE.get()).define('B', Blocks.BLAST_FURNACE).define('I', LibCommonTags.Items.INGOTS_IRON).pattern("III").pattern("BXB").pattern("III").unlockedBy("has_iron", has(LibCommonTags.Items.INGOTS_IRON)).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get()).define('X', MachinesBlocks.BASIC_ALLOY_FORGE.get()).define('S', MachinesTags.Items.INGOTS_STEEL).pattern("SSS").pattern("SXS").pattern("SSS").unlockedBy("has_steel", has(MachinesTags.Items.INGOTS_STEEL)).save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.ADVANCED_ALLOY_FORGE.get()).define('X', MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get()).define('E', MachinesTags.Items.INGOTS_ELECTRUM).define('V', MachinesTags.Items.INGOTS_INVAR).pattern("VEV").pattern("EXE").pattern("VEV").unlockedBy("has_electrum", has(MachinesTags.Items.INGOTS_ELECTRUM)).save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.EXPERT_ALLOY_FORGE.get()).define('X', MachinesBlocks.ADVANCED_ALLOY_FORGE.get()).define('P', MachinesTags.Items.INGOTS_PLATINUM).pattern(" P ").pattern("PXP").pattern(" P ").unlockedBy("has_platinum", has(MachinesTags.Items.INGOTS_PLATINUM)).save(this.output);

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.BASIC_GRINDING_MILL.get()).define('X', MachinesBlocks.MACHINE_CORE.get()).define('F', Blocks.FURNACE).define('I', LibCommonTags.Items.INGOTS_IRON).define('P', Items.IRON_PICKAXE).define('G', MachinesTags.Items.GEARS_IRON).pattern("IPI").pattern("GXG").pattern("IFI").unlockedBy("has_iron_pickaxe", has(Items.IRON_PICKAXE)).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get()).define('X', MachinesBlocks.BASIC_GRINDING_MILL.get()).define('S', MachinesTags.Items.INGOTS_STEEL).pattern("SSS").pattern("SXS").pattern("SSS").unlockedBy("has_steel", has(MachinesTags.Items.INGOTS_STEEL)).save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.ADVANCED_GRINDING_MILL.get()).define('X', MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get()).define('E', MachinesTags.Items.INGOTS_ELECTRUM).define('V', MachinesTags.Items.INGOTS_INVAR).pattern("VEV").pattern("EXE").pattern("VEV").unlockedBy("has_electrum", has(MachinesTags.Items.INGOTS_ELECTRUM)).save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MachinesBlocks.EXPERT_GRINDING_MILL.get()).define('X', MachinesBlocks.ADVANCED_GRINDING_MILL.get()).define('P', MachinesTags.Items.INGOTS_PLATINUM).pattern(" P ").pattern("PXP").pattern(" P ").unlockedBy("has_platinum", has(MachinesTags.Items.INGOTS_PLATINUM)).save(this.output);
    }

    private void alloy(TagKey<Item> ingredient1, int ingredient1Count, TagKey<Item> ingredient2, int ingredient2Count, ItemStackTemplate result, float experience) {
        alloy(ingredient1, ingredient1Count, ingredient2, ingredient2Count, result, experience, 400);
    }

    private void alloy(TagKey<Item> ingredient1, TagKey<Item> ingredient2, ItemStackTemplate result, float experience) {
        alloy(ingredient1, ingredient2, result, experience, 400);
    }

    private void alloy(TagKey<Item> ingredient1, TagKey<Item> ingredient2, ItemStackTemplate result, float experience, int cookTime) {
        alloy(ingredient1, 1, ingredient2, 1, result, experience, cookTime);
    }

    private void alloy(TagKey<Item> ingredient1, int ingredient1Count, TagKey<Item> ingredient2, int ingredient2Count, ItemStackTemplate result, float experience, int cookTime) {
        AlloyForgeRecipeBuilder.recipe(new MachineIngredient(this.tag(ingredient1), ingredient1Count), new MachineIngredient(this.tag(ingredient2), ingredient2Count), result, experience, cookTime).unlockedBy("has_ingredient1", has(ingredient1)).unlockedBy("has_ingredient2", has(ingredient2)).save(this.output);
    }

    private void grindingDustFromRawOre(TagKey<Item> ingredient, ItemStackTemplate result, float experience, int cookTime) {
        grinding(ingredient, result, experience, cookTime, "_from_raw_ore");
    }

    private void grindingDustFromIngot(TagKey<Item> ingredient, ItemStackTemplate result, float experience, int cookTime) {
        grinding(ingredient, result, experience, cookTime, "_from_ingot");
    }

    private void grinding(TagKey<Item> ingredient, ItemStackTemplate result, float experience, int cookTime) {
        grinding(ingredient, result, experience, cookTime, "");
    }

    private void grinding(TagKey<Item> ingredient, ItemStackTemplate result, float experience, int cookTime, String name) {
        GrindingMillRecipeBuilder.recipe(new MachineIngredient(this.tag(ingredient)), result, experience, cookTime).unlockedBy("has_ingredient", has(ingredient)).save(this.output, key(getKeyPath(result.item().value()) + name));
    }

    private void gear(TagKey<Item> material, ItemLike gear) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, gear).define('M', material).define('S', LibCommonTags.Items.RODS_WOODEN).pattern(" M ").pattern("MSM").pattern(" M ").unlockedBy("has_material", has(material)).save(this.output);
    }

    private void ingotDust(TagKey<Item> dust, ItemLike ingot) {
        SimpleCookingRecipeBuilder.blasting(this.tag(dust), RecipeCategory.MISC, CookingBookCategory.MISC, ingot, 0.1F, 100).unlockedBy("has_dust", has(dust)).save(this.output, key(dust.location().getPath() + "_blasting"));
        SimpleCookingRecipeBuilder.smelting(this.tag(dust), RecipeCategory.MISC, CookingBookCategory.MISC, ingot, 0.1F, 200).unlockedBy("has_dust", has(dust)).save(this.output, key(dust.location().getPath() + "_smelting"));
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
            return new MachinesRecipes(registries, output);
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
