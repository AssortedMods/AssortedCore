package com.grim3212.assorted.machines.client.screen;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeBookCategories;
import com.grim3212.assorted.machines.common.inventory.GrindingMillContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

/**
 * See {@link AlloyForgeScreen} for why {@code renderBg} became {@code extractBackground}.
 */
public class GrindingMillScreen extends BaseMachineScreen<GrindingMillContainer> {

    private static final Identifier GRINDING_MILL_GUI_TEXTURE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/container/grinding_mill.png");
    private static final Component FILTER_NAME = Component.translatable("gui." + Constants.MOD_ID + ".recipebook.toggleRecipes.grindable");

    public GrindingMillScreen(GrindingMillContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, FILTER_NAME, MachinesBlocks.BASIC_GRINDING_MILL.get(), MachinesRecipeBookCategories.GRINDING_MILL.get());
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(graphics, mouseX, mouseY, partialTicks);

        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, GRINDING_MILL_GUI_TEXTURE, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isBurning()) {
            int k = this.menu.getBurnLeftScaled();
            graphics.blit(RenderPipelines.GUI_TEXTURED, GRINDING_MILL_GUI_TEXTURE, i + 81, j + 46 + 12 - k, 176.0F, (float) (12 - k), 14, k + 1, 256, 256);
        }

        int l = this.menu.getCookProgressionScaled();
        graphics.blit(RenderPipelines.GUI_TEXTURED, GRINDING_MILL_GUI_TEXTURE, i + 77, j + 25, 176.0F, 14.0F, l + 1, 20, 256, 256);
    }
}
