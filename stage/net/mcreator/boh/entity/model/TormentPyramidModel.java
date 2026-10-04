package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TormentPyramidEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class TormentPyramidModel extends GeoModel<TormentPyramidEntity> {

    public ResourceLocation getAnimationResource(TormentPyramidEntity entity) {
        return new ResourceLocation("boh", "animations/pyramidheadaoe.animation.json");
    }

    public ResourceLocation getModelResource(TormentPyramidEntity entity) {
        return new ResourceLocation("boh", "geo/pyramidheadaoe.geo.json");
    }

    public ResourceLocation getTextureResource(TormentPyramidEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
