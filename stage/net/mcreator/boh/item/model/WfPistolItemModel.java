package net.mcreator.boh.item.model;

import net.mcreator.boh.item.WfPistolItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class WfPistolItemModel extends GeoModel<WfPistolItem> {

    public ResourceLocation getAnimationResource(WfPistolItem animatable) {
        return new ResourceLocation("boh", "animations/wf_pistol.animation.json");
    }

    public ResourceLocation getModelResource(WfPistolItem animatable) {
        return new ResourceLocation("boh", "geo/wf_pistol.geo.json");
    }

    public ResourceLocation getTextureResource(WfPistolItem animatable) {
        return new ResourceLocation("boh", "textures/item/pistol_wf.png");
    }
}
