package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.RatazanaEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class AttackedByRatazanaProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && sourceentity instanceof RatazanaEntity
            && entity instanceof EntityLivingBase _entity
            && !M.isClientSide(M.level(_entity))) {
            M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.BLESSED.get(), 120, 0, false, false));
        }
    }
}
