package net.mcreator.boh.item.model;

import net.mcreator.boh.item.MimicryItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class MimicryItemModel extends GeoModel<MimicryItem> {

    public ResourceLocation getAnimationResource(MimicryItem animatable) {
        return new ResourceLocation("boh", "animations/mimicry.animation.json");
    }

    public ResourceLocation getModelResource(MimicryItem animatable) {
        return new ResourceLocation("boh", "geo/mimicry.geo.json");
    }

    public ResourceLocation getTextureResource(MimicryItem animatable) {
        return new ResourceLocation("boh", "textures/item/red_mist_sword.png");
    }
}
