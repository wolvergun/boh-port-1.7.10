package net.mcreator.boh.init;

import net.mcreator.boh.client.model.Modelblood_spill;
import net.mcreator.boh.client.model.Modelstilt_walker;
import net.mcreator.boh.compat.forge.client.event.RegisterLayerDefinitions;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class BohModModels {

    @SubscribeEvent
    public void registerLayerDefinitions(RegisterLayerDefinitions event) {
        M.registerLayerDefinition(event, Modelstilt_walker.LAYER_LOCATION, Modelstilt_walker::createBodyLayer);
        M.registerLayerDefinition(event, Modelblood_spill.LAYER_LOCATION, Modelblood_spill::createBodyLayer);
    }
}
