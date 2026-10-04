package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.DeerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class DeerOnInitialEntitySpawnProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (!(entity instanceof EntityLivingBase _livEnt0 && M.isBaby(_livEnt0))) {
                BohMod.queueServerWork(2, () -> M.putDouble(M.getPersistentData(entity), "size", 1.0));
                if (Math.random() < 0.05) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.DEER_MIMIC.get()), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED);
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
            if (Math.random() < 0.5) {
                BohMod.queueServerWork(2, () -> {
                    if (entity instanceof DeerEntity animatable) {
                        animatable.setTexture("doe");
                    }
                });
            }
        }
    }
}
