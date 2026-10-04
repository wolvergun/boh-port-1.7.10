package net.mcreator.boh.compat.forge.client.event;

import net.mcreator.boh.compat.mc.client.Camera;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 ViewportEvent.ComputeFogColor, bridged from EntityViewRenderEvent.FogColors. */
public class ComputeFogColor extends Event {

    public ComputeFogColor() {
        this(null, 0, 0, 0, 0);
    }

    private final Camera camera;
    private final double partialTick;
    private float red, green, blue;

    public ComputeFogColor(Camera camera, double partialTick, float r, float g, float b) {
        this.camera = camera;
        this.partialTick = partialTick;
        red = r;
        green = g;
        blue = b;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getPartialTick() {
        return partialTick;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public void setRed(float f) {
        red = f;
    }

    public void setGreen(float f) {
        green = f;
    }

    public void setBlue(float f) {
        blue = f;
    }
}
