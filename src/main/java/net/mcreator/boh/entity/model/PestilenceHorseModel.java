package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.PestilenceHorseEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class PestilenceHorseModel extends GeoModel<PestilenceHorseEntity> {
    public ResourceLocation getAnimationResource(PestilenceHorseEntity entity) {
        return new ResourceLocation("boh", "animations/4horse.animation.json");
    }

    public ResourceLocation getModelResource(PestilenceHorseEntity entity) {
        return new ResourceLocation("boh", "geo/4horse.geo.json");
    }

    public ResourceLocation getTextureResource(PestilenceHorseEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
