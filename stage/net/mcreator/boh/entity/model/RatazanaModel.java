package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.RatazanaEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class RatazanaModel extends GeoModel<RatazanaEntity> {

    public ResourceLocation getAnimationResource(RatazanaEntity entity) {
        return new ResourceLocation("boh", "animations/rat.animation.json");
    }

    public ResourceLocation getModelResource(RatazanaEntity entity) {
        return new ResourceLocation("boh", "geo/rat.geo.json");
    }

    public ResourceLocation getTextureResource(RatazanaEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
