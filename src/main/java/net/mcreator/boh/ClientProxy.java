package net.mcreator.boh;

import com.google.common.collect.ImmutableMap;
import cpw.mods.fml.client.registry.RenderingRegistry;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Function;
import net.mcreator.boh.compat.block.BlockModels;
import net.mcreator.boh.compat.client.ClientDebugCommand;
import net.mcreator.boh.compat.client.ClientEventBridge;
import net.mcreator.boh.compat.client.ClientRegistry;
import net.mcreator.boh.compat.client.ElementBlockRenderer;
import net.mcreator.boh.compat.client.ItemExtensions;
import net.mcreator.boh.compat.client.ItemRendererBridge;
import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.entity.BohArrow;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.mcreator.boh.compat.forge.client.event.RegisterLayerDefinitions;
import net.mcreator.boh.compat.forge.client.event.RegisterParticleProvidersEvent;
import net.mcreator.boh.compat.forge.client.event.RegisterRenderers;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mcreator.boh.compat.item.BohBlockItem;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.mc.client.renderer.entity.ThrownItemRenderer;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public void preInit() {
        ClientEventBridge.install();
        ClientDebugCommand.register();
        ElementBlockRenderer.register();
        MinecraftForge.EVENT_BUS.register(ParticleEngine.EVENTS);
        BohMod.MOD_BUS.post(new RegisterParticleProvidersEvent());
        BohMod.MOD_BUS.post(new RegisterLayerDefinitions());
    }

    @Override
    public void init() {
        BlockModels.assignRenderIds();
        RenderingRegistry.registerEntityRenderingHandler(BohAreaEffectCloud.class, new Render() {
            {
                Objects.requireNonNull(ClientProxy.this);
            }

            public void doRender(Entity e, double x, double y, double z, float yaw, float pt) {
            }

            protected ResourceLocation getEntityTexture(Entity e) {
                return null;
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(BohArrow.class, new ThrownItemRenderer(Context.INSTANCE));
        BohMod.MOD_BUS.post(new RegisterRenderers());

        for (Entry<EntityType<?>, Function<Context, ?>> e : ClientRegistry.ENTITY.entrySet()) {
            try {
                Object r = e.getValue().apply(Context.INSTANCE);
                if (r instanceof Render && e.getKey().getEntityClass() != null) {
                    RenderingRegistry.registerEntityRenderingHandler(e.getKey().getEntityClass(), (Render)r);
                }
            } catch (Throwable var6) {
                BohMod.LOGGER.error("Could not create renderer for " + e.getKey().getId(), var6);
            }
        }

        for (Entry<BlockEntityType<?>, Function<Context, ?>> e : ClientRegistry.BLOCK_ENTITY.entrySet()) {
            try {
                Object r = e.getValue().apply(Context.INSTANCE);
                if (r instanceof TileEntitySpecialRenderer) {
                    cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(e.getKey().tileClass(), (TileEntitySpecialRenderer)r);
                }
            } catch (Throwable var5) {
                BohMod.LOGGER.error("Could not create block entity renderer for " + e.getKey().getId(), var5);
            }
        }

        for (Object o : Item.itemRegistry) {
            if (o instanceof BohItem || o instanceof BohBlockItem) {
                Item item = (Item)o;
                BlockEntityWithoutLevelRenderer r = ItemExtensions.of(item).getCustomRenderer();
                if (r != null) {
                    MinecraftForgeClient.registerItemRenderer(item, new ItemRendererBridge(item, r));
                }
            }
        }

        RegisterDimensionSpecialEffectsEvent fx = new RegisterDimensionSpecialEffectsEvent();
        BohMod.MOD_BUS.post(fx);
        DimensionSpecialEffectsManager.set(ImmutableMap.copyOf(fx.effects));
        BohMod.MOD_BUS.post(new FMLClientSetupEvent());
    }
}
