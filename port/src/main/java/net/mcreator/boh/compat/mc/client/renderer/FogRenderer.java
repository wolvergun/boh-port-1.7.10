package net.mcreator.boh.compat.mc.client.renderer;

/** 1.20 FogRenderer: fog state is owned by 1.7.10's EntityRenderer, so these are no-ops. */
public final class FogRenderer {

    private FogRenderer() {}

    public static void levelFogColor() {}

    public static void setupNoFog() {
        org.lwjgl.opengl.GL11.glDisable(org.lwjgl.opengl.GL11.GL_FOG);
    }
}
