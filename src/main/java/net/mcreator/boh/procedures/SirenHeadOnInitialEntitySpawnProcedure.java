package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SirenHeadOnInitialEntitySpawnProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohModVariables.MapVariables.get(world).spawn_siren = 1.0;
            BohModVariables.MapVariables.get(world).syncData(world);
            if (entity instanceof SirenHeadEntity && Math.random() < 0.5) {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }

                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.LIGHT_HEAD.get(), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED
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
}
