package net.mcreator.boh.compat.mc.client.model.geom;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.mcreator.boh.compat.mc.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.ResourceLocation;

/** Vanilla layer keys used by the mod plus the registry of mod layer definitions. */
public final class ModelLayers {

    public static final ModelLayerLocation PLAYER = new ModelLayerLocation(new ResourceLocation("minecraft", "player"), "main");
    public static final ModelLayerLocation PLAYER_INNER_ARMOR = new ModelLayerLocation(new ResourceLocation("minecraft", "player"), "inner_armor");
    public static final ModelLayerLocation PLAYER_OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation("minecraft", "player"), "outer_armor");

    private static final Map<ModelLayerLocation, Supplier<LayerDefinition>> DEFINITIONS = new HashMap<>();
    private static final Map<ModelLayerLocation, ModelPart> BAKED = new HashMap<>();

    private ModelLayers() {}

    public static void register(ModelLayerLocation loc, Supplier<LayerDefinition> def) {
        DEFINITIONS.put(loc, def);
        BAKED.remove(loc);
    }

    public static ModelPart bake(ModelLayerLocation loc) {
        ModelPart p = BAKED.get(loc);
        if (p != null) return p;
        Supplier<LayerDefinition> def = DEFINITIONS.get(loc);
        p = def == null ? new ModelPart(new java.util.ArrayList<>(), new HashMap<>()) : def.get().bakeRoot();
        BAKED.put(loc, p);
        return p;
    }
}
