package net.mcreator.boh.compat.forge.client.event;

import java.util.function.Function;

import net.mcreator.boh.compat.client.ClientRegistry;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 EntityRenderersEvent.RegisterRenderers. */
public class RegisterRenderers extends Event {

    public void registerEntityRenderer(EntityType<?> type, Function<Context, ?> factory) {
        ClientRegistry.entityRenderer(type, factory);
    }

    public void registerBlockEntityRenderer(BlockEntityType<?> type, Function<Context, ?> factory) {
        ClientRegistry.blockEntityRenderer(type, factory);
    }
}
