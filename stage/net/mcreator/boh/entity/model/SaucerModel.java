package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SaucerEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class SaucerModel extends GeoModel<SaucerEntity> {

    public ResourceLocation getAnimationResource(SaucerEntity entity) {
        return new ResourceLocation("boh", "animations/saucer.animation.json");
    }

    public ResourceLocation getModelResource(SaucerEntity entity) {
        return new ResourceLocation("boh", "geo/saucer.geo.json");
    }

    public ResourceLocation getTextureResource(SaucerEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
