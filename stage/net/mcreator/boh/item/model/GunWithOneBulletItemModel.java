package net.mcreator.boh.item.model;

import net.mcreator.boh.item.GunWithOneBulletItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class GunWithOneBulletItemModel extends GeoModel<GunWithOneBulletItem> {

    public ResourceLocation getAnimationResource(GunWithOneBulletItem animatable) {
        return new ResourceLocation("boh", "animations/gun_with_one_bullet.animation.json");
    }

    public ResourceLocation getModelResource(GunWithOneBulletItem animatable) {
        return new ResourceLocation("boh", "geo/gun_with_one_bullet.geo.json");
    }

    public ResourceLocation getTextureResource(GunWithOneBulletItem animatable) {
        return new ResourceLocation("boh", "textures/item/gun_with_one_bullet.png");
    }
}
