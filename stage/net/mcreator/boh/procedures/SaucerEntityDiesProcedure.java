package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.mcreator.boh.compat.M;

public class SaucerEntityDiesProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World _level && !M.isClientSide(_level)) {
                M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
            }
            if (world instanceof World _level && !M.isClientSide(_level)) {
                M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
            }
            if (world instanceof World _level && !M.isClientSide(_level)) {
                M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
            }
            if (world instanceof World _level && !M.isClientSide(_level)) {
                M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
            }
            BohMod.queueServerWork(10, () -> {
                if (world instanceof World _level && !M.isClientSide(_level)) {
                    M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                }
                if (world instanceof World _level && !M.isClientSide(_level)) {
                    M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                }
                if (world instanceof World _level && !M.isClientSide(_level)) {
                    M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                }
                if (world instanceof World _level && !M.isClientSide(_level)) {
                    M.explode(_level, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                }
                BohMod.queueServerWork(10, () -> {
                    if (world instanceof World _levelxxxx && !M.isClientSide(_levelxxxx)) {
                        M.explode(_levelxxxx, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                    }
                    if (world instanceof World _levelxxx && !M.isClientSide(_levelxxx)) {
                        M.explode(_levelxxx, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                    }
                    if (world instanceof World _levelxx && !M.isClientSide(_levelxx)) {
                        M.explode(_levelxx, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                    }
                    if (world instanceof World _levelx && !M.isClientSide(_levelx)) {
                        M.explode(_levelx, null, x + Mth.nextInt(RandomSource.create(), -20, 20), y, z + Mth.nextInt(RandomSource.create(), -20, 20), 6.0F, ExplosionInteraction.NONE);
                    }
                });
            });
            if (Math.random() < 0.33 && world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(EntityType.COW, _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }
            Entity _ent = entity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/stopsound @a music boh:gray_ost");
            }
            Entity _ent_r53 = entity;
            if (!M.isClientSide(M.level(_ent_r53)) && M.getServer(_ent_r53) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r53)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r53), M.getRotationVector(_ent_r53), M.level(_ent_r53) instanceof WorldServer ? (WorldServer) M.level(_ent_r53) : null, 4, M.getString(M.getName(_ent_r53)), M.getDisplayName(_ent_r53), M.getServer(M.level(_ent_r53)), _ent_r53), "/stopsound @a master boh:mother_ship_idle");
            }
            Entity _ent_r54 = entity;
            if (!M.isClientSide(M.level(_ent_r54)) && M.getServer(_ent_r54) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r54)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r54), M.getRotationVector(_ent_r54), M.level(_ent_r54) instanceof WorldServer ? (WorldServer) M.level(_ent_r54) : null, 4, M.getString(M.getName(_ent_r54)), M.getDisplayName(_ent_r54), M.getServer(M.level(_ent_r54)), _ent_r54), "/stopsound @a master boh:mother_ship_loop");
            }
            BohModVariables.MapVariables.get(world).spawn_saucer = 0.0;
            BohModVariables.MapVariables.get(world).syncData(world);
        }
    }
}
