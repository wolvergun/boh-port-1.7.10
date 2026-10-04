package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.GhostfaceDecoyEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GhostfaceOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _livEnt0
            && _livEnt0.hasEffect(MobEffects.SATURATION)
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true).isEmpty()
            && entity instanceof LivingEntity _entity) {
            _entity.removeEffect(MobEffects.SATURATION);
         }

         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
            && world.getEntitiesOfClass(GhostfaceDecoyEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true).isEmpty()
            && world instanceof ServerLevel _level) {
            Entity entityToSpawn = ((EntityType)BohModEntities.GHOSTFACE_DECOY.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
            }
         }

         if (entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(MobEffects.SATURATION)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 1, false, false));
            }

            entity.getPersistentData().putDouble("timer_ghostface", entity.getPersistentData().getDouble("timer_ghostface") + 1.0);
         }

         if (entity.getPersistentData().getDouble("timer_ghostface") >= 500.0 && entity instanceof LivingEntity _entity) {
            _entity.removeEffect(MobEffects.SATURATION);
         }

         if (world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true).isEmpty()) {
            entity.getPersistentData().putDouble("timer_ghostface", 0.0);
         }
      }
   }
}
