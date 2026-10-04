package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.AnglerEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class AnglerModel extends GeoModel<AnglerEntity> {
    public ResourceLocation getAnimationResource(AnglerEntity entity) {
        return new ResourceLocation("boh", "animations/lifeform.animation.json");
    }

    public ResourceLocation getModelResource(AnglerEntity entity) {
        return new ResourceLocation("boh", "geo/lifeform.geo.json");
    }

    public ResourceLocation getTextureResource(AnglerEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
