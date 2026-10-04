package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.FuwattiEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class AoOniOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player) {
                  boolean _setval = true;
                  entityiterator.getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                     capability.chase_aoni = _setval;
                     capability.syncPlayerVariables(entityiterator);
                  });
               }
            }
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) && world instanceof ServerLevel _level) {
            _level.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                        CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null
                     )
                     .withSuppressedOutput(),
                  "/stopsound @a music boh:ao_oni_chase"
               );
         }

         if (world.getEntitiesOfClass(FuwattiEntity.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true).isEmpty()
            && entity instanceof Mob _mob
            && _mob.isAggressive()
            && Math.random() < 0.2
            && Math.random() < 0.1
            && world instanceof ServerLevel _level) {
            Entity entityToSpawn = ((EntityType)BohModEntities.FUWATTI.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
            }
         }
      }
   }
}
