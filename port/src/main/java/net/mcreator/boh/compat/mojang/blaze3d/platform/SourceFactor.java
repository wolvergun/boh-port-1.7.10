package net.mcreator.boh.compat.mojang.blaze3d.platform;

import org.lwjgl.opengl.GL11;

/** 1.20 GlStateManager.SourceFactor. */
public enum SourceFactor {

    CONSTANT_ALPHA(0x8003),
    CONSTANT_COLOR(0x8001),
    DST_ALPHA(GL11.GL_DST_ALPHA),
    DST_COLOR(GL11.GL_DST_COLOR),
    ONE(GL11.GL_ONE),
    ONE_MINUS_CONSTANT_ALPHA(0x8004),
    ONE_MINUS_CONSTANT_COLOR(0x8002),
    ONE_MINUS_DST_ALPHA(GL11.GL_ONE_MINUS_DST_ALPHA),
    ONE_MINUS_DST_COLOR(GL11.GL_ONE_MINUS_DST_COLOR),
    ONE_MINUS_SRC_ALPHA(GL11.GL_ONE_MINUS_SRC_ALPHA),
    ONE_MINUS_SRC_COLOR(GL11.GL_ONE_MINUS_SRC_COLOR),
    SRC_ALPHA(GL11.GL_SRC_ALPHA),
    SRC_ALPHA_SATURATE(GL11.GL_SRC_ALPHA_SATURATE),
    SRC_COLOR(GL11.GL_SRC_COLOR),
    ZERO(GL11.GL_ZERO);

    public final int value;

    SourceFactor(int v) {
        value = v;
    }
}
