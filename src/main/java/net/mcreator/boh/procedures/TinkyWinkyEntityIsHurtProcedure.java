package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TinkyWinkyEntityIsHurtProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null
            && Math.random() < 0.2
            && (entity instanceof EntityLivingBase _livEntx ? M.getHealth(_livEntx) : -1.0F)
                < (entity instanceof EntityLivingBase _livEnt ? M.getMaxHealth(_livEnt) : -1.0F) / 3.0F) {
            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }

            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(
                    BohModEntities.TINKY_TANK.get(), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED
                );
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
