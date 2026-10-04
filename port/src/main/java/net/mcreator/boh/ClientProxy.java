package net.mcreator.boh;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;

import net.mcreator.boh.compat.block.BlockModels;
import net.mcreator.boh.compat.client.ClientEventBridge;
import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.mcreator.boh.compat.forge.client.event.RegisterLayerDefinitions;
import net.mcreator.boh.compat.forge.client.event.RegisterParticleProvidersEvent;
import net.mcreator.boh.compat.forge.client.event.RegisterRenderers;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;

/** Client lifecycle: posts the 1.20 client registration events and binds renderers. */
public class ClientProxy extends CommonProxy {

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public void preInit() {
        ClientEventBridge.install();
        net.mcreator.boh.compat.client.ClientDebugCommand.register();
        net.mcreator.boh.compat.client.ElementBlockRenderer.register();
        MinecraftForge.EVENT_BUS.register(ParticleEngine.EVENTS);
        // textures are stitched between preInit and init, so sprite sets must be known now
        BohMod.MOD_BUS.post(new RegisterParticleProvidersEvent());
        BohMod.MOD_BUS.post(new RegisterLayerDefinitions());
    }

    @Override
    public void init() {
        BlockModels.assignRenderIds();
        net.mcreator.boh.compat.client.VillagerSkins.register();
        net.mcreator.boh.compat.client.BiomeAmbience.register();
        RenderingRegistry.registerEntityRenderingHandler(net.mcreator.boh.compat.entity.BohAreaEffectCloud.class, new net.minecraft.client.renderer.entity.Render() {

            @Override
            public void doRender(net.minecraft.entity.Entity e, double x, double y, double z, float yaw, float pt) {}

            @Override
            protected net.minecraft.util.ResourceLocation getEntityTexture(net.minecraft.entity.Entity e) {
                return null;
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(net.mcreator.boh.compat.entity.BohArrow.class,
            new net.mcreator.boh.compat.mc.client.renderer.entity.ThrownItemRenderer(net.mcreator.boh.compat.mc.client.renderer.entity.Context.INSTANCE));
        BohMod.MOD_BUS.post(new RegisterRenderers());
        for (Map.Entry<EntityType<?>, Function<Context, ?>> e : net.mcreator.boh.compat.client.ClientRegistry.ENTITY.entrySet()) {
            try {
                Object r = e.getValue().apply(Context.INSTANCE);
                if (r instanceof Render && e.getKey().getEntityClass() != null)
                    RenderingRegistry.registerEntityRenderingHandler(e.getKey().getEntityClass(), (Render) r);
            } catch (Throwable t) {
                BohMod.LOGGER.error("Could not create renderer for " + e.getKey().getId(), t);
            }
        }
        for (Map.Entry<BlockEntityType<?>, Function<Context, ?>> e : net.mcreator.boh.compat.client.ClientRegistry.BLOCK_ENTITY.entrySet()) {
            try {
                Object r = e.getValue().apply(Context.INSTANCE);
                if (r instanceof TileEntitySpecialRenderer)
                    cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(e.getKey().tileClass(), (TileEntitySpecialRenderer) r);
            } catch (Throwable t) {
                BohMod.LOGGER.error("Could not create block entity renderer for " + e.getKey().getId(), t);
            }
        }
        for (Object o : net.minecraft.item.Item.itemRegistry) {
            if (!(o instanceof net.mcreator.boh.compat.item.BohItem) && !(o instanceof net.mcreator.boh.compat.item.BohBlockItem)) continue;
            net.minecraft.item.Item item = (net.minecraft.item.Item) o;
            net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer r = net.mcreator.boh.compat.client.ItemExtensions.of(item).getCustomRenderer();
            if (r != null) net.minecraftforge.client.MinecraftForgeClient.registerItemRenderer(item, new net.mcreator.boh.compat.client.ItemRendererBridge(item, r));
        }
        RegisterDimensionSpecialEffectsEvent fx = new RegisterDimensionSpecialEffectsEvent();
        BohMod.MOD_BUS.post(fx);
        DimensionSpecialEffectsManager.set(ImmutableMap.copyOf(fx.effects));
        BohMod.MOD_BUS.post(new FMLClientSetupEvent());
    }
}
