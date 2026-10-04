package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class VampirismOnEffectActiveTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World _lvl0 && M.isDay(_lvl0) && M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z))) {
                M.setSecondsOnFire(entity, 5);
            }
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.HEAL, 60, 0, false, false));
            }
        }
    }
}
