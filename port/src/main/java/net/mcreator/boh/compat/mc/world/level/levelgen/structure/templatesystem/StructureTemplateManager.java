package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.mcreator.boh.BohMod;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/** 1.20 StructureTemplateManager: loads data/&lt;ns&gt;/structures/&lt;name&gt;.nbt from the mod jar. */
public final class StructureTemplateManager {

    public static final StructureTemplateManager INSTANCE = new StructureTemplateManager();
    private final Map<String, StructureTemplate> cache = new HashMap<>();

    private StructureTemplateManager() {}

    public synchronized StructureTemplate getOrCreate(ResourceLocation id) {
        String key = id.toString();
        StructureTemplate t = cache.get(key);
        if (t != null) return t;
        t = new StructureTemplate();
        try (InputStream in = StructureTemplateManager.class
            .getResourceAsStream("/data/" + id.getResourceDomain() + "/structures/" + id.getResourcePath() + ".nbt")) {
            if (in != null) {
                NBTTagCompound tag = CompressedStreamTools.readCompressed(in);
                t.load(tag);
            } else {
                BohMod.LOGGER.warn("Missing structure {}", id);
            }
        } catch (Exception e) {
            BohMod.LOGGER.error("Could not load structure " + id, e);
        }
        cache.put(key, t);
        return t;
    }

    public Optional<StructureTemplate> get(ResourceLocation id) {
        StructureTemplate t = getOrCreate(id);
        return t.isEmpty() ? Optional.empty() : Optional.of(t);
    }
}
