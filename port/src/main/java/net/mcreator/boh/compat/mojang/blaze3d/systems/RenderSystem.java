package net.mcreator.boh.compat.mojang.blaze3d.systems;

import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.client.renderer.ShaderInstance;
import net.mcreator.boh.compat.mojang.blaze3d.platform.DestFactor;
import net.mcreator.boh.compat.mojang.blaze3d.platform.SourceFactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/** 1.20 RenderSystem over 1.7.10 fixed-function GL. */
public final class RenderSystem {

    public static ShaderInstance shader = ShaderInstance.POSITION_TEX;
    public static final float[] COLOR = { 1, 1, 1, 1 };

    private RenderSystem() {}

    public static void setShader(Supplier<ShaderInstance> s) {
        ShaderInstance i = s == null ? null : s.get();
        shader = i == null ? ShaderInstance.POSITION_TEX : i;
    }

    public static void setShaderColor(float r, float g, float b, float a) {
        COLOR[0] = r;
        COLOR[1] = g;
        COLOR[2] = b;
        COLOR[3] = a;
        GL11.glColor4f(r, g, b, a);
    }

    public static float[] getShaderColor() {
        return COLOR;
    }

    public static void setShaderTexture(int unit, ResourceLocation tex) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
    }

    public static void enableBlend() {
        GL11.glEnable(GL11.GL_BLEND);
    }

    public static void disableBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void defaultBlendFunc() {
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
    }

    public static void blendFunc(SourceFactor s, DestFactor d) {
        GL11.glBlendFunc(s.value, d.value);
    }

    public static void blendFuncSeparate(SourceFactor s, DestFactor d, SourceFactor sa, DestFactor da) {
        OpenGlHelper.glBlendFunc(s.value, d.value, sa.value, da.value);
    }

    public static void depthMask(boolean b) {
        GL11.glDepthMask(b);
    }

    public static void enableDepthTest() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void disableDepthTest() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void enableCull() {
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    public static void disableCull() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public static void enableTexture() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void disableTexture() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void setShaderFogColor(float r, float g, float b) {}

    public static void setShaderFogStart(float f) {
        GL11.glFogf(GL11.GL_FOG_START, f);
    }

    public static void setShaderFogEnd(float f) {
        GL11.glFogf(GL11.GL_FOG_END, f);
    }
}
