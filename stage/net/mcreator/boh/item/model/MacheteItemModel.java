package net.mcreator.boh.item.model;

import net.mcreator.boh.item.MacheteItem;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.geo.GeoModel;

public class MacheteItemModel extends GeoModel<MacheteItem> {

    public ResourceLocation getAnimationResource(MacheteItem animatable) {
        return new ResourceLocation("boh", "animations/machete.animation.json");
    }

    public ResourceLocation getModelResource(MacheteItem animatable) {
        return new ResourceLocation("boh", "geo/machete.geo.json");
    }

    public ResourceLocation getTextureResource(MacheteItem animatable) {
        return new ResourceLocation("boh", "textures/item/machete.png");
    }
}
