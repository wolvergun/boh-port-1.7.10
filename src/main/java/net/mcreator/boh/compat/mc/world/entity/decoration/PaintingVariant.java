package net.mcreator.boh.compat.mc.world.entity.decoration;

import net.minecraft.util.ResourceLocation;

public class PaintingVariant {
    private final int width;
    private final int height;
    private ResourceLocation id;

    public PaintingVariant(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }
}
