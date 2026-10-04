package net.mcreator.boh.init;

import net.mcreator.boh.client.model.Modelblood_spill;
import net.mcreator.boh.client.model.Modelstilt_walker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class BohModModels {
   @SubscribeEvent
   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(Modelstilt_walker.LAYER_LOCATION, Modelstilt_walker::createBodyLayer);
      event.registerLayerDefinition(Modelblood_spill.LAYER_LOCATION, Modelblood_spill::createBodyLayer);
   }
}
