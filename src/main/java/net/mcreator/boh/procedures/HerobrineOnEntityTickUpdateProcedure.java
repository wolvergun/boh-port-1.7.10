package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.entity.HerobrineEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;

public class HerobrineOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof HerobrineEntity _datEntSetI) {
                M.set(
                    M.getEntityData(_datEntSetI),
                    HerobrineEntity.DATA_timer,
                    (entity instanceof HerobrineEntity _datEntI ? M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) + 1
                );
            }

            if ((entity instanceof HerobrineEntity _datEntI ? M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) == 3) {
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
                        "/execute at @e[type=boh:herobrine] run playsound minecraft:ambient.cave hostile @a"
                    );
                }

                if (Math.random() < 0.7) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
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
                            "/execute at @e[type=boh:herobrine] run playsound minecraft:entity.ghast.hurt hostile @a"
                        );
                    }
                }
            }

            if ((entity instanceof HerobrineEntity _datEntI ? M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) == 20
                && !M.isClientSide(M.level(entity))
                && M.getServer(entity) != null) {
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
                    "/spreadplayers ~ ~ 5 5 false @e[type=boh:herobrine,limit=1,distance=0..2]"
                );
            }

            if ((entity instanceof HerobrineEntity _datEntI ? M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) == 60) {
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
                        "/execute at @e[type=boh:herobrine] run particle minecraft:large_smoke ~ ~1 ~ 0.2 0.5 0.2 0 100 force"
                    );
                }

                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            }
        }
    }
}
