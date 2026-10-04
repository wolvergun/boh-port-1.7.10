package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.DeathHorseEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class DeathHorseModel extends GeoModel<DeathHorseEntity> {
    public ResourceLocation getAnimationResource(DeathHorseEntity entity) {
        return new ResourceLocation("boh", "animations/4horse.animation.json");
    }

    public ResourceLocation getModelResource(DeathHorseEntity entity) {
        return new ResourceLocation("boh", "geo/4horse.geo.json");
    }

    public ResourceLocation getTextureResource(DeathHorseEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
