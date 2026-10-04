package net.mcreator.boh.compat.mc.client.gui;

import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;

/** 1.20 GuiGraphics: immediate-mode 2D drawing, implemented by MClientImpl. */
public class GuiGraphics {

    private final PoseStack pose = new PoseStack();

    public PoseStack pose() {
        return pose;
    }

    public int guiWidth() {
        return MClientImpl.guiWidth();
    }

    public int guiHeight() {
        return MClientImpl.guiHeight();
    }

    public void blit(ResourceLocation tex, int x, int y, float u, float v, int w, int h, int texW, int texH) {
        MClientImpl.blit(tex, x, y, u, v, w, h, w, h, texW, texH);
    }

    public void blit(ResourceLocation tex, int x, int y, int w, int h, float u, float v, int uw, int vh, int texW, int texH) {
        MClientImpl.blit(tex, x, y, u, v, w, h, uw, vh, texW, texH);
    }

    public void blit(ResourceLocation tex, int x, int y, int u, int v, int w, int h) {
        MClientImpl.blit(tex, x, y, u, v, w, h, w, h, 256, 256);
    }

    public int drawString(Object font, String text, int x, int y, int color) {
        return MClientImpl.drawString(text, x, y, color, true);
    }

    public int drawString(Object font, String text, int x, int y, int color, boolean shadow) {
        return MClientImpl.drawString(text, x, y, color, shadow);
    }

    public int drawString(Object font, Component text, int x, int y, int color) {
        return MClientImpl.drawString(text.getFormattedText(), x, y, color, true);
    }

    public int drawString(Object font, Component text, int x, int y, int color, boolean shadow) {
        return MClientImpl.drawString(text.getFormattedText(), x, y, color, shadow);
    }

    public void drawCenteredString(Object font, String text, int x, int y, int color) {
        MClientImpl.drawString(text, x - MClientImpl.stringWidth(text) / 2, y, color, true);
    }

    public void drawCenteredString(Object font, Component text, int x, int y, int color) {
        drawCenteredString(font, text.getFormattedText(), x, y, color);
    }

    public void fill(int x1, int y1, int x2, int y2, int color) {
        MClientImpl.fill(x1, y1, x2, y2, color);
    }

    public void renderItem(net.minecraft.item.ItemStack stack, int x, int y) {
        MClientImpl.renderItem(stack, x, y);
    }
}
