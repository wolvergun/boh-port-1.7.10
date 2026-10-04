package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.entity.SonicExeEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class TargetedByExeProcedure {
    @SubscribeEvent
    public void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(event, M.getOriginalTarget(event), M.getEntity(event));
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && sourceentity instanceof SonicExeEntity
            && entity instanceof EntityLivingBase _entity
            && !M.isClientSide(M.level(_entity))) {
            M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.HIDE_AND_SEEK.get(), 300, 0, false, false));
        }
    }
}
