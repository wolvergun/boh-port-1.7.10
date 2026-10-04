package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class JasonsMaskHelmetTickEventProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && (M.isInWater(entity) || M.isRaining(M.getLevelData(world)) && M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z)))) {
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 60, 0));
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 60, 0));
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.WATER_BREATHING, 60, 0));
            }
        }
    }
}
