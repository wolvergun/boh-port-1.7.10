package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown9Entity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class Unown9Model extends GeoModel<Unown9Entity> {
    public ResourceLocation getAnimationResource(Unown9Entity entity) {
        return new ResourceLocation("boh", "animations/unown.animation.json");
    }

    public ResourceLocation getModelResource(Unown9Entity entity) {
        return new ResourceLocation("boh", "geo/unown.geo.json");
    }

    public ResourceLocation getTextureResource(Unown9Entity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
