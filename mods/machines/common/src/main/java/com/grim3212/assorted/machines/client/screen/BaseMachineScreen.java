package com.grim3212.assorted.machines.client.screen;

import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** What the two machine screens share: the recipe book. */
public abstract class BaseMachineScreen<T extends BaseMachineContainer> extends AbstractRecipeBookScreen<T> {

    /** Left of the input slots, which start at x 32; the furnace's spot is over one. */
    private static final int BUTTON_X = 8;
    private static final int BUTTON_Y = 33;

    private final MachineRecipeBookComponent<T> book;

    protected BaseMachineScreen(T menu, Inventory inventory, Component title, Component filterName, ItemLike tabIcon, RecipeBookCategory category) {
        this(menu, inventory, title, new MachineRecipeBookComponent<>(menu, filterName, List.of(new RecipeBookComponent.TabInfo(tabIcon.asItem(), category))));
    }

    private BaseMachineScreen(T menu, Inventory inventory, Component title, MachineRecipeBookComponent<T> book) {
        super(menu, book, inventory, title);
        this.book = book;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + BUTTON_X, this.topPos + BUTTON_Y);
    }

    /** For JEI. */
    public @Nullable ScreenRectangle getRecipeBookArea() {
        return this.book.bookArea();
    }
}
