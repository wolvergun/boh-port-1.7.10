package net.mcreator.boh.compat.mc.client.model.geom;

import net.minecraft.util.ResourceLocation;

public final class ModelLayerLocation {
    private final ResourceLocation model;
    private final String layer;

    public ModelLayerLocation(ResourceLocation model, String layer) {
        this.model = model;
        this.layer = layer;
    }

    public ResourceLocation getModel() {
        return this.model;
    }

    public String getLayer() {
        return this.layer;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ModelLayerLocation && ((ModelLayerLocation)o).model.equals(this.model) && ((ModelLayerLocation)o).layer.equals(this.layer);
    }

    @Override
    public int hashCode() {
        return this.model.hashCode() * 31 + this.layer.hashCode();
    }

    @Override
    public String toString() {
        return this.model + "#" + this.layer;
    }
}
