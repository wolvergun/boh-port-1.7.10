package net.mcreator.boh.item.model;

import net.mcreator.boh.item.ZacksScytheItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class ZacksScytheItemModel extends GeoModel<ZacksScytheItem> {

    public ResourceLocation getAnimationResource(ZacksScytheItem animatable) {
        return new ResourceLocation("boh", "animations/z_scythe.animation.json");
    }

    public ResourceLocation getModelResource(ZacksScytheItem animatable) {
        return new ResourceLocation("boh", "geo/z_scythe.geo.json");
    }

    public ResourceLocation getTextureResource(ZacksScytheItem animatable) {
        return new ResourceLocation("boh", "textures/item/zack_scythe.png");
    }
}
