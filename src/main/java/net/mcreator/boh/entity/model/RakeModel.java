package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.RakeEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class RakeModel extends GeoModel<RakeEntity> {
    public ResourceLocation getAnimationResource(RakeEntity entity) {
        return new ResourceLocation("boh", "animations/the_rake.animation.json");
    }

    public ResourceLocation getModelResource(RakeEntity entity) {
        return new ResourceLocation("boh", "geo/the_rake.geo.json");
    }

    public ResourceLocation getTextureResource(RakeEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
