package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class StopAttackingOwnerParasiteProcedure {

    @SubscribeEvent
    public void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(event, M.getOriginalTarget(event), M.getEntity(event));
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (M.getItem((entity instanceof EntityLivingBase _entGetArmor ? M.getItemBySlot(_entGetArmor, EquipmentSlot.HEAD) : M.EMPTY)) == BohModItems.WHISPERING_THORNS_HELMET.get() && sourceentity instanceof EntityLivingBase _livEnt2 && M.hasEffect(_livEnt2, (Potion) BohModMobEffects.PARASITES_SONG.get()) && sourceentity instanceof EntityLiving) {
                try {
                    M.setTarget(((EntityLiving) sourceentity), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
