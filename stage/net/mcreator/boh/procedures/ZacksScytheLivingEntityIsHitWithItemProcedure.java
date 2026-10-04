package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class ZacksScytheLivingEntityIsHitWithItemProcedure {

    public static void execute(World world, Entity sourceentity) {
        if (sourceentity != null) {
            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 60, 2));
            }
            if (Math.random() < 0.1) {
                M.hurt(sourceentity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 2.0F);
            }
        }
    }
}
