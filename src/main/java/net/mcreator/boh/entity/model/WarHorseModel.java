package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.WarHorseEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class WarHorseModel extends GeoModel<WarHorseEntity> {
    public ResourceLocation getAnimationResource(WarHorseEntity entity) {
        return new ResourceLocation("boh", "animations/4horse.animation.json");
    }

    public ResourceLocation getModelResource(WarHorseEntity entity) {
        return new ResourceLocation("boh", "geo/4horse.geo.json");
    }

    public ResourceLocation getTextureResource(WarHorseEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
