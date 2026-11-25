package com.grim3212.assorted.machines.client.screen;

import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/** What the two machine screens share. The background, drawn by each machine, comes with {@code super.render}. */
public abstract class BaseMachineScreen<T extends BaseMachineContainer> extends AbstractContainerScreen<T> {

    protected BaseMachineScreen(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
