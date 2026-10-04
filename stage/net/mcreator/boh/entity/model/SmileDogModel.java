package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SmileDogEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class SmileDogModel extends GeoModel<SmileDogEntity> {

    public ResourceLocation getAnimationResource(SmileDogEntity entity) {
        return new ResourceLocation("boh", "animations/smiledog.animation.json");
    }

    public ResourceLocation getModelResource(SmileDogEntity entity) {
        return new ResourceLocation("boh", "geo/smiledog.geo.json");
    }

    public ResourceLocation getTextureResource(SmileDogEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
