package net.mcreator.boh.compat.mc.client.renderer;

/** Fixed-function stand-in for a 1.20 core shader: which vertex attributes it consumes. */
public enum ShaderInstance {

    POSITION(false, false),
    POSITION_COLOR(false, true),
    POSITION_TEX(true, false),
    POSITION_TEX_COLOR(true, true),
    POSITION_COLOR_TEX(true, true);

    public final boolean textured, colored;

    ShaderInstance(boolean textured, boolean colored) {
        this.textured = textured;
        this.colored = colored;
    }
}
