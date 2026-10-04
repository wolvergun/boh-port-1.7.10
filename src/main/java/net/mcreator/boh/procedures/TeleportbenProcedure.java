package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.BenDrownedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportbenProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.getX(M.player(event)), M.getY(M.player(event)), M.getZ(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && (new Object() {
                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.SURVIVAL;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player
                                ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null
                                    && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))))
                                        == GameType.SURVIVAL
                                : false;
                        }
                    }
                })
                .checkGamemode(entity)
            && M.isEmpty(M.getEntitiesOfClass(world, BenDrownedEntity.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true))) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof BenDrownedEntity
                    && !M.getBoolean(M.getPersistentData(entityiterator), "inWater")
                    && Math.random() < 0.1
                    && Math.random() < 0.1) {
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
                            "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 1"
                        );
                    }

                    BohMod.queueServerWork(
                        10,
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
                                    "/execute at @p[gamemode=survival] rotated ~ 1 run spreadplayers ~ ~ 1 2 false @e[type=boh:ben_drowned,limit=1]"
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
                                    "/execute at @e[type=boh:ben_drowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~"
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
                                    "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 2"
                                );
                            }

                            BohMod.queueServerWork(
                                10,
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
                                            "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 0"
                                        );
                                    }
                                }
                            );
                        }
                    );
                }
            }
        }
    }
}
