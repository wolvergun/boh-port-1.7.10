package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.GoldLostEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class GoldLostModel extends GeoModel<GoldLostEntity> {

    public ResourceLocation getAnimationResource(GoldLostEntity entity) {
        return new ResourceLocation("boh", "animations/gold.animation.json");
    }

    public ResourceLocation getModelResource(GoldLostEntity entity) {
        return new ResourceLocation("boh", "geo/gold.geo.json");
    }

    public ResourceLocation getTextureResource(GoldLostEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
