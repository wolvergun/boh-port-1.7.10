package net.mcreator.boh.compat.forge.client.event;

import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.mcreator.boh.compat.mojang.blaze3d.shaders.FogShape;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 ViewportEvent.RenderFog; when canceled the bridge applies near/far as linear fog. */
@Cancelable
public class RenderFog extends Event {

    public RenderFog() {
        this(null, null, 0, 0, 0);
    }

    private final FogMode mode;
    private final Camera camera;
    private final double partialTick;
    private float near, far;
    private FogShape shape = FogShape.SPHERE;

    public RenderFog(FogMode mode, Camera camera, double partialTick, float near, float far) {
        this.mode = mode;
        this.camera = camera;
        this.partialTick = partialTick;
        this.near = near;
        this.far = far;
    }

    public FogMode getMode() {
        return mode;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getPartialTick() {
        return partialTick;
    }

    public float getNearPlaneDistance() {
        return near;
    }

    public void setNearPlaneDistance(float f) {
        near = f;
    }

    public float getFarPlaneDistance() {
        return far;
    }

    public void setFarPlaneDistance(float f) {
        far = f;
    }

    public FogShape getFogShape() {
        return shape;
    }

    public void setFogShape(FogShape s) {
        shape = s;
    }

    public void scaleNearPlaneDistance(float f) {
        near *= f;
    }

    public void scaleFarPlaneDistance(float f) {
        far *= f;
    }
}
