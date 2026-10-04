package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.GasterEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class GasterModel extends GeoModel<GasterEntity> {
    public ResourceLocation getAnimationResource(GasterEntity entity) {
        return new ResourceLocation("boh", "animations/gaster.animation.json");
    }

    public ResourceLocation getModelResource(GasterEntity entity) {
        return new ResourceLocation("boh", "geo/gaster.geo.json");
    }

    public ResourceLocation getTextureResource(GasterEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
