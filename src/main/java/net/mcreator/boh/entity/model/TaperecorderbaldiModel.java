package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TaperecorderbaldiEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class TaperecorderbaldiModel extends GeoModel<TaperecorderbaldiEntity> {
    public ResourceLocation getAnimationResource(TaperecorderbaldiEntity entity) {
        return new ResourceLocation("boh", "animations/tape_recorder_baldi.animation.json");
    }

    public ResourceLocation getModelResource(TaperecorderbaldiEntity entity) {
        return new ResourceLocation("boh", "geo/tape_recorder_baldi.geo.json");
    }

    public ResourceLocation getTextureResource(TaperecorderbaldiEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
