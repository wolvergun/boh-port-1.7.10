package net.mcreator.boh.geo;

public interface GeoBlockEntity extends GeoAnimatable {

    @Override
    default double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
}
