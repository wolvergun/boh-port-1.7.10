package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class Level0PlayerEntersDimensionProcedure {
   public static void execute(LevelAccessor world, double x, double z, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(5, () -> {
            Entity _ent = entity;
            _ent.teleportTo(x, -63.0, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, -63.0, z, _ent.getYRot(), _ent.getXRot());
            }

            entity.fallDistance = 0.0F;
         });
      }
   }
}
