package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.WarHorseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class PlayerRideApocalipseHorsesProcedure {
    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        execute(event, M.getEntity(event));
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null && M.getVehicle(entity) instanceof WarHorseEntity) {
            entity.fallDistance = 0.0F;
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.FIRE_RESISTANCE, 60, 0, false, false));
            }
        }
    }
}
