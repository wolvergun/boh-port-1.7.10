package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.SonicBoomEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class SonicBoomOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(2, () -> {
                if (entity instanceof SonicBoomEntity) {
                    ((SonicBoomEntity)entity).setAnimation("spawn");
                }

                M.teleportTo(entity, x, y + 8.0, z);
                if (entity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y + 8.0, z, M.getYRot(entity), M.getXRot(entity));
                }
            });
            BohMod.queueServerWork(40, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
        }
    }
}
