package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.client.event.RenderFog;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFogShroudedCliffsProcedure {
    public static RenderFog provider = null;

    public static void setDistance(float start, float end) {
        M.setNearPlaneDistance(provider, start);
        M.setFarPlaneDistance(provider, end);
        if (!M.isCanceled(provider)) {
            M.setCanceled(provider, true);
        }
    }

    public static void setShape(FogShape shape) {
        M.setFogShape(provider, shape);
        if (!M.isCanceled(provider)) {
            M.setCanceled(provider, true);
        }
    }

    @SubscribeEvent
    public void renderFog(RenderFog event) {
        provider = event;
        if (M.getMode(provider) == FogMode.FOG_TERRAIN) {
            WorldClient level = M.level(Minecraft.getMinecraft());
            Entity entity = M.getEntity(M.getCamera(provider));
            if (level != null && entity != null) {
                Vec3 pos = M.getPosition(entity, (float)provider.getPartialTick());
                execute(provider, level, pos.x(), pos.y(), pos.z());
            }
        }
    }

    public static void execute(World world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z) {
        if (M.is(M.getBiome(world, BlockPos.containing(x, y, z)), new ResourceLocation("boh:shrouded_cliffs"))) {
            setDistance(30.0F, 140.0F);
        }
    }
}
