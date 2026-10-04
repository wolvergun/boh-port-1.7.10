package net.mcreator.boh.compat.mc.world.entity.decoration;

import net.minecraft.util.ResourceLocation;

/** 1.20 PaintingVariant (size in pixels); placed by the port's painting entity. */
public class PaintingVariant {

    private final int width, height;
    private ResourceLocation id;

    public PaintingVariant(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }
}
