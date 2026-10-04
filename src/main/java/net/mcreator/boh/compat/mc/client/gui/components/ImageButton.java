package net.mcreator.boh.compat.mc.client.gui.components;

import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ImageButton extends Button {
    private final ResourceLocation texture;
    private final int u;
    private final int v;
    private final int yDiff;
    private final int texW;
    private final int texH;

    public ImageButton(int x, int y, int w, int h, int u, int v, int yDiff, ResourceLocation tex, int texW, int texH, Button.OnPress onPress) {
        super(x, y, w, h, Component.empty(), onPress);
        this.texture = tex;
        this.u = u;
        this.v = v;
        this.yDiff = yDiff;
        this.texW = texW;
        this.texH = texH;
    }

    public ImageButton(int x, int y, int w, int h, int u, int v, int yDiff, ResourceLocation tex, Button.OnPress onPress) {
        this(x, y, w, h, u, v, yDiff, tex, 256, 256, onPress);
    }

    public ImageButton(int x, int y, int w, int h, int u, int v, ResourceLocation tex, Button.OnPress onPress) {
        this(x, y, w, h, u, v, h, tex, 256, 256, onPress);
    }

    @Override
    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        if (this.visible) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3042);
            int vv = this.v + (this.isHoveredOrFocused() ? this.yDiff : 0);
            MClientImpl.blit(
                this.texture,
                this.xPosition,
                this.yPosition,
                this.u,
                vv,
                this.width,
                this.height,
                this.width,
                this.height,
                this.texW,
                this.texH
            );
        }
    }
}
