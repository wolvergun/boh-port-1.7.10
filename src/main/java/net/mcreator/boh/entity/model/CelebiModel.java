package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.CelebiEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class CelebiModel extends GeoModel<CelebiEntity> {
    public ResourceLocation getAnimationResource(CelebiEntity entity) {
        return new ResourceLocation("boh", "animations/celebi.animation.json");
    }

    public ResourceLocation getModelResource(CelebiEntity entity) {
        return new ResourceLocation("boh", "geo/celebi.geo.json");
    }

    public ResourceLocation getTextureResource(CelebiEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
