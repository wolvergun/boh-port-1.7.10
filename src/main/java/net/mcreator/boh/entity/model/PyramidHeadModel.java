package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class PyramidHeadModel extends GeoModel<PyramidHeadEntity> {
    public ResourceLocation getAnimationResource(PyramidHeadEntity entity) {
        return new ResourceLocation("boh", "animations/pyramidhead.animation.json");
    }

    public ResourceLocation getModelResource(PyramidHeadEntity entity) {
        return new ResourceLocation("boh", "geo/pyramidhead.geo.json");
    }

    public ResourceLocation getTextureResource(PyramidHeadEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
