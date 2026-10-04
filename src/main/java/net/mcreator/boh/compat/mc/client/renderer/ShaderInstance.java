package net.mcreator.boh.compat.mc.client.renderer;

public enum ShaderInstance {
    POSITION(false, false),
    POSITION_COLOR(false, true),
    POSITION_TEX(true, false),
    POSITION_TEX_COLOR(true, true),
    POSITION_COLOR_TEX(true, true);

    public final boolean textured;
    public final boolean colored;

    private ShaderInstance(boolean textured, boolean colored) {
        this.textured = textured;
        this.colored = colored;
    }
}
