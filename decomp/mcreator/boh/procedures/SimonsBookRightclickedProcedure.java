package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.item.SimonsBookItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SimonsBookRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         ItemStack _ist = itemstack;
         if (_ist.hurt(1, RandomSource.create(), null)) {
            _ist.shrink(1);
            _ist.setDamageValue(0);
         }

         BohMod.queueServerWork(
            2,
            () -> {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               Entity _shootFrom = entity;
               Level projectileLevel = _shootFrom.level();
               if (!projectileLevel.isClientSide()) {
                  Projectile _entityToSpawn = (new Object() {
                        public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                           AbstractHurtingProjectile entityToSpawn = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                           entityToSpawn.setOwner(shooter);
                           entityToSpawn.xPower = ax;
                           entityToSpawn.yPower = ay;
                           entityToSpawn.zPower = az;
                           return entityToSpawn;
                        }
                     })
                     .getFireball(
                        projectileLevel,
                        entity,
                        Mth.nextInt(RandomSource.create(), 0, 0),
                        Mth.nextInt(RandomSource.create(), 0, 0),
                        Mth.nextInt(RandomSource.create(), 0, 0)
                     );
                  _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                  _entityToSpawn.shoot(
                     _shootFrom.getLookAngle().x,
                     _shootFrom.getLookAngle().y,
                     _shootFrom.getLookAngle().z,
                     Mth.nextInt(RandomSource.create(), 1, 1),
                     0.0F
                  );
                  projectileLevel.addFreshEntity(_entityToSpawn);
               }

               BohMod.queueServerWork(
                  2,
                  () -> {
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                              SoundSource.NEUTRAL,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                              SoundSource.NEUTRAL,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }

                     Entity _shootFromx = entity;
                     Level projectileLevelx = _shootFromx.level();
                     if (!projectileLevelx.isClientSide()) {
                        Projectile _entityToSpawnx = (new Object() {
                              public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                                 AbstractHurtingProjectile entityToSpawn = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                                 entityToSpawn.setOwner(shooter);
                                 entityToSpawn.xPower = ax;
                                 entityToSpawn.yPower = ay;
                                 entityToSpawn.zPower = az;
                                 return entityToSpawn;
                              }
                           })
                           .getFireball(
                              projectileLevelx,
                              entity,
                              Mth.nextInt(RandomSource.create(), 0, 0),
                              Mth.nextInt(RandomSource.create(), 0, 0),
                              Mth.nextInt(RandomSource.create(), 0, 0)
                           );
                        _entityToSpawnx.setPos(_shootFromx.getX(), _shootFromx.getEyeY() - 0.1, _shootFromx.getZ());
                        _entityToSpawnx.shoot(
                           _shootFromx.getLookAngle().x,
                           _shootFromx.getLookAngle().y,
                           _shootFromx.getLookAngle().z,
                           Mth.nextInt(RandomSource.create(), 1, 1),
                           0.0F
                        );
                        projectileLevelx.addFreshEntity(_entityToSpawnx);
                     }

                     BohMod.queueServerWork(
                        2,
                        () -> {
                           if (world instanceof Level _level) {
                              if (!_level.isClientSide()) {
                                 _level.playSound(
                                    null,
                                    BlockPos.containing(x, y, z),
                                    (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                    SoundSource.NEUTRAL,
                                    1.0F,
                                    1.0F
                                 );
                              } else {
                                 _level.playLocalSound(
                                    x,
                                    y,
                                    z,
                                    (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                    SoundSource.NEUTRAL,
                                    1.0F,
                                    1.0F,
                                    false
                                 );
                              }
                           }

                           Entity _shootFromxx = entity;
                           Level projectileLevelxx = _shootFromxx.level();
                           if (!projectileLevelxx.isClientSide()) {
                              Projectile _entityToSpawnxx = (new Object() {
                                    public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                                       AbstractHurtingProjectile entityToSpawn = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                                       entityToSpawn.setOwner(shooter);
                                       entityToSpawn.xPower = ax;
                                       entityToSpawn.yPower = ay;
                                       entityToSpawn.zPower = az;
                                       return entityToSpawn;
                                    }
                                 })
                                 .getFireball(
                                    projectileLevelxx,
                                    entity,
                                    Mth.nextInt(RandomSource.create(), 0, 0),
                                    Mth.nextInt(RandomSource.create(), 0, 0),
                                    Mth.nextInt(RandomSource.create(), 0, 0)
                                 );
                              _entityToSpawnxx.setPos(_shootFromxx.getX(), _shootFromxx.getEyeY() - 0.1, _shootFromxx.getZ());
                              _entityToSpawnxx.shoot(
                                 _shootFromxx.getLookAngle().x,
                                 _shootFromxx.getLookAngle().y,
                                 _shootFromxx.getLookAngle().z,
                                 Mth.nextInt(RandomSource.create(), 1, 1),
                                 0.0F
                              );
                              projectileLevelxx.addFreshEntity(_entityToSpawnxx);
                           }

                           BohMod.queueServerWork(
                              2,
                              () -> {
                                 if (world instanceof Level _level) {
                                    if (!_level.isClientSide()) {
                                       _level.playSound(
                                          null,
                                          BlockPos.containing(x, y, z),
                                          (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                          SoundSource.NEUTRAL,
                                          1.0F,
                                          1.0F
                                       );
                                    } else {
                                       _level.playLocalSound(
                                          x,
                                          y,
                                          z,
                                          (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")),
                                          SoundSource.NEUTRAL,
                                          1.0F,
                                          1.0F,
                                          false
                                       );
                                    }
                                 }

                                 Entity _shootFromxxx = entity;
                                 Level projectileLevelxxx = _shootFromxxx.level();
                                 if (!projectileLevelxxx.isClientSide()) {
                                    Projectile _entityToSpawnxxx = (new Object() {
                                          public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                                             AbstractHurtingProjectile entityToSpawn = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                                             entityToSpawn.setOwner(shooter);
                                             entityToSpawn.xPower = ax;
                                             entityToSpawn.yPower = ay;
                                             entityToSpawn.zPower = az;
                                             return entityToSpawn;
                                          }
                                       })
                                       .getFireball(
                                          projectileLevelxxx,
                                          entity,
                                          Mth.nextInt(RandomSource.create(), 0, 0),
                                          Mth.nextInt(RandomSource.create(), 0, 0),
                                          Mth.nextInt(RandomSource.create(), 0, 0)
                                       );
                                    _entityToSpawnxxx.setPos(_shootFromxxx.getX(), _shootFromxxx.getEyeY() - 0.1, _shootFromxxx.getZ());
                                    _entityToSpawnxxx.shoot(
                                       _shootFromxxx.getLookAngle().x,
                                       _shootFromxxx.getLookAngle().y,
                                       _shootFromxxx.getLookAngle().z,
                                       Mth.nextInt(RandomSource.create(), 1, 1),
                                       0.0F
                                    );
                                    projectileLevelxxx.addFreshEntity(_entityToSpawnxxx);
                                 }

                                 BohMod.queueServerWork(
                                    20,
                                    () -> {
                                       Vec3 _center = new Vec3(x, y, z);

                                       for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20.0), e -> true)
                                          .stream()
                                          .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                          .toList()) {
                                          if (entityiterator instanceof SmallFireball && !entityiterator.level().isClientSide()) {
                                             entityiterator.discard();
                                          }
                                       }
                                    }
                                 );
                              }
                           );
                        }
                     );
                  }
               );
            }
         );
         if (itemstack.getItem() instanceof SimonsBookItem) {
            itemstack.getOrCreateTag().putString("geckoAnim", "fire");
         }

         if (entity instanceof Player _player) {
            _player.getCooldowns().addCooldown(itemstack.getItem(), 100);
         }

         BohMod.queueServerWork(20, () -> {
            if (itemstack.getItem() instanceof SimonsBookItem) {
               itemstack.getOrCreateTag().putString("geckoAnim", "empty");
            }
         });
      }
   }
}
