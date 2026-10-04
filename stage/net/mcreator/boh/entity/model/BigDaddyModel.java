package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.BigDaddyEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class BigDaddyModel extends GeoModel<BigDaddyEntity> {

    public ResourceLocation getAnimationResource(BigDaddyEntity entity) {
        return new ResourceLocation("boh", "animations/big_daddy.animation.json");
    }

    public ResourceLocation getModelResource(BigDaddyEntity entity) {
        return new ResourceLocation("boh", "geo/big_daddy.geo.json");
    }

    public ResourceLocation getTextureResource(BigDaddyEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
