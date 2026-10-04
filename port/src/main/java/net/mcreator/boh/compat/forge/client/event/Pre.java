package net.mcreator.boh.compat.forge.client.event;

import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 RenderGuiEvent.Pre, posted from RenderGameOverlayEvent.Pre(ALL). */
@Cancelable
public class Pre extends Event {

    public Pre() {
        this(null, null, 0);
    }

    private final Window window;
    private final GuiGraphics graphics;
    private final float partialTick;

    public Pre(Window window, GuiGraphics graphics, float partialTick) {
        this.window = window;
        this.graphics = graphics;
        this.partialTick = partialTick;
    }

    public Window getWindow() {
        return window;
    }

    public GuiGraphics getGuiGraphics() {
        return graphics;
    }

    public float getPartialTick() {
        return partialTick;
    }
}
