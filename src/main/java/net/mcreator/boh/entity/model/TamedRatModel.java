package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TamedRatEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class TamedRatModel extends GeoModel<TamedRatEntity> {
    public ResourceLocation getAnimationResource(TamedRatEntity entity) {
        return new ResourceLocation("boh", "animations/rat.animation.json");
    }

    public ResourceLocation getModelResource(TamedRatEntity entity) {
        return new ResourceLocation("boh", "geo/rat.geo.json");
    }

    public ResourceLocation getTextureResource(TamedRatEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
