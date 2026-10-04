package net.mcreator.boh.compat.mc.client.gui.components;

import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/** 1.20 ImageButton: textured button with a hover row offset. */
public class ImageButton extends Button {

    private final ResourceLocation texture;
    private final int u, v, yDiff, texW, texH;

    public ImageButton(int x, int y, int w, int h, int u, int v, int yDiff, ResourceLocation tex, int texW, int texH, OnPress onPress) {
        super(x, y, w, h, Component.empty(), onPress);
        this.texture = tex;
        this.u = u;
        this.v = v;
        this.yDiff = yDiff;
        this.texW = texW;
        this.texH = texH;
    }

    public ImageButton(int x, int y, int w, int h, int u, int v, int yDiff, ResourceLocation tex, OnPress onPress) {
        this(x, y, w, h, u, v, yDiff, tex, 256, 256, onPress);
    }

    public ImageButton(int x, int y, int w, int h, int u, int v, ResourceLocation tex, OnPress onPress) {
        this(x, y, w, h, u, v, h, tex, 256, 256, onPress);
    }

    @Override
    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        if (!visible) return;
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_BLEND);
        int vv = v + (isHoveredOrFocused() ? yDiff : 0);
        MClientImpl.blit(texture, xPosition, yPosition, u, vv, width, height, width, height, texW, texH);
    }
}
