package net.mcreator.boh.compat.mc.client.gui.components;

import java.util.function.Function;

import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/** 1.20 Button on 1.7.10 GuiButton. */
public class Button extends GuiButton {

    @FunctionalInterface
    public interface OnPress {

        void onPress(Button b);
    }

    public static final class Builder {

        final Component message;
        final OnPress onPress;
        int x, y, width = 150, height = 20;

        Builder(Component message, OnPress onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Builder bounds(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            width = w;
            height = h;
            return this;
        }

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder size(int w, int h) {
            width = w;
            height = h;
            return this;
        }

        public Builder tooltip(Object t) {
            return this;
        }

        public Button build() {
            return new Button(this);
        }

        public Button build(Function<Builder, Button> f) {
            return f.apply(this);
        }
    }

    protected final OnPress onPress;
    private Minecraft mcRef;
    private int lastMouseX, lastMouseY;

    public static Builder builder(Component message, OnPress onPress) {
        return new Builder(message, onPress);
    }

    protected Button(Builder b) {
        super(0, b.x, b.y, b.width, b.height, b.message == null ? "" : b.message.getFormattedText());
        onPress = b.onPress;
    }

    public Button(int x, int y, int w, int h, Component message, OnPress onPress) {
        super(0, x, y, w, h, message == null ? "" : message.getFormattedText());
        this.onPress = onPress;
    }

    public void onPress() {
        if (onPress != null) onPress.onPress(this);
    }

    public void setMessage(Component c) {
        displayString = c.getFormattedText();
    }

    public boolean isHoveredOrFocused() {
        return field_146123_n;
    }

    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.drawButton(mcRef, mouseX, mouseY);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        mcRef = mc;
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        field_146123_n = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        renderWidget(new GuiGraphics(), mouseX, mouseY, 0);
    }
}
