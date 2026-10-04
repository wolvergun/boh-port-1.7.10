package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class ChuckyGrabEntityIsHurtProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof EntityPlayer && Math.random() < 0.25) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.CHUCKY.get()), _level, BlockPos.containing(x, y + 2.0, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                    }
                }
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
                if (sourceentity instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, (Potion) BohModMobEffects.EFFECT_CHUCKY_GRAB.get());
                }
            }
        }
    }
}
