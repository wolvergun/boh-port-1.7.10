package net.mcreator.boh.compat.mc.client.model.geom;

import net.minecraft.util.ResourceLocation;

/** 1.20 ModelLayerLocation: key of a baked java model layer. */
public final class ModelLayerLocation {

    private final ResourceLocation model;
    private final String layer;

    public ModelLayerLocation(ResourceLocation model, String layer) {
        this.model = model;
        this.layer = layer;
    }

    public ResourceLocation getModel() {
        return model;
    }

    public String getLayer() {
        return layer;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ModelLayerLocation && ((ModelLayerLocation) o).model.equals(model)
            && ((ModelLayerLocation) o).layer.equals(layer);
    }

    @Override
    public int hashCode() {
        return model.hashCode() * 31 + layer.hashCode();
    }

    @Override
    public String toString() {
        return model + "#" + layer;
    }
}
