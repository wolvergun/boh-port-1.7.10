package net.mcreator.boh.compat.mc.client.renderer;

import org.lwjgl.opengl.GL11;

public final class FogRenderer {
    private FogRenderer() {
    }

    public static void levelFogColor() {
    }

    public static void setupNoFog() {
        GL11.glDisable(2912);
    }
}
