package net.mcreator.boh.compat.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Reads the original models/item/*.json to pick 1.7.10 icons. Only touches jar resources, so it is safe on a
 * dedicated server. Textures are laid out for the 1.7.10 atlases as textures/items/item/..., textures/blocks/block/...
 */
public final class ItemModels {

    private ItemModels() {}

    static JsonObject read(String path) {
        try (InputStream in = ItemModels.class.getResourceAsStream(path)) {
            if (in == null) return null;
            return new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    /** Icon name for an item: its model's layer0 texture ("boh:item/x"), else "boh:item/<id>". */
    public static String iconFor(ResourceLocation id) {
        JsonObject model = read("/assets/" + id.getResourceDomain() + "/models/item/" + id.getResourcePath() + ".json");
        if (model != null && model.has("textures")) {
            JsonObject tex = model.getAsJsonObject("textures");
            String t = tex.has("layer0") ? tex.get("layer0").getAsString() : tex.has("particle") ? tex.get("particle").getAsString() : null;
            if (t != null) return t.contains(":") ? t : "minecraft:" + t;
        }
        return id.getResourceDomain() + ":item/" + id.getResourcePath();
    }

    /** Parent of the item model, e.g. "item/generated" or "boh:displaysettings/x.item" (3D model). */
    public static String parentOf(ResourceLocation id) {
        JsonObject model = read("/assets/" + id.getResourceDomain() + "/models/item/" + id.getResourcePath() + ".json");
        return model != null && model.has("parent") ? model.get("parent").getAsString() : null;
    }
}
