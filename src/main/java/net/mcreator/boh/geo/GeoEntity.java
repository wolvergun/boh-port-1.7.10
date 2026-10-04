package net.mcreator.boh.geo;

import net.minecraft.entity.Entity;

public interface GeoEntity extends GeoAnimatable {
    @Override
    default double getTick(Object entity) {
        return ((Entity)entity).ticksExisted;
    }
}
