package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.DecoyDogEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class DecoyDogModel extends GeoModel<DecoyDogEntity> {
    public ResourceLocation getAnimationResource(DecoyDogEntity entity) {
        return new ResourceLocation("boh", "animations/smiledog.animation.json");
    }

    public ResourceLocation getModelResource(DecoyDogEntity entity) {
        return new ResourceLocation("boh", "geo/smiledog.geo.json");
    }

    public ResourceLocation getTextureResource(DecoyDogEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
