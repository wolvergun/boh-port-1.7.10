package net.mcreator.boh.block.model;

import net.mcreator.boh.block.entity.JarOWispTileEntity;
import net.mcreator.boh.geo.GeoModel;
import net.minecraft.util.ResourceLocation;

public class JarOWispBlockModel extends GeoModel<JarOWispTileEntity> {
    public ResourceLocation getAnimationResource(JarOWispTileEntity animatable) {
        return new ResourceLocation("boh", "animations/jar_o_wisp.animation.json");
    }

    public ResourceLocation getModelResource(JarOWispTileEntity animatable) {
        return new ResourceLocation("boh", "geo/jar_o_wisp.geo.json");
    }

    public ResourceLocation getTextureResource(JarOWispTileEntity animatable) {
        return new ResourceLocation("boh", "textures/block/pot_o_wisp.png");
    }
}
