package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.FamineHorseEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class FamineHorseModel extends GeoModel<FamineHorseEntity> {

    public ResourceLocation getAnimationResource(FamineHorseEntity entity) {
        return new ResourceLocation("boh", "animations/4horse.animation.json");
    }

    public ResourceLocation getModelResource(FamineHorseEntity entity) {
        return new ResourceLocation("boh", "geo/4horse.geo.json");
    }

    public ResourceLocation getTextureResource(FamineHorseEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
