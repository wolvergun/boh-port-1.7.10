package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.client.event.RenderFog;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class RedmistRenderFogProcedure {

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
                Vec3 pos = M.getPosition(entity, (float) provider.getPartialTick());
                execute(provider, entity);
            }
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, (Potion) BohModMobEffects.RED_MIST.get())) {
                setDistance(0.0F, 10.0F);
            }
        }
    }
}
