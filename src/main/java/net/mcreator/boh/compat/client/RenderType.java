package net.mcreator.boh.compat.client;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RenderType {
    private final ResourceLocation texture;
    private final RenderType.Mode mode;

    private RenderType(ResourceLocation texture, RenderType.Mode mode) {
        this.texture = texture;
        this.mode = mode;
    }

    public static RenderType entitySolid(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.SOLID);
    }

    public static RenderType entityCutout(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.CUTOUT);
    }

    public static RenderType entityCutoutNoCull(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.CUTOUT_NO_CULL);
    }

    public static RenderType entityCutoutNoCull(ResourceLocation tex, boolean outline) {
        return new RenderType(tex, RenderType.Mode.CUTOUT_NO_CULL);
    }

    public static RenderType entityTranslucent(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.TRANSLUCENT);
    }

    public static RenderType entityTranslucent(ResourceLocation tex, boolean outline) {
        return new RenderType(tex, RenderType.Mode.TRANSLUCENT);
    }

    public static RenderType entityTranslucentCull(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.TRANSLUCENT_CULL);
    }

    public static RenderType itemEntityTranslucentCull(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.TRANSLUCENT_CULL);
    }

    public static RenderType entityTranslucentEmissive(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.EYES);
    }

    public static RenderType eyes(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.EYES);
    }

    public static RenderType energySwirl(ResourceLocation tex, float u, float v) {
        return new RenderType(tex, RenderType.Mode.ENERGY_SWIRL);
    }

    public static RenderType armorCutoutNoCull(ResourceLocation tex) {
        return new RenderType(tex, RenderType.Mode.CUTOUT_NO_CULL);
    }

    public static RenderType lines() {
        return new RenderType(null, RenderType.Mode.LINES);
    }

    public ResourceLocation texture() {
        return this.texture;
    }

    public RenderType.Mode mode() {
        return this.mode;
    }

    public boolean fullBright() {
        return this.mode == RenderType.Mode.EYES || this.mode == RenderType.Mode.ENERGY_SWIRL;
    }

    void setup() {
        if (this.texture != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
        }

        switch (this.mode) {
            case SOLID:
                GL11.glDisable(3042);
                GL11.glEnable(2884);
                GL11.glEnable(3008);
                GL11.glAlphaFunc(516, 0.1F);
                break;
            case CUTOUT:
                GL11.glDisable(3042);
                GL11.glEnable(2884);
                GL11.glEnable(3008);
                GL11.glAlphaFunc(516, 0.1F);
                break;
            case CUTOUT_NO_CULL:
                GL11.glDisable(3042);
                GL11.glDisable(2884);
                GL11.glEnable(3008);
                GL11.glAlphaFunc(516, 0.1F);
                break;
            case TRANSLUCENT:
            case TRANSLUCENT_CULL:
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 771);
                if (this.mode == RenderType.Mode.TRANSLUCENT) {
                    GL11.glDisable(2884);
                } else {
                    GL11.glEnable(2884);
                }

                GL11.glEnable(3008);
                GL11.glAlphaFunc(516, 0.003921569F);
                break;
            case EYES:
            case ENERGY_SWIRL:
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                GL11.glDisable(2884);
                GL11.glDepthMask(false);
                GL11.glDepthFunc(515);
                GL11.glEnable(3008);
                GL11.glAlphaFunc(516, 0.003921569F);
                break;
            case LINES:
                GL11.glDisable(3553);
        }
    }

    void clear() {
        switch (this.mode) {
            case TRANSLUCENT:
            case TRANSLUCENT_CULL:
                GL11.glDisable(3042);
                break;
            case EYES:
            case ENERGY_SWIRL:
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                break;
            case LINES:
                GL11.glEnable(3553);
        }

        GL11.glEnable(2884);
        GL11.glAlphaFunc(516, 0.1F);
    }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof RenderType r) ? false : this.mode == r.mode && Objects.equals(this.texture, r.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.texture, this.mode);
    }

    public static enum Mode {
        SOLID,
        CUTOUT,
        CUTOUT_NO_CULL,
        TRANSLUCENT,
        TRANSLUCENT_CULL,
        EYES,
        ENERGY_SWIRL,
        LINES;
    }
}
