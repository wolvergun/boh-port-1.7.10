package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.AnglerEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

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
