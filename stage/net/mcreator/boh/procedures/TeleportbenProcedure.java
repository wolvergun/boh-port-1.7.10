package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.BenDrownedEntity;
import net.minecraft.client.Minecraft;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

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
        if (entity != null) {
            if ((new Object() {

                public boolean checkGamemode(Entity _ent) {
                    if (_ent instanceof EntityPlayerMP _serverPlayer) {
                        return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.SURVIVAL;
                    } else {
                        return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.SURVIVAL : false;
                    }
                }
            }).checkGamemode(entity) && M.isEmpty(M.getEntitiesOfClass(world, BenDrownedEntity.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true))) {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof BenDrownedEntity && !M.getBoolean(M.getPersistentData(entityiterator), "inWater") && Math.random() < 0.1 && Math.random() < 0.1) {
                        Entity _ent = entity;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 1");
                        }
                        BohMod.queueServerWork(10, () -> {
                            Entity _entx = entity;
                            if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/execute at @p[gamemode=survival] rotated ~ 1 run spreadplayers ~ ~ 1 2 false @e[type=boh:ben_drowned,limit=1]");
                            }
                            Entity _entx_r60 = entity;
                            if (!M.isClientSide(M.level(_entx_r60)) && M.getServer(_entx_r60) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r60)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r60), M.getRotationVector(_entx_r60), M.level(_entx_r60) instanceof WorldServer ? (WorldServer) M.level(_entx_r60) : null, 4, M.getString(M.getName(_entx_r60)), M.getDisplayName(_entx_r60), M.getServer(M.level(_entx_r60)), _entx_r60), "/execute at @e[type=boh:ben_drowned,distance=0..5] run playsound boh:ben_laughing hostile @p ~ ~ ~");
                            }
                            Entity _entx_r61 = entity;
                            if (!M.isClientSide(M.level(_entx_r61)) && M.getServer(_entx_r61) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r61)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r61), M.getRotationVector(_entx_r61), M.level(_entx_r61) instanceof WorldServer ? (WorldServer) M.level(_entx_r61) : null, 4, M.getString(M.getName(_entx_r61)), M.getDisplayName(_entx_r61), M.getServer(M.level(_entx_r61)), _entx_r61), "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 2");
                            }
                            BohMod.queueServerWork(10, () -> {
                                Entity _entxx = entity;
                                if (!M.isClientSide(M.level(_entxx)) && M.getServer(_entxx) != null) {
                                    M.performPrefixedCommand(M.getCommands(M.getServer(_entxx)), new CommandSourceStack(CommandSource.NULL, M.position(_entxx), M.getRotationVector(_entxx), M.level(_entxx) instanceof WorldServer ? (WorldServer) M.level(_entxx) : null, 4, M.getString(M.getName(_entxx)), M.getDisplayName(_entxx), M.getServer(M.level(_entxx)), _entxx), "/scoreboard players set @e[type=boh:ben_drowned,distance=0..20,limit=1] anim 0");
                                }
                            });
                        });
                    }
                }
            }
        }
    }
}
