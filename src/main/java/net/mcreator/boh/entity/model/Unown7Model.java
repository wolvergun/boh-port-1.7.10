package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown7Entity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class Unown7Model extends GeoModel<Unown7Entity> {
    public ResourceLocation getAnimationResource(Unown7Entity entity) {
        return new ResourceLocation("boh", "animations/unown.animation.json");
    }

    public ResourceLocation getModelResource(Unown7Entity entity) {
        return new ResourceLocation("boh", "geo/unown.geo.json");
    }

    public ResourceLocation getTextureResource(Unown7Entity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
