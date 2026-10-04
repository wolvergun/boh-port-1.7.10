package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class StealHealthProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity sourceentity) {
        execute(null, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity sourceentity) {
        if (sourceentity != null
            && sourceentity instanceof EntityLivingBase _livEnt0
            && M.hasEffect(_livEnt0, BohModMobEffects.VAMPIRISM.get())
            && sourceentity instanceof EntityLivingBase _entity
            && !M.isClientSide(M.level(_entity))) {
            M.addEffect(_entity, M.new_PotionEffect(MobEffects.REGENERATION, 5, 1));
        }
    }
}
