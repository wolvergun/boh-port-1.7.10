package net.mcreator.boh.entity.model;

import net.mcreator.boh.entity.CartoonCatEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class CartoonCatModel extends GeoModel<CartoonCatEntity> {
    public ResourceLocation getAnimationResource(CartoonCatEntity entity) {
        return new ResourceLocation("boh", "animations/cartoon_cat.animation.json");
    }

    public ResourceLocation getModelResource(CartoonCatEntity entity) {
        return new ResourceLocation("boh", "geo/cartoon_cat.geo.json");
    }

    public ResourceLocation getTextureResource(CartoonCatEntity entity) {
        return new ResourceLocation("boh", "textures/entities/" + entity.getTexture() + ".png");
    }
}
