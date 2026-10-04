package net.mcreator.boh.compat.mc.client.renderer;

/** 1.20 GameRenderer static shader getters. */
public final class GameRenderer {

    private GameRenderer() {}

    public static ShaderInstance getPositionShader() {
        return ShaderInstance.POSITION;
    }

    public static ShaderInstance getPositionColorShader() {
        return ShaderInstance.POSITION_COLOR;
    }

    public static ShaderInstance getPositionTexShader() {
        return ShaderInstance.POSITION_TEX;
    }

    public static ShaderInstance getPositionTexColorShader() {
        return ShaderInstance.POSITION_TEX_COLOR;
    }

    public static ShaderInstance getPositionColorTexShader() {
        return ShaderInstance.POSITION_COLOR_TEX;
    }

    public static ShaderInstance getPositionColorTexLightmapShader() {
        return ShaderInstance.POSITION_COLOR_TEX;
    }

    public static ShaderInstance getRendertypeCutoutShader() {
        return ShaderInstance.POSITION_TEX_COLOR;
    }
}
