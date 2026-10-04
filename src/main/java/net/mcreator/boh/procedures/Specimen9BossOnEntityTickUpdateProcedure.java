package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Specimen9BossOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.isShiftKeyDown(entity)) {
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (Math.random() < 0.01) {
                    Vec3 _center = new Vec3(x, y, z);

                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                        .toList()) {
                        if (entityiterator instanceof EntityPlayer) {
                            for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 4); index0++) {
                                if (world instanceof WorldServer _level) {
                                    Entity entityToSpawn = M.spawn(
                                        BohModEntities.TAKEN_PILLAR.get(),
                                        _level,
                                        BlockPos.containing(M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator)),
                                        MobSpawnType.MOB_SUMMONED
                                    );
                                    if (entityToSpawn != null) {
                                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                    }
                                }
                            }
                        }
                    }
                }

                if (Math.random() < 0.005) {
                    Vec3 _center = new Vec3(x, y, z);

                    for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                        .toList()) {
                        if (entityiteratorx instanceof EntityPlayer) {
                            for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 1, 3); index1++) {
                                if (world instanceof WorldServer _levelx) {
                                    Entity entityToSpawn = M.spawn(
                                        BohModEntities.TAKEN_HANDS.get(), _levelx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED
                                    );
                                    if (entityToSpawn != null) {
                                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                                    }
                                }
                            }
                        }
                    }
                }

                if (Math.random() < 0.01) {
                    if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "/particle minecraft:crimson_spore ~ ~1.4 ~ 0 0 0 0.1 100"
                        );
                    }

                    BohMod.queueServerWork(
                        20,
                        () -> {
                            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                                M.performPrefixedCommand(
                                    M.getCommands(M.getServer(entity)),
                                    new CommandSourceStack(
                                        CommandSource.NULL,
                                        M.position(entity),
                                        M.getRotationVector(entity),
                                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                        4,
                                        M.getString(M.getName(entity)),
                                        M.getDisplayName(entity),
                                        M.getServer(M.level(entity)),
                                        entity
                                    ),
                                    "/playsound minecraft:entity.wither.shoot hostile @a ~ ~ ~ 0.4 1.4"
                                );
                            }

                            World projectileLevel = M.level(entity);
                            if (!M.isClientSide(projectileLevel)) {
                                Entity _entityToSpawn = (new Object() {
                                        public Entity getFireball(World level, Entity shooter, double ax, double ay, double az) {
                                            EntityFireball entityToSpawn = M.new_EntityLargeFireball(EntityType.FIREBALL, level);
                                            M.setOwner(entityToSpawn, shooter);
                                            M.set_xPower(entityToSpawn, ax);
                                            M.set_yPower(entityToSpawn, ay);
                                            M.set_zPower(entityToSpawn, az);
                                            return entityToSpawn;
                                        }
                                    })
                                    .getFireball(
                                        projectileLevel,
                                        entity,
                                        M.getLookAngle(entity).x / 10.0,
                                        M.getLookAngle(entity).y / 10.0,
                                        M.getLookAngle(entity).z / 10.0
                                    );
                                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 1.0F, 0.0F);
                                M.addFreshEntity(projectileLevel, _entityToSpawn);
                            }
                        }
                    );
                }

                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiteratorxx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiteratorxx instanceof EntityPlayer) {
                        M.lookAt(entity, Anchor.EYES, new Vec3(M.getX(entityiteratorxx), M.getY(entityiteratorxx), M.getZ(entityiteratorxx)));
                    }
                }
            }

            if (!M.getBoolean(M.getPersistentData(entity), "Loop")) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                            SoundSource.MUSIC,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                            SoundSource.MUSIC,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "Loop", true);
                BohMod.queueServerWork(1920, () -> M.putBoolean(M.getPersistentData(entity), "Loop", false));
            }

            if (!M.isShiftKeyDown(entity) && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_RESISTANCE, 20, 200, false, false));
            }
        }
    }
}
