package net.mcreator.boh.compat.forge.client.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;

@Cancelable
public class Pre extends Event {
    private final Window window;
    private final GuiGraphics graphics;
    private final float partialTick;

    public Pre() {
        this(null, null, 0.0F);
    }

    public Pre(Window window, GuiGraphics graphics, float partialTick) {
        this.window = window;
        this.graphics = graphics;
        this.partialTick = partialTick;
    }

    public Window getWindow() {
        return this.window;
    }

    public GuiGraphics getGuiGraphics() {
        return this.graphics;
    }

    public float getPartialTick() {
        return this.partialTick;
    }
}
