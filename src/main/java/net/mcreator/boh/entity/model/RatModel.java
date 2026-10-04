package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.RatEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class RatModel extends GeoModel<RatEntity> {
    public ResourceLocation getAnimationResource(RatEntity entity) {
        return new ResourceLocation("boh", "animations/rat.animation.json");
    }

    public ResourceLocation getModelResource(RatEntity entity) {
        return new ResourceLocation("boh", "geo/rat.geo.json");
    }

    public ResourceLocation getTextureResource(RatEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
