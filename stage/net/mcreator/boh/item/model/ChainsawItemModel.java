package net.mcreator.boh.item.model;

import net.mcreator.boh.item.ChainsawItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class ChainsawItemModel extends GeoModel<ChainsawItem> {

    public ResourceLocation getAnimationResource(ChainsawItem animatable) {
        return new ResourceLocation("boh", "animations/chainsaw.animation.json");
    }

    public ResourceLocation getModelResource(ChainsawItem animatable) {
        return new ResourceLocation("boh", "geo/chainsaw.geo.json");
    }

    public ResourceLocation getTextureResource(ChainsawItem animatable) {
        return new ResourceLocation("boh", "textures/item/chainsaw.png");
    }
}
