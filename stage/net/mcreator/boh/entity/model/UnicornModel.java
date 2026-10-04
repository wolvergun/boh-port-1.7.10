package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.UnicornEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class UnicornModel extends GeoModel<UnicornEntity> {

    public ResourceLocation getAnimationResource(UnicornEntity entity) {
        return new ResourceLocation("boh", "animations/4horse.animation.json");
    }

    public ResourceLocation getModelResource(UnicornEntity entity) {
        return new ResourceLocation("boh", "geo/4horse.geo.json");
    }

    public ResourceLocation getTextureResource(UnicornEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
