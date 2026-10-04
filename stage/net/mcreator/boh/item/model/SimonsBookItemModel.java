package net.mcreator.boh.item.model;

import net.mcreator.boh.item.SimonsBookItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class SimonsBookItemModel extends GeoModel<SimonsBookItem> {

    public ResourceLocation getAnimationResource(SimonsBookItem animatable) {
        return new ResourceLocation("boh", "animations/book_simon.animation.json");
    }

    public ResourceLocation getModelResource(SimonsBookItem animatable) {
        return new ResourceLocation("boh", "geo/book_simon.geo.json");
    }

    public ResourceLocation getTextureResource(SimonsBookItem animatable) {
        return new ResourceLocation("boh", "textures/item/books_simon.png");
    }
}
