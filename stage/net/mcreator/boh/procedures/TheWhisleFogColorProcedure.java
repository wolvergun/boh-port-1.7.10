package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.util.Mth;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.client.event.ComputeFogColor;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class TheWhisleFogColorProcedure {

    public static ComputeFogColor provider = null;

    public static void setColor(int color) {
        M.setRed(provider, (color >> 16 & 0xFF) / 255.0F);
        M.setGreen(provider, (color >> 8 & 0xFF) / 255.0F);
        M.setBlue(provider, (color & 0xFF) / 255.0F);
    }

    public static void setColor(float level, int color) {
        if (!(level <= 0.0F)) {
            if (level >= 1.0F) {
                M.setRed(provider, (color >> 16 & 0xFF) / 255.0F);
                M.setGreen(provider, (color >> 8 & 0xFF) / 255.0F);
                M.setBlue(provider, (color & 0xFF) / 255.0F);
            } else {
                level = Mth.clamp(level, 0.0F, 1.0F);
                M.setRed(provider, Mth.clamp(Mth.lerp(level, Mth.clamp(M.getRed(provider), 0.0F, 1.0F), (color >> 16 & 0xFF) / 255.0F), 0.0F, 1.0F));
                M.setGreen(provider, Mth.clamp(Mth.lerp(level, Mth.clamp(M.getGreen(provider), 0.0F, 1.0F), (color >> 8 & 0xFF) / 255.0F), 0.0F, 1.0F));
                M.setBlue(provider, Mth.clamp(Mth.lerp(level, Mth.clamp(M.getBlue(provider), 0.0F, 1.0F), (color & 0xFF) / 255.0F), 0.0F, 1.0F));
            }
        }
    }

    @SubscribeEvent
    public void computeFogColor(ComputeFogColor event) {
        provider = event;
        WorldClient level = M.level(Minecraft.getMinecraft());
        Entity entity = M.getEntity(M.getCamera(provider));
        if (level != null && entity != null) {
            Vec3 entPos = M.getPosition(entity, (float) provider.getPartialTick());
            execute(provider, entity);
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (M.dimension(M.level(entity)) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:baseplate_dimension")) && entity instanceof EntityLivingBase _livEnt3 && M.hasEffect(_livEnt3, (Potion) BohModMobEffects.THE_WHISLE.get())) {
                setColor(-16777216);
            }
        }
    }
}
