package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SonicBoomEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class SonicBoomOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(2, () -> {
                if (entity instanceof SonicBoomEntity) {
                    ((SonicBoomEntity) entity).setAnimation("spawn");
                }
                Entity _ent = entity;
                M.teleportTo(_ent, x, y + 8.0, z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y + 8.0, z, M.getYRot(_ent), M.getXRot(_ent));
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
