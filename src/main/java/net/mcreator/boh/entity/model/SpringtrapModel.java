package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.SpringtrapEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class SpringtrapModel extends GeoModel<SpringtrapEntity> {
    public ResourceLocation getAnimationResource(SpringtrapEntity entity) {
        return new ResourceLocation("boh", "animations/springtrap.animation.json");
    }

    public ResourceLocation getModelResource(SpringtrapEntity entity) {
        return new ResourceLocation("boh", "geo/springtrap.geo.json");
    }

    public ResourceLocation getTextureResource(SpringtrapEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
