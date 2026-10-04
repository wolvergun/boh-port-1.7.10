package net.mcreator.boh.compat.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.minecraft.util.ResourceLocation;

public final class ItemModels {
    private ItemModels() {
    }

    static JsonObject read(String path) {
        try {
            JsonObject var2;
            try (InputStream in = ItemModels.class.getResourceAsStream(path)) {
                if (in == null) {
                    return null;
                }

                var2 = new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            }

            return var2;
        } catch (Exception var6) {
            return null;
        }
    }

    public static String iconFor(ResourceLocation id) {
        JsonObject model = read("/assets/" + id.getResourceDomain() + "/models/item/" + id.getResourcePath() + ".json");
        if (model != null && model.has("textures")) {
            JsonObject tex = model.getAsJsonObject("textures");
            String t = tex.has("layer0") ? tex.get("layer0").getAsString() : (tex.has("particle") ? tex.get("particle").getAsString() : null);
            if (t != null) {
                return t.contains(":") ? t : "minecraft:" + t;
            }
        }

        return id.getResourceDomain() + ":item/" + id.getResourcePath();
    }

    public static String parentOf(ResourceLocation id) {
        JsonObject model = read("/assets/" + id.getResourceDomain() + "/models/item/" + id.getResourcePath() + ".json");
        return model != null && model.has("parent") ? model.get("parent").getAsString() : null;
    }
}
