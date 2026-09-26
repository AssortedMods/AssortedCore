package com.grim3212.assorted.machines.client.screen;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeBookCategories;
import com.grim3212.assorted.machines.common.inventory.AlloyForgeContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

/**
 * The alloy forge screen. It records its background into a {@link GuiGraphicsExtractor}; the base
 * screen draws the rest, including the recipe book.
 */
public class AlloyForgeScreen extends BaseMachineScreen<AlloyForgeContainer> {

    private static final Identifier ALLOY_FORGE_GUI_TEXTURE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/container/alloy_forge.png");
    private static final Component FILTER_NAME = Component.translatable("gui." + Constants.MOD_ID + ".recipebook.toggleRecipes.alloyable");

    public AlloyForgeScreen(AlloyForgeContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, FILTER_NAME, MachinesBlocks.BASIC_ALLOY_FORGE.get(), MachinesRecipeBookCategories.ALLOY_FORGE.get());
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(graphics, mouseX, mouseY, partialTicks);

        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, ALLOY_FORGE_GUI_TEXTURE, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isBurning()) {
            int k = this.menu.getBurnLeftScaled();
            graphics.blit(RenderPipelines.GUI_TEXTURED, ALLOY_FORGE_GUI_TEXTURE, i + 81, j + 46 + 12 - k, 176.0F, (float) (12 - k), 14, k + 1, 256, 256);
        }

        int l = this.menu.getCookProgressionScaled();
        graphics.blit(RenderPipelines.GUI_TEXTURED, ALLOY_FORGE_GUI_TEXTURE, i + 77, j + 28, 176.0F, 14.0F, l + 1, 16, 256, 256);
    }
}
