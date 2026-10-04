package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SaucerEntityDiesProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World && !M.isClientSide(world)) {
                M.explode(
                    world,
                    null,
                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                    y,
                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                    6.0F,
                    ExplosionInteraction.NONE
                );
            }

            if (world instanceof World && !M.isClientSide(world)) {
                M.explode(
                    world,
                    null,
                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                    y,
                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                    6.0F,
                    ExplosionInteraction.NONE
                );
            }

            if (world instanceof World && !M.isClientSide(world)) {
                M.explode(
                    world,
                    null,
                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                    y,
                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                    6.0F,
                    ExplosionInteraction.NONE
                );
            }

            if (world instanceof World && !M.isClientSide(world)) {
                M.explode(
                    world,
                    null,
                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                    y,
                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                    6.0F,
                    ExplosionInteraction.NONE
                );
            }

            BohMod.queueServerWork(
                10,
                () -> {
                    if (world instanceof World && !M.isClientSide(world)) {
                        M.explode(
                            world,
                            null,
                            x + Mth.nextInt(RandomSource.create(), -20, 20),
                            y,
                            z + Mth.nextInt(RandomSource.create(), -20, 20),
                            6.0F,
                            ExplosionInteraction.NONE
                        );
                    }

                    if (world instanceof World && !M.isClientSide(world)) {
                        M.explode(
                            world,
                            null,
                            x + Mth.nextInt(RandomSource.create(), -20, 20),
                            y,
                            z + Mth.nextInt(RandomSource.create(), -20, 20),
                            6.0F,
                            ExplosionInteraction.NONE
                        );
                    }

                    if (world instanceof World && !M.isClientSide(world)) {
                        M.explode(
                            world,
                            null,
                            x + Mth.nextInt(RandomSource.create(), -20, 20),
                            y,
                            z + Mth.nextInt(RandomSource.create(), -20, 20),
                            6.0F,
                            ExplosionInteraction.NONE
                        );
                    }

                    if (world instanceof World && !M.isClientSide(world)) {
                        M.explode(
                            world,
                            null,
                            x + Mth.nextInt(RandomSource.create(), -20, 20),
                            y,
                            z + Mth.nextInt(RandomSource.create(), -20, 20),
                            6.0F,
                            ExplosionInteraction.NONE
                        );
                    }

                    BohMod.queueServerWork(
                        10,
                        () -> {
                            if (world instanceof World && !M.isClientSide(world)) {
                                M.explode(
                                    world,
                                    null,
                                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                                    y,
                                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                                    6.0F,
                                    ExplosionInteraction.NONE
                                );
                            }

                            if (world instanceof World && !M.isClientSide(world)) {
                                M.explode(
                                    world,
                                    null,
                                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                                    y,
                                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                                    6.0F,
                                    ExplosionInteraction.NONE
                                );
                            }

                            if (world instanceof World && !M.isClientSide(world)) {
                                M.explode(
                                    world,
                                    null,
                                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                                    y,
                                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                                    6.0F,
                                    ExplosionInteraction.NONE
                                );
                            }

                            if (world instanceof World && !M.isClientSide(world)) {
                                M.explode(
                                    world,
                                    null,
                                    x + Mth.nextInt(RandomSource.create(), -20, 20),
                                    y,
                                    z + Mth.nextInt(RandomSource.create(), -20, 20),
                                    6.0F,
                                    ExplosionInteraction.NONE
                                );
                            }
                        }
                    );
                }
            );
            if (Math.random() < 0.33 && world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(EntityType.COW, _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }

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
                    "/stopsound @a music boh:gray_ost"
                );
            }

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
                    "/stopsound @a master boh:mother_ship_idle"
                );
            }

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
                    "/stopsound @a master boh:mother_ship_loop"
                );
            }

            BohModVariables.MapVariables.get(world).spawn_saucer = 0.0;
            BohModVariables.MapVariables.get(world).syncData(world);
        }
    }
}
