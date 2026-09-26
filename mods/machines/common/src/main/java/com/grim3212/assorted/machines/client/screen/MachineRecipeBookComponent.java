package com.grim3212.assorted.machines.client.screen;

import com.grim3212.assorted.machines.api.crafting.MachineRecipeDisplay;
import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The recipe book beside a machine screen. Ghosts are drawn here rather than through
 * {@link GhostSlots}, whose setters are package private and which draws no count on an input slot.
 */
public class MachineRecipeBookComponent<T extends BaseMachineContainer> extends RecipeBookComponent<T> {

    /** Vanilla's furnace filter sprites: the same "only what this can make" toggle. */
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted"));

    /** {@link GhostSlots}' own colours. */
    private static final int GHOST_BACKDROP = 822018048;
    private static final int GHOST_TINT = 822083583;

    /** The tab column hangs this far off the book's left edge. */
    private static final int TAB_OVERHANG = 30;

    /** Cycle for a ghost slot that accepts several stacks. */
    private static final long GHOST_CYCLE_MILLIS = 1500L;

    private final Component filterName;
    private final List<Ghost> ghosts = new ArrayList<>();

    private int screenWidth;
    private int screenHeight;
    private boolean widthTooNarrow;

    public MachineRecipeBookComponent(T menu, Component filterName, List<RecipeBookComponent.TabInfo> tabInfos) {
        super(menu, tabInfos);
        this.filterName = filterName;
    }

    @Override
    public void init(int width, int height, Minecraft minecraft, boolean widthTooNarrow) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.widthTooNarrow = widthTooNarrow;
        super.init(width, height, minecraft, widthTooNarrow);
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }

    @Override
    protected Component getRecipeFilterName() {
        return this.filterName;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return slot == this.menu.recipeBookResultSlot() || this.menu.recipeBookInputSlots().contains(slot);
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents stackedContents) {
        collection.selectRecipes(stackedContents, display -> display instanceof MachineRecipeDisplay);
    }

    /** Vanilla's {@code ghostSlots} stays empty; {@link #extractGhostRecipe} draws these. */
    @Override
    protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipe, ContextMap context) {
        this.ghosts.clear();
        if (!(recipe instanceof MachineRecipeDisplay machineRecipe)) {
            return;
        }

        this.addGhost(this.menu.recipeBookResultSlot(), recipe.result().resolveForStacks(context), true);

        List<Slot> inputSlots = this.menu.recipeBookInputSlots();
        List<MachineRecipeDisplay.Input> inputs = machineRecipe.inputs();

        for (int i = 0; i < Math.min(inputSlots.size(), inputs.size()); i++) {
            this.addGhost(inputSlots.get(i), inputs.get(i).stacksAtCount(context), false);
        }
    }

    private void addGhost(Slot slot, List<ItemStack> stacks, boolean result) {
        if (!stacks.isEmpty()) {
            this.ghosts.add(new Ghost(slot, stacks, result));
        }
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        if (slot != null && this.isCraftingSlot(slot)) {
            this.ghosts.clear();
        }

        super.slotClicked(slot);
    }

    @Override
    public void extractGhostRecipe(GuiGraphicsExtractor graphics, boolean isResultSlotBig) {
        long index = Util.getMillis() / GHOST_CYCLE_MILLIS;

        for (Ghost ghost : this.ghosts) {
            int x = ghost.slot().x;
            int y = ghost.slot().y;

            if (ghost.result() && isResultSlotBig) {
                graphics.fill(x - 4, y - 4, x + 20, y + 20, GHOST_BACKDROP);
            } else {
                graphics.fill(x, y, x + 16, y + 16, GHOST_BACKDROP);
            }

            ItemStack stack = ghost.stack(index);
            graphics.fakeItem(stack, x, y);
            graphics.fill(x, y, x + 16, y + 16, GHOST_TINT);
            // On inputs too, unlike vanilla: a count above one has to show.
            graphics.itemDecorations(this.minecraft.font, stack, x, y);
        }
    }

    @Override
    public void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, @Nullable Slot hoveredSlot) {
        super.extractTooltip(graphics, mouseX, mouseY, hoveredSlot);

        if (hoveredSlot == null) {
            return;
        }

        for (Ghost ghost : this.ghosts) {
            if (ghost.slot() == hoveredSlot) {
                ItemStack stack = ghost.stack(Util.getMillis() / GHOST_CYCLE_MILLIS);
                graphics.setComponentTooltipForNextFrame(this.minecraft.font, Screen.getTooltipFromItem(this.minecraft, stack), mouseX, mouseY, stack.get(DataComponents.TOOLTIP_STYLE));
                return;
            }
        }
    }

    /** Null while closed. For JEI, which only routes around the vanilla screens' books. */
    public @Nullable ScreenRectangle bookArea() {
        if (!this.isVisible()) {
            return null;
        }

        // The placement RecipeBookComponent gives itself.
        int xOffset = this.widthTooNarrow ? 0 : 86;
        int x = (this.screenWidth - IMAGE_WIDTH) / 2 - xOffset;
        int y = (this.screenHeight - IMAGE_HEIGHT) / 2;

        return new ScreenRectangle(x - TAB_OVERHANG, y, IMAGE_WIDTH + TAB_OVERHANG, IMAGE_HEIGHT);
    }

    private record Ghost(Slot slot, List<ItemStack> stacks, boolean result) {

        ItemStack stack(long index) {
            return this.stacks.get((int) Math.floorMod(index, this.stacks.size()));
        }
    }
}
