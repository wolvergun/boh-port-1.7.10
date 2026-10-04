package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChuckyGrabOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof LivingEntity _livEnt0
               && _livEnt0.hasEffect((MobEffect)BohModMobEffects.EFFECT_CHUCKY_GRAB.get())
               && entityiterator instanceof Player) {
               Entity _ent = entity;
               _ent.setYRot(entityiterator.getYRot());
               _ent.setXRot(entityiterator.getXRot());
               _ent.setYBodyRot(_ent.getYRot());
               _ent.setYHeadRot(_ent.getYRot());
               _ent.yRotO = _ent.getYRot();
               _ent.xRotO = _ent.getXRot();
               if (_ent instanceof LivingEntity _entity) {
                  _entity.yBodyRotO = _entity.getYRot();
                  _entity.yHeadRotO = _entity.getYRot();
               }

               _ent = entity;
               _ent.teleportTo(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ());
               if (_ent instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.connection
                     .teleport(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), _ent.getYRot(), _ent.getXRot());
               }
            }
         }

         Entity var16 = world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 2.0, 2.0, 2.0), e -> true).stream().sorted((new Object() {
            Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
               return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
            }
         }).compareDistOf(x, y, z)).findFirst().orElse(null);
         if (!(var16 instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect((MobEffect)BohModMobEffects.EFFECT_CHUCKY_GRAB.get()))) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.CHUCKY.get()).spawn(_level, BlockPos.containing(x, y + 2.0, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
               }
            }

            if (!entity.level().isClientSide()) {
               entity.discard();
            }
         }
      }
   }
}
