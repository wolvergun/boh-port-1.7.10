package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DecoyDogRightClickedOnEntityProcedure {
    public static void execute(World world, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == Items.BONE) {
            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }

            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(
                    BohModEntities.SMILE_DOG.get(), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED
                );
                if (entityToSpawn != null) {
                    M.setYRot(entityToSpawn, M.getYRot(entity));
                    M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                    M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                    M.setXRot(entityToSpawn, M.getXRot(entity));
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
