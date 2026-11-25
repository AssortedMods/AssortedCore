package com.grim3212.assorted.machines.client.screen;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.inventory.GrindingMillContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GrindingMillScreen extends BaseMachineScreen<GrindingMillContainer> {

    private static final ResourceLocation GRINDING_MILL_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/container/grinding_mill.png");

    public GrindingMillScreen(GrindingMillContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(GRINDING_MILL_GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isBurning()) {
            int k = this.menu.getBurnLeftScaled();
            graphics.blit(GRINDING_MILL_GUI_TEXTURE, i + 81, j + 46 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.menu.getCookProgressionScaled();
        graphics.blit(GRINDING_MILL_GUI_TEXTURE, i + 77, j + 25, 176, 14, l + 1, 20);
    }
}
