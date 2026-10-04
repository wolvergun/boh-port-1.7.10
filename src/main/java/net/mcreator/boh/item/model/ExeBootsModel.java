package net.mcreator.boh.item.model;

import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.item.ExeBootsItem;
import net.minecraft.util.ResourceLocation;

public class ExeBootsModel extends GeoModel<ExeBootsItem> {
    public ResourceLocation getAnimationResource(ExeBootsItem object) {
        return new ResourceLocation("boh", "animations/exe_boots.animation.json");
    }

    public ResourceLocation getModelResource(ExeBootsItem object) {
        return new ResourceLocation("boh", "geo/exe_boots.geo.json");
    }

    public ResourceLocation getTextureResource(ExeBootsItem object) {
        return new ResourceLocation("boh", "textures/item/exe.png");
    }
}
