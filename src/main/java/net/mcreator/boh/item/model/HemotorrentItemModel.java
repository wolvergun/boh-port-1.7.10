package net.mcreator.boh.item.model;

import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.item.HemotorrentItem;
import net.minecraft.util.ResourceLocation;

public class HemotorrentItemModel extends GeoModel<HemotorrentItem> {
    public ResourceLocation getAnimationResource(HemotorrentItem animatable) {
        return new ResourceLocation("boh", "animations/hemotorrent.animation.json");
    }

    public ResourceLocation getModelResource(HemotorrentItem animatable) {
        return new ResourceLocation("boh", "geo/hemotorrent.geo.json");
    }

    public ResourceLocation getTextureResource(HemotorrentItem animatable) {
        return new ResourceLocation("boh", "textures/item/hemotorrent.png");
    }
}
