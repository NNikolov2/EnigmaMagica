package com.nikotokiller.enigmamagica.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nikotokiller.enigmamagica.EnigmaMagica;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GemFusionScreen extends AbstractContainerScreen<GemFusionMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(EnigmaMagica.MODID, "textures/gui/gem_fusion_table_gui.png");

    public GemFusionScreen(GemFusionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(pGuiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics pGuiGraphics, int x, int y) {
        if(menu.isCrafting()){
            pGuiGraphics.blit(TEXTURE, x + 127 - menu.getScaledProgress(), y + 36, 196 - menu.getScaledProgress(), 14, 26, 14);
            pGuiGraphics.blit(TEXTURE, x + 50, y + 36, 177, 0, menu.getScaledProgress(), 14);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

}
