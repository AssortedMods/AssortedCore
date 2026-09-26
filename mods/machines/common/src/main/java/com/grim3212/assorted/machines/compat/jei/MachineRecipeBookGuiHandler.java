package com.grim3212.assorted.machines.compat.jei;

import com.grim3212.assorted.machines.client.screen.BaseMachineScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

/** Keeps JEI's overlay off an open recipe book. JEI registers its own only for the vanilla screens. */
public class MachineRecipeBookGuiHandler implements IGuiContainerHandler<BaseMachineScreen<?>> {

    @Override
    public List<Rect2i> getGuiExtraAreas(BaseMachineScreen<?> screen) {
        ScreenRectangle book = screen.getRecipeBookArea();
        return book == null ? List.of() : List.of(new Rect2i(book.left(), book.top(), book.width(), book.height()));
    }
}
