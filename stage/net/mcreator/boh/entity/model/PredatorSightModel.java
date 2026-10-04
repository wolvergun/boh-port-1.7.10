package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PredatorSightEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class PredatorSightModel extends GeoModel<PredatorSightEntity> {

    public ResourceLocation getAnimationResource(PredatorSightEntity entity) {
        return new ResourceLocation("boh", "animations/predator_sight.animation.json");
    }

    public ResourceLocation getModelResource(PredatorSightEntity entity) {
        return new ResourceLocation("boh", "geo/predator_sight.geo.json");
    }

    public ResourceLocation getTextureResource(PredatorSightEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
