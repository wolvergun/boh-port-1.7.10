package net.mcreator.boh.block.listener;

import net.mcreator.boh.block.renderer.JarOWispTileRenderer;
import net.mcreator.boh.block.renderer.PokerNightTileRenderer;
import net.mcreator.boh.init.BohModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "boh", bus = Bus.MOD)
public class ClientListener {
   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer((BlockEntityType)BohModBlockEntities.JAR_O_WISP.get(), context -> new JarOWispTileRenderer());
      event.registerBlockEntityRenderer((BlockEntityType)BohModBlockEntities.POKER_NIGHT.get(), context -> new PokerNightTileRenderer());
   }
}
