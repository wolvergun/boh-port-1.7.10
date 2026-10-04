package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TinkyTankEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class TinkyTankModel extends GeoModel<TinkyTankEntity> {
    public ResourceLocation getAnimationResource(TinkyTankEntity entity) {
        return new ResourceLocation("boh", "animations/tinkytank.animation.json");
    }

    public ResourceLocation getModelResource(TinkyTankEntity entity) {
        return new ResourceLocation("boh", "geo/tinkytank.geo.json");
    }

    public ResourceLocation getTextureResource(TinkyTankEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
