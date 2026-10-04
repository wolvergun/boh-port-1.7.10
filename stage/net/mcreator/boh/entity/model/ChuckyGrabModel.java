package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.ChuckyGrabEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class ChuckyGrabModel extends GeoModel<ChuckyGrabEntity> {

    public ResourceLocation getAnimationResource(ChuckyGrabEntity entity) {
        return new ResourceLocation("boh", "animations/chucky_grab.animation.json");
    }

    public ResourceLocation getModelResource(ChuckyGrabEntity entity) {
        return new ResourceLocation("boh", "geo/chucky_grab.geo.json");
    }

    public ResourceLocation getTextureResource(ChuckyGrabEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
