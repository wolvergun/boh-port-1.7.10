package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LifeformEffigyDieProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event));
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null
            && (
                M.getItem(entity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY) == BohModItems.LIFEFORM_EFFIGY.get()
                    || M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getOffhandItem(_livEnt) : M.EMPTY) == BohModItems.LIFEFORM_EFFIGY.get()
            )) {
            if (event != null && M.isCancelable(event)) {
                M.setCanceled(event, true);
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.REGENERATION, 60, 5));
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.HEAL, 60, 5));
            }
        }
    }
}
