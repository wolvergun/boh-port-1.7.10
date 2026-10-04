package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PhantomBBEntity;
import net.mcreator.boh.entity.PhantomChicaEntity;
import net.mcreator.boh.entity.PhantomFoxyEntity;
import net.mcreator.boh.entity.PhantomFreddyEntity;
import net.mcreator.boh.entity.PhantomMangleEntity;
import net.mcreator.boh.entity.PhantomPuppetEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MassacreAxeRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (entity instanceof Player _player) {
            _player.getCooldowns().addCooldown(itemstack.getItem(), 300);
         }

         ItemStack _ist = itemstack;
         if (_ist.hurt(5, RandomSource.create(), null)) {
            _ist.shrink(1);
            _ist.setDamageValue(0);
         }

         if (itemstack.getOrCreateTag().getDouble("summon_animatronic") < 5.0) {
            itemstack.getOrCreateTag().putDouble("summon_animatronic", itemstack.getOrCreateTag().getDouble("summon_animatronic") + 1.0);
         } else {
            itemstack.getOrCreateTag().putDouble("summon_animatronic", 0.0);
         }

         if (itemstack.getOrCreateTag().getDouble("summon_animatronic") == 0.0) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.PHANTOM_FREDDY.get())
                  .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               2,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof PhantomFreddyEntity
                        && !(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
                        && entityiterator instanceof TamableAnimal _toTame
                        && entity instanceof Player _owner) {
                        _toTame.tame(_owner);
                     }
                  }
               }
            );
         } else if (itemstack.getOrCreateTag().getDouble("summon_animatronic") == 1.0) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.PHANTOM_CHICA.get())
                  .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               2,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof PhantomChicaEntity
                        && !(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
                        && entityiterator instanceof TamableAnimal _toTame
                        && entity instanceof Player _owner) {
                        _toTame.tame(_owner);
                     }
                  }
               }
            );
         } else if (itemstack.getOrCreateTag().getDouble("summon_animatronic") == 2.0) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.PHANTOM_BB.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               2,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof PhantomBBEntity
                        && !(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
                        && entityiterator instanceof TamableAnimal _toTame
                        && entity instanceof Player _owner) {
                        _toTame.tame(_owner);
                     }
                  }
               }
            );
         } else if (itemstack.getOrCreateTag().getDouble("summon_animatronic") == 3.0) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.PHANTOM_FOXY.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               2,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof PhantomFoxyEntity
                        && !(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
                        && entityiterator instanceof TamableAnimal _toTame
                        && entity instanceof Player _owner) {
                        _toTame.tame(_owner);
                     }
                  }
               }
            );
         } else if (itemstack.getOrCreateTag().getDouble("summon_animatronic") == 4.0) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.PHANTOM_MANGLE.get())
                  .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               2,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof PhantomMangleEntity
                        && !(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
                        && entityiterator instanceof TamableAnimal _toTame
                        && entity instanceof Player _owner) {
                        _toTame.tame(_owner);
                     }
                  }
               }
            );
         } else if (itemstack.getOrCreateTag().getDouble("summon_animatronic") == 5.0) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.PHANTOM_PUPPET.get())
                  .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               2,
               () -> {
                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof PhantomPuppetEntity
                        && !(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
                        && entityiterator instanceof TamableAnimal _toTame
                        && entity instanceof Player _owner) {
                        _toTame.tame(_owner);
                     }
                  }
               }
            );
         }
      }
   }
}
