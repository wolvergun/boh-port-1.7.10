package net.mcreator.boh.compat.mc.world.entity.ai.village.poi;

import java.util.Optional;

public final class PoiTypes {
    private PoiTypes() {
    }

    public static <T> Optional<T> forState(Object state) {
        return Optional.empty();
    }
}
