package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.XenomorphEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class AttackXenomorphProcedure {
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
        if (entity != null && sourceentity != null && sourceentity instanceof XenomorphEntity) {
            if (sourceentity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.DAMAGE_BOOST)) {
                if (sourceentity instanceof XenomorphEntity) {
                    ((XenomorphEntity)sourceentity).setAnimation("attack_tail");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.POISON, 200, 0));
                }
            } else if (sourceentity instanceof XenomorphEntity) {
                ((XenomorphEntity)sourceentity).setAnimation("attack");
            }
        }
    }
}
