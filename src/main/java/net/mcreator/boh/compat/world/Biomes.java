package net.mcreator.boh.compat.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public final class Biomes {
    private static final Map<BiomeGenBase, ResourceLocation> KEYS = new HashMap<>();

    private Biomes() {
    }

    public static void register(BiomeGenBase b, ResourceLocation id) {
        KEYS.put(b, id);
    }

    public static BiomeGenBase byKey(ResourceLocation id) {
        for (Entry<BiomeGenBase, ResourceLocation> e : KEYS.entrySet()) {
            if (e.getValue().equals(id)) {
                return e.getKey();
            }
        }

        return null;
    }

    public static ResourceLocation keyOf(BiomeGenBase b) {
        ResourceLocation k = KEYS.get(b);
        if (k != null) {
            return k;
        } else {
            return b.biomeName == null ? null : new ResourceLocation("minecraft", b.biomeName.toLowerCase().replace(' ', '_').replace("+", ""));
        }
    }
}
