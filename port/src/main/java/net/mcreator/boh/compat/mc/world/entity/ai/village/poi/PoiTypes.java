package net.mcreator.boh.compat.mc.world.entity.ai.village.poi;

import java.util.Optional;

/** 1.20 PoiTypes (no POIs in 1.7.10). */
public final class PoiTypes {

    private PoiTypes() {}

    public static <T> Optional<T> forState(Object state) {
        return Optional.empty();
    }
}
