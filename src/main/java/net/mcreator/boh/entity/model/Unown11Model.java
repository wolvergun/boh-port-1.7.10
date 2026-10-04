package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.Unown11Entity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class Unown11Model extends GeoModel<Unown11Entity> {
    public ResourceLocation getAnimationResource(Unown11Entity entity) {
        return new ResourceLocation("boh", "animations/unown.animation.json");
    }

    public ResourceLocation getModelResource(Unown11Entity entity) {
        return new ResourceLocation("boh", "geo/unown.geo.json");
    }

    public ResourceLocation getTextureResource(Unown11Entity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
