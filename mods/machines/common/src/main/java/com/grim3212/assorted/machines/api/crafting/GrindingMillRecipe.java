package com.grim3212.assorted.machines.api.crafting;

import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeBookCategories;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.grim3212.assorted.lib.manual.IManualRecipeProvider;
import com.grim3212.assorted.lib.manual.ManualRecipeView;
import com.grim3212.assorted.lib.manual.ManualSlot;

import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

public class GrindingMillRecipe extends BaseMachineRecipe implements IManualRecipeProvider {

	protected final MachineIngredient ingredient;

	public GrindingMillRecipe(String groupIn, MachineIngredient ingredientIn, ItemStackTemplate resultIn, float experienceIn, int cookTimeIn) {
		super(groupIn, resultIn, experienceIn, cookTimeIn);
		this.ingredient = ingredientIn;
	}

	@Override
	public boolean matches(MachineRecipeInput input, Level worldIn) {
		return this.ingredient.test(input.getItem(0));
	}

	@Override
	public RecipeSerializer<GrindingMillRecipe> getSerializer() {
		return GrindingMillRecipeSerializer.INSTANCE;
	}

	@Override
	public RecipeType<GrindingMillRecipe> getType() {
		return MachinesRecipeTypes.GRINDING_MILL.get();
	}

	@Override
	public boolean validInput(ItemStack stack) {
		return this.ingredient.test(stack);
	}

	public boolean validItem(ItemStack stack) {
		return this.ingredient.getBaseIngredient().test(stack);
	}

	public MachineIngredient getIngredient() {
		return ingredient;
	}

	/** The tool slot is worn down, not consumed, so it is not an ingredient. */
	@Override
	public List<MachineIngredient> machineIngredients() {
		return List.of(this.ingredient);
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return MachinesRecipeBookCategories.GRINDING_MILL.get();
	}

	@Override
	protected SlotDisplay craftingStationDisplay() {
		return new SlotDisplay.Composite(List.of(
				new SlotDisplay.ItemSlotDisplay(MachinesBlocks.BASIC_GRINDING_MILL.get().asItem()),
				new SlotDisplay.ItemSlotDisplay(MachinesBlocks.INTERMEDIATE_GRINDING_MILL.get().asItem()),
				new SlotDisplay.ItemSlotDisplay(MachinesBlocks.ADVANCED_GRINDING_MILL.get().asItem()),
				new SlotDisplay.ItemSlotDisplay(MachinesBlocks.EXPERT_GRINDING_MILL.get().asItem())));
	}

	/** Its own view, not {@link #display()}: the manual keeps the ingredient unresolved for tooltips. */
	@Override
	public ManualRecipeView manualView() {
		return ManualRecipeView.shaped(1, 1, List.of(this.ingredient.manualSlot()),
				ManualSlot.of(new SlotDisplay.ItemStackSlotDisplay(this.getResultTemplate())));
	}
}
