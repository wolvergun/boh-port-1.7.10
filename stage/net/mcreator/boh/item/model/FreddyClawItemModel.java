package net.mcreator.boh.item.model;

import net.mcreator.boh.item.FreddyClawItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class FreddyClawItemModel extends GeoModel<FreddyClawItem> {

    public ResourceLocation getAnimationResource(FreddyClawItem animatable) {
        return new ResourceLocation("boh", "animations/freddy_claw.animation.json");
    }

    public ResourceLocation getModelResource(FreddyClawItem animatable) {
        return new ResourceLocation("boh", "geo/freddy_claw.geo.json");
    }

    public ResourceLocation getTextureResource(FreddyClawItem animatable) {
        return new ResourceLocation("boh", "textures/item/freddy_claw.png");
    }
}
