package net.mcreator.boh.compat.mojang.blaze3d.systems;

import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.client.renderer.ShaderInstance;
import net.mcreator.boh.compat.mojang.blaze3d.platform.DestFactor;
import net.mcreator.boh.compat.mojang.blaze3d.platform.SourceFactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RenderSystem {
    public static ShaderInstance shader = ShaderInstance.POSITION_TEX;
    public static final float[] COLOR = new float[]{1.0F, 1.0F, 1.0F, 1.0F};

    private RenderSystem() {
    }

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
        GL11.glEnable(3042);
    }

    public static void disableBlend() {
        GL11.glDisable(3042);
    }

    public static void defaultBlendFunc() {
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
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
        GL11.glEnable(2929);
    }

    public static void disableDepthTest() {
        GL11.glDisable(2929);
    }

    public static void enableCull() {
        GL11.glEnable(2884);
    }

    public static void disableCull() {
        GL11.glDisable(2884);
    }

    public static void enableTexture() {
        GL11.glEnable(3553);
    }

    public static void disableTexture() {
        GL11.glDisable(3553);
    }

    public static void setShaderFogColor(float r, float g, float b) {
    }

    public static void setShaderFogStart(float f) {
        GL11.glFogf(2915, f);
    }

    public static void setShaderFogEnd(float f) {
        GL11.glFogf(2916, f);
    }
}
