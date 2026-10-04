package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.HerobrineEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.M;

public class HerobrineOnEntityTickUpdateProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof HerobrineEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), HerobrineEntity.DATA_timer, (entity instanceof HerobrineEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) + 1);
            }
            if ((entity instanceof HerobrineEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) == 3) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @e[type=boh:herobrine] run playsound minecraft:ambient.cave hostile @a");
                }
                if (Math.random() < 0.7) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }
                    _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @e[type=boh:herobrine] run playsound minecraft:entity.ghast.hurt hostile @a");
                    }
                }
            }
            if ((entity instanceof HerobrineEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) == 20) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/spreadplayers ~ ~ 5 5 false @e[type=boh:herobrine,limit=1,distance=0..2]");
                }
            }
            if ((entity instanceof HerobrineEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(HerobrineEntity.DATA_timer) : 0) == 60) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @e[type=boh:herobrine] run particle minecraft:large_smoke ~ ~1 ~ 0.2 0.5 0.2 0 100 force");
                }
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            }
        }
    }
}
