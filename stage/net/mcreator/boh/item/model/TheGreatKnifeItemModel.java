package net.mcreator.boh.item.model;

import net.mcreator.boh.item.TheGreatKnifeItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class TheGreatKnifeItemModel extends GeoModel<TheGreatKnifeItem> {

    public ResourceLocation getAnimationResource(TheGreatKnifeItem animatable) {
        return new ResourceLocation("boh", "animations/the_great_knife.animation.json");
    }

    public ResourceLocation getModelResource(TheGreatKnifeItem animatable) {
        return new ResourceLocation("boh", "geo/the_great_knife.geo.json");
    }

    public ResourceLocation getTextureResource(TheGreatKnifeItem animatable) {
        return new ResourceLocation("boh", "textures/item/great_knife.png");
    }
}
