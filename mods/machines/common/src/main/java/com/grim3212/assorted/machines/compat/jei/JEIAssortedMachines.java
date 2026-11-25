package com.grim3212.assorted.machines.compat.jei;

import com.grim3212.assorted.lib.crafting.SyncedRecipes;
import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.api.crafting.AlloyForgeRecipe;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipe;
import com.grim3212.assorted.machines.api.machines.MachineUtil;
import com.grim3212.assorted.machines.client.screen.AlloyForgeScreen;
import com.grim3212.assorted.machines.client.screen.GrindingMillScreen;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.grim3212.assorted.machines.common.inventory.AlloyForgeContainer;
import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import com.grim3212.assorted.machines.common.inventory.GrindingMillContainer;
import com.grim3212.assorted.machines.common.inventory.MachinesContainerTypes;
import com.grim3212.assorted.machines.compat.jei.categories.AlloyForgeRecipeCategory;
import com.grim3212.assorted.machines.compat.jei.categories.GrindingMillRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@JeiPlugin
public class JEIAssortedMachines implements IModPlugin {

    public static final RecipeType<AlloyForgeRecipe> ALLOY_FORGE = RecipeType.create(Constants.MOD_ID, "alloy_forge", AlloyForgeRecipe.class);
    public static final RecipeType<GrindingMillRecipe> GRINDING_MILL = RecipeType.create(Constants.MOD_ID, "grinding_mill", GrindingMillRecipe.class);

    private static final ResourceLocation PLUGIN_ID = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jei_plugin");

    static {
        SyncedRecipes.addUpdateListener(JEIAssortedMachines::onRecipesUpdated);
    }

    /**
     * The recipes currently handed to JEI, so a later sync knows what to take back out. Only ever
     * touched from the client thread: plugin loading and packet handling both run there.
     */
    private static List<AlloyForgeRecipe> shownAlloyForge = List.of();
    private static List<GrindingMillRecipe> shownGrindingMill = List.of();

    /** Replaced wholesale on each sync, so identity spots a fresh one. */
    private static Object shownFrom = SyncedRecipes.recipes();

    @Nullable
    private static IJeiRuntime runtime;

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new AlloyForgeRecipeCategory(guiHelper));
        registration.addRecipeCategories(new GrindingMillRecipeCategory(guiHelper));
    }

    /** From {@link SyncedRecipes}, which every loader fills from the recipes the server sent. */
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // The accepted tool list fills the grinding mill's tool slot, and JEI walks every slot as
        // the recipes go in to build its ingredient index, so it has to be built first.
        JEIHelpers.hydrateLists();

        shownFrom = SyncedRecipes.recipes();
        shownAlloyForge = alloyForgeRecipes();
        shownGrindingMill = grindingMillRecipes();

        registration.addRecipes(ALLOY_FORGE, shownAlloyForge);
        registration.addRecipes(GRINDING_MILL, shownGrindingMill);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloyForgeScreen.class, 78, 28, 24, 16, ALLOY_FORGE);
        registration.addRecipeClickArea(GrindingMillScreen.class, 78, 25, 20, 20, GRINDING_MILL);
    }

    /**
     * With no recipe book for the machines in 1.21.1, this is what fills one from a recipe. The
     * grinding mill's category lays out its tool slot second, so both take two slots.
     */
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(AlloyForgeContainer.class, MachinesContainerTypes.ALLOY_FORGE.get(), ALLOY_FORGE,
                0, BaseMachineContainer.TRANSFER_SLOTS, BaseMachineContainer.INVENTORY_START, BaseMachineContainer.INVENTORY_SIZE);
        registration.addRecipeTransferHandler(GrindingMillContainer.class, MachinesContainerTypes.GRINDING_MILL.get(), GRINDING_MILL,
                0, BaseMachineContainer.TRANSFER_SLOTS, BaseMachineContainer.INVENTORY_START, BaseMachineContainer.INVENTORY_SIZE);
    }

    /**
     * Registers the machines as crafting stations. They burn furnace fuel, so they are stations for
     * {@code FUELING} too.
     */
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ItemLike[] alloyForges = new ItemLike[]{MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesBlocks.INTERMEDIATE_ALLOY_FORGE.get(), MachinesBlocks.ADVANCED_ALLOY_FORGE.get(), MachinesBlocks.EXPERT_ALLOY_FORGE.get()};
        ItemLike[] grindingMills = new ItemLike[]{MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get(), MachinesBlocks.ADVANCED_GRINDING_MILL.get(), MachinesBlocks.EXPERT_GRINDING_MILL.get()};

        registration.addRecipeCatalysts(ALLOY_FORGE, alloyForges);
        registration.addRecipeCatalysts(GRINDING_MILL, grindingMills);
        registration.addRecipeCatalysts(RecipeTypes.FUELING, alloyForges);
        registration.addRecipeCatalysts(RecipeTypes.FUELING, grindingMills);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
        // The sync may have landed between registerRecipes and here, in which case what JEI holds
        // is already out of date.
        onRecipesUpdated();
    }

    @Override
    public void onRuntimeUnavailable() {
        runtime = null;
    }

    /**
     * Replaces the listed recipes after a sync: a reload sends them again, and they can arrive
     * after {@link #registerRecipes}. The set is replaced wholesale, so identity spots a new one.
     */
    private static void onRecipesUpdated() {
        IJeiRuntime jeiRuntime = runtime;
        if (jeiRuntime == null) {
            return;
        }

        Object current = SyncedRecipes.recipes();
        if (current == shownFrom) {
            return;
        }

        IRecipeManager recipeManager = jeiRuntime.getRecipeManager();
        recipeManager.hideRecipes(ALLOY_FORGE, shownAlloyForge);
        recipeManager.hideRecipes(GRINDING_MILL, shownGrindingMill);

        shownFrom = current;
        shownAlloyForge = alloyForgeRecipes();
        shownGrindingMill = grindingMillRecipes();

        recipeManager.addRecipes(ALLOY_FORGE, shownAlloyForge);
        recipeManager.addRecipes(GRINDING_MILL, shownGrindingMill);
    }

    private static List<AlloyForgeRecipe> alloyForgeRecipes() {
        return MachineUtil.recipesOfType(null, MachinesRecipeTypes.ALLOY_FORGE.get()).toList();
    }

    private static List<GrindingMillRecipe> grindingMillRecipes() {
        return MachineUtil.recipesOfType(null, MachinesRecipeTypes.GRINDING_MILL.get()).toList();
    }
}
