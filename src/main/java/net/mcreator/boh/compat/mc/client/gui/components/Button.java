package net.mcreator.boh.compat.mc.client.gui.components;

import java.util.function.Function;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button extends GuiButton {
    protected final Button.OnPress onPress;
    private Minecraft mcRef;
    private int lastMouseX;
    private int lastMouseY;

    public static Button.Builder builder(Component message, Button.OnPress onPress) {
        return new Button.Builder(message, onPress);
    }

    protected Button(Button.Builder b) {
        super(0, b.x, b.y, b.width, b.height, b.message == null ? "" : b.message.getFormattedText());
        this.onPress = b.onPress;
    }

    public Button(int x, int y, int w, int h, Component message, Button.OnPress onPress) {
        super(0, x, y, w, h, message == null ? "" : message.getFormattedText());
        this.onPress = onPress;
    }

    public void onPress() {
        if (this.onPress != null) {
            this.onPress.onPress(this);
        }
    }

    public void setMessage(Component c) {
        this.displayString = c.getFormattedText();
    }

    public boolean isHoveredOrFocused() {
        return this.field_146123_n;
    }

    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.drawButton(this.mcRef, mouseX, mouseY);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.mcRef = mc;
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
        this.field_146123_n = mouseX >= this.xPosition
            && mouseY >= this.yPosition
            && mouseX < this.xPosition + this.width
            && mouseY < this.yPosition + this.height;
        this.renderWidget(new GuiGraphics(), mouseX, mouseY, 0.0F);
    }

    public static final class Builder {
        final Component message;
        final Button.OnPress onPress;
        int x;
        int y;
        int width = 150;
        int height = 20;

        Builder(Component message, Button.OnPress onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Button.Builder bounds(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            return this;
        }

        public Button.Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Button.Builder size(int w, int h) {
            this.width = w;
            this.height = h;
            return this;
        }

        public Button.Builder tooltip(Object t) {
            return this;
        }

        public Button build() {
            return new Button(this);
        }

        public Button build(Function<Button.Builder, Button> f) {
            return f.apply(this);
        }
    }

    @FunctionalInterface
    public interface OnPress {
        void onPress(Button var1);
    }
}
