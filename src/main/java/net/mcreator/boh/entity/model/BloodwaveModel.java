package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BloodwaveEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class BloodwaveModel extends GeoModel<BloodwaveEntity> {
    public ResourceLocation getAnimationResource(BloodwaveEntity entity) {
        return new ResourceLocation("boh", "animations/bloodwave.animation.json");
    }

    public ResourceLocation getModelResource(BloodwaveEntity entity) {
        return new ResourceLocation("boh", "geo/bloodwave.geo.json");
    }

    public ResourceLocation getTextureResource(BloodwaveEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
