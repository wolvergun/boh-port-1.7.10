package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.TakenPillarEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class TakenPillarModel extends GeoModel<TakenPillarEntity> {
    public ResourceLocation getAnimationResource(TakenPillarEntity entity) {
        return new ResourceLocation("boh", "animations/taken_pillar.animation.json");
    }

    public ResourceLocation getModelResource(TakenPillarEntity entity) {
        return new ResourceLocation("boh", "geo/taken_pillar.geo.json");
    }

    public ResourceLocation getTextureResource(TakenPillarEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
