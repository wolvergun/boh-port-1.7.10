package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BaldiEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class BaldiModel extends GeoModel<BaldiEntity> {

    public ResourceLocation getAnimationResource(BaldiEntity entity) {
        return new ResourceLocation("boh", "animations/baldi.animation.json");
    }

    public ResourceLocation getModelResource(BaldiEntity entity) {
        return new ResourceLocation("boh", "geo/baldi.geo.json");
    }

    public ResourceLocation getTextureResource(BaldiEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
