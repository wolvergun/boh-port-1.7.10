package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.JasonMaskEntity;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class JasonMaskModel extends GeoModel<JasonMaskEntity> {

    public ResourceLocation getAnimationResource(JasonMaskEntity entity) {
        return new ResourceLocation("boh", "animations/jason_voorhees_mask.animation.json");
    }

    public ResourceLocation getModelResource(JasonMaskEntity entity) {
        return new ResourceLocation("boh", "geo/jason_voorhees_mask.geo.json");
    }

    public ResourceLocation getTextureResource(JasonMaskEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
