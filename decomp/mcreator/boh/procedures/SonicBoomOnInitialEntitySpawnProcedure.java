package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SonicBoomEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class SonicBoomOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(2, () -> {
            if (entity instanceof SonicBoomEntity) {
               ((SonicBoomEntity)entity).setAnimation("spawn");
            }

            Entity _ent = entity;
            _ent.teleportTo(x, y + 8.0, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, y + 8.0, z, _ent.getYRot(), _ent.getXRot());
            }
         });
         BohMod.queueServerWork(40, () -> {
            if (!entity.level().isClientSide()) {
               entity.discard();
            }
         });
      }
   }
}
