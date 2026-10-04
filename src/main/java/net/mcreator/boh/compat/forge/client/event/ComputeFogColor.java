package net.mcreator.boh.compat.forge.client.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.mcreator.boh.compat.mc.client.Camera;

public class ComputeFogColor extends Event {
    private final Camera camera;
    private final double partialTick;
    private float red;
    private float green;
    private float blue;

    public ComputeFogColor() {
        this(null, 0.0, 0.0F, 0.0F, 0.0F);
    }

    public ComputeFogColor(Camera camera, double partialTick, float r, float g, float b) {
        this.camera = camera;
        this.partialTick = partialTick;
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public double getPartialTick() {
        return this.partialTick;
    }

    public float getRed() {
        return this.red;
    }

    public float getGreen() {
        return this.green;
    }

    public float getBlue() {
        return this.blue;
    }

    public void setRed(float f) {
        this.red = f;
    }

    public void setGreen(float f) {
        this.green = f;
    }

    public void setBlue(float f) {
        this.blue = f;
    }
}
