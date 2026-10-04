package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.KrampusEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class KrampusModel extends GeoModel<KrampusEntity> {

    public ResourceLocation getAnimationResource(KrampusEntity entity) {
        return new ResourceLocation("boh", "animations/krampus.animation.json");
    }

    public ResourceLocation getModelResource(KrampusEntity entity) {
        return new ResourceLocation("boh", "geo/krampus.geo.json");
    }

    public ResourceLocation getTextureResource(KrampusEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
