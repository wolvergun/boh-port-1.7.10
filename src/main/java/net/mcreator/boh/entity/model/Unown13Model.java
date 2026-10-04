package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown13Entity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class Unown13Model extends GeoModel<Unown13Entity> {
    public ResourceLocation getAnimationResource(Unown13Entity entity) {
        return new ResourceLocation("boh", "animations/unown.animation.json");
    }

    public ResourceLocation getModelResource(Unown13Entity entity) {
        return new ResourceLocation("boh", "geo/unown.geo.json");
    }

    public ResourceLocation getTextureResource(Unown13Entity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
