package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.mcreator.boh.compat.mc.client.model.geom.ModelLayerLocation;
import net.mcreator.boh.compat.mc.client.model.geom.ModelLayers;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;

public final class Context {
    public static final Context INSTANCE = new Context();

    public ModelPart bakeLayer(ModelLayerLocation loc) {
        return ModelLayers.bake(loc);
    }

    public Object getModelManager() {
        return null;
    }

    public Object getItemRenderer() {
        return null;
    }

    public Object getBlockRenderDispatcher() {
        return null;
    }
}
