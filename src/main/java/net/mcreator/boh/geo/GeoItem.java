package net.mcreator.boh.geo;

public interface GeoItem extends GeoAnimatable {
    @Override
    default double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }
}
