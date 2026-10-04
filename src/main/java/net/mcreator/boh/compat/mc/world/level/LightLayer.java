package net.mcreator.boh.compat.mc.world.level;

import net.minecraft.world.EnumSkyBlock;

public enum LightLayer {
    SKY(EnumSkyBlock.Sky),
    BLOCK(EnumSkyBlock.Block);

    private final EnumSkyBlock legacy;

    private LightLayer(EnumSkyBlock legacy) {
        this.legacy = legacy;
    }

    public EnumSkyBlock toVanilla() {
        return this.legacy;
    }
}
