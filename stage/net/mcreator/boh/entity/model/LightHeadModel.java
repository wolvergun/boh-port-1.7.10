package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.LightHeadEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class LightHeadModel extends GeoModel<LightHeadEntity> {

    public ResourceLocation getAnimationResource(LightHeadEntity entity) {
        return new ResourceLocation("boh", "animations/sirenhead.animation.json");
    }

    public ResourceLocation getModelResource(LightHeadEntity entity) {
        return new ResourceLocation("boh", "geo/sirenhead.geo.json");
    }

    public ResourceLocation getTextureResource(LightHeadEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
