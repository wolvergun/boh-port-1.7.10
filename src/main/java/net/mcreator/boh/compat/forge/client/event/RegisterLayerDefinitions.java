package net.mcreator.boh.compat.forge.client.event;

import cpw.mods.fml.common.eventhandler.Event;
import java.util.function.Supplier;
import net.mcreator.boh.compat.mc.client.model.geom.ModelLayerLocation;
import net.mcreator.boh.compat.mc.client.model.geom.ModelLayers;
import net.mcreator.boh.compat.mc.client.model.geom.builders.LayerDefinition;

public class RegisterLayerDefinitions extends Event {
    public void registerLayerDefinition(ModelLayerLocation loc, Supplier<LayerDefinition> def) {
        ModelLayers.register(loc, def);
    }
}
