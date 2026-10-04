package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PlayerFixStaticProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.player(event));
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (!(entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, BohModMobEffects.HIDE_AND_SEEK.get()))) {
                M.putDouble(M.getPersistentData(entity), "exe_static", 0.0);
                M.putDouble(M.getPersistentData(entity), "exe_apparison", 0.0);
            }

            if (!(entity instanceof EntityLivingBase _livEnt3 && M.hasEffect(_livEnt3, BohModMobEffects.ENGAGED.get()))) {
                M.putDouble(M.getPersistentData(entity), "static_slender", 0.0);
            }
        }
    }
}
