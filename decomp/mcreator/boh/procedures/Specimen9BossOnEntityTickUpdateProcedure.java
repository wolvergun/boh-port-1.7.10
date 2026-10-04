package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class Specimen9BossOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!entity.isShiftKeyDown()) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (Math.random() < 0.01) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player) {
                     for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 4); index0++) {
                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)BohModEntities.TAKEN_PILLAR.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }
                     }
                  }
               }
            }

            if (Math.random() < 0.005) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player) {
                     for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 1, 3); index1++) {
                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)BohModEntities.TAKEN_HANDS.get())
                              .spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }
                     }
                  }
               }
            }

            if (Math.random() < 0.01) {
               Entity _ent = entity;
               if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                  _ent.getServer()
                     .getCommands()
                     .performPrefixedCommand(
                        new CommandSourceStack(
                           CommandSource.NULL,
                           _ent.position(),
                           _ent.getRotationVector(),
                           _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                           4,
                           _ent.getName().getString(),
                           _ent.getDisplayName(),
                           _ent.level().getServer(),
                           _ent
                        ),
                        "/particle minecraft:crimson_spore ~ ~1.4 ~ 0 0 0 0.1 100"
                     );
               }

               BohMod.queueServerWork(
                  20,
                  () -> {
                     Entity _entx = entity;
                     if (!_entx.level().isClientSide() && _entx.getServer() != null) {
                        _entx.getServer()
                           .getCommands()
                           .performPrefixedCommand(
                              new CommandSourceStack(
                                 CommandSource.NULL,
                                 _entx.position(),
                                 _entx.getRotationVector(),
                                 _entx.level() instanceof ServerLevel ? (ServerLevel)_entx.level() : null,
                                 4,
                                 _entx.getName().getString(),
                                 _entx.getDisplayName(),
                                 _entx.level().getServer(),
                                 _entx
                              ),
                              "/playsound minecraft:entity.wither.shoot hostile @a ~ ~ ~ 0.4 1.4"
                           );
                     }

                     _entx = entity;
                     Level projectileLevel = _entx.level();
                     if (!projectileLevel.isClientSide()) {
                        Projectile _entityToSpawn = (new Object() {
                              public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
                                 AbstractHurtingProjectile entityToSpawn = new LargeFireball(EntityType.FIREBALL, level);
                                 entityToSpawn.setOwner(shooter);
                                 entityToSpawn.xPower = ax;
                                 entityToSpawn.yPower = ay;
                                 entityToSpawn.zPower = az;
                                 return entityToSpawn;
                              }
                           })
                           .getFireball(
                              projectileLevel, entity, entity.getLookAngle().x / 10.0, entity.getLookAngle().y / 10.0, entity.getLookAngle().z / 10.0
                           );
                        _entityToSpawn.setPos(_entx.getX(), _entx.getEyeY() - 0.1, _entx.getZ());
                        _entityToSpawn.shoot(_entx.getLookAngle().x, _entx.getLookAngle().y, _entx.getLookAngle().z, 1.0F, 0.0F);
                        projectileLevel.addFreshEntity(_entityToSpawn);
                     }
                  }
               );
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player) {
                  entity.lookAt(Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()));
               }
            }
         }

         if (!entity.getPersistentData().getBoolean("Loop")) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                     SoundSource.MUSIC,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                     SoundSource.MUSIC,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("Loop", true);
            BohMod.queueServerWork(1920, () -> entity.getPersistentData().putBoolean("Loop", false));
         }

         if (!entity.isShiftKeyDown() && entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 200, false, false));
         }
      }
   }
}
