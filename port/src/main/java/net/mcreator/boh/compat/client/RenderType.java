package net.mcreator.boh.compat.client;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * Stand-in for the 1.20 RenderType: a texture plus a fixed-function GL state preset applied by
 * {@link BufferSource} when a batch of that type is drawn.
 */
public final class RenderType {

    public enum Mode {
        SOLID,
        CUTOUT,
        CUTOUT_NO_CULL,
        TRANSLUCENT,
        TRANSLUCENT_CULL,
        EYES,
        ENERGY_SWIRL,
        LINES
    }

    private final ResourceLocation texture;
    private final Mode mode;

    private RenderType(ResourceLocation texture, Mode mode) {
        this.texture = texture;
        this.mode = mode;
    }

    public static RenderType entitySolid(ResourceLocation tex) {
        return new RenderType(tex, Mode.SOLID);
    }

    public static RenderType entityCutout(ResourceLocation tex) {
        return new RenderType(tex, Mode.CUTOUT);
    }

    public static RenderType entityCutoutNoCull(ResourceLocation tex) {
        return new RenderType(tex, Mode.CUTOUT_NO_CULL);
    }

    public static RenderType entityCutoutNoCull(ResourceLocation tex, boolean outline) {
        return new RenderType(tex, Mode.CUTOUT_NO_CULL);
    }

    public static RenderType entityTranslucent(ResourceLocation tex) {
        return new RenderType(tex, Mode.TRANSLUCENT);
    }

    public static RenderType entityTranslucent(ResourceLocation tex, boolean outline) {
        return new RenderType(tex, Mode.TRANSLUCENT);
    }

    public static RenderType entityTranslucentCull(ResourceLocation tex) {
        return new RenderType(tex, Mode.TRANSLUCENT_CULL);
    }

    public static RenderType itemEntityTranslucentCull(ResourceLocation tex) {
        return new RenderType(tex, Mode.TRANSLUCENT_CULL);
    }

    public static RenderType entityTranslucentEmissive(ResourceLocation tex) {
        return new RenderType(tex, Mode.EYES);
    }

    public static RenderType eyes(ResourceLocation tex) {
        return new RenderType(tex, Mode.EYES);
    }

    public static RenderType energySwirl(ResourceLocation tex, float u, float v) {
        return new RenderType(tex, Mode.ENERGY_SWIRL);
    }

    public static RenderType armorCutoutNoCull(ResourceLocation tex) {
        return new RenderType(tex, Mode.CUTOUT_NO_CULL);
    }

    public static RenderType lines() {
        return new RenderType(null, Mode.LINES);
    }

    public ResourceLocation texture() {
        return texture;
    }

    public Mode mode() {
        return mode;
    }

    public boolean fullBright() {
        return mode == Mode.EYES || mode == Mode.ENERGY_SWIRL;
    }

    void setup() {
        if (texture != null) Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        switch (mode) {
            case SOLID:
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
                break;
            case CUTOUT:
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
                break;
            case CUTOUT_NO_CULL:
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
                break;
            case TRANSLUCENT:
            case TRANSLUCENT_CULL:
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
                if (mode == Mode.TRANSLUCENT) GL11.glDisable(GL11.GL_CULL_FACE);
                else GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569f);
                break;
            case EYES:
            case ENERGY_SWIRL:
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glDepthMask(false);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569f);
                break;
            case LINES:
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                break;
        }
    }

    void clear() {
        switch (mode) {
            case EYES:
            case ENERGY_SWIRL:
                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_BLEND);
                break;
            case TRANSLUCENT:
            case TRANSLUCENT_CULL:
                GL11.glDisable(GL11.GL_BLEND);
                break;
            case LINES:
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                break;
            default:
                break;
        }
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RenderType)) return false;
        RenderType r = (RenderType) o;
        return mode == r.mode && Objects.equals(texture, r.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture, mode);
    }
}
