package net.mcreator.boh.compat.forge.client.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.mcreator.boh.compat.mojang.blaze3d.shaders.FogShape;

@Cancelable
public class RenderFog extends Event {
    private final FogMode mode;
    private final Camera camera;
    private final double partialTick;
    private float near;
    private float far;
    private FogShape shape = FogShape.SPHERE;

    public RenderFog() {
        this(null, null, 0.0, 0.0F, 0.0F);
    }

    public RenderFog(FogMode mode, Camera camera, double partialTick, float near, float far) {
        this.mode = mode;
        this.camera = camera;
        this.partialTick = partialTick;
        this.near = near;
        this.far = far;
    }

    public FogMode getMode() {
        return this.mode;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public double getPartialTick() {
        return this.partialTick;
    }

    public float getNearPlaneDistance() {
        return this.near;
    }

    public void setNearPlaneDistance(float f) {
        this.near = f;
    }

    public float getFarPlaneDistance() {
        return this.far;
    }

    public void setFarPlaneDistance(float f) {
        this.far = f;
    }

    public FogShape getFogShape() {
        return this.shape;
    }

    public void setFogShape(FogShape s) {
        this.shape = s;
    }

    public void scaleNearPlaneDistance(float f) {
        this.near *= f;
    }

    public void scaleFarPlaneDistance(float f) {
        this.far *= f;
    }
}
