package net.mcreator.boh.compat.world;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

/** 1.20 biome ids for 1.7.10 biomes (vanilla by name, the mod's own registered by compat world generation). */
public final class Biomes {

    private static final Map<BiomeGenBase, ResourceLocation> KEYS = new HashMap<>();

    private Biomes() {}

    public static void register(BiomeGenBase b, ResourceLocation id) {
        KEYS.put(b, id);
    }

    public static BiomeGenBase byKey(ResourceLocation id) {
        for (Map.Entry<BiomeGenBase, ResourceLocation> e : KEYS.entrySet()) if (e.getValue().equals(id)) return e.getKey();
        return null;
    }

    public static ResourceLocation keyOf(BiomeGenBase b) {
        ResourceLocation k = KEYS.get(b);
        if (k != null) return k;
        return b.biomeName == null ? null : new ResourceLocation("minecraft", b.biomeName.toLowerCase().replace(' ', '_').replace("+", ""));
    }
}
