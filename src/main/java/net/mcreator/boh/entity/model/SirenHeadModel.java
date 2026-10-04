package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class SirenHeadModel extends GeoModel<SirenHeadEntity> {
    public ResourceLocation getAnimationResource(SirenHeadEntity entity) {
        return new ResourceLocation("boh", "animations/sirenhead.animation.json");
    }

    public ResourceLocation getModelResource(SirenHeadEntity entity) {
        return new ResourceLocation("boh", "geo/sirenhead.geo.json");
    }

    public ResourceLocation getTextureResource(SirenHeadEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
