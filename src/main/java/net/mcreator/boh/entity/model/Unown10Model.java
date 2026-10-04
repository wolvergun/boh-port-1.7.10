package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown10Entity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class Unown10Model extends GeoModel<Unown10Entity> {
    public ResourceLocation getAnimationResource(Unown10Entity entity) {
        return new ResourceLocation("boh", "animations/unown.animation.json");
    }

    public ResourceLocation getModelResource(Unown10Entity entity) {
        return new ResourceLocation("boh", "geo/unown.geo.json");
    }

    public ResourceLocation getTextureResource(Unown10Entity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
