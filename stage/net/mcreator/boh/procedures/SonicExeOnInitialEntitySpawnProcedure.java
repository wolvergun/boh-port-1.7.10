package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SonicExeEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class SonicExeOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(500, () -> {
                if (Math.random() < 0.33 && world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.TAILS.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
            });
            BohMod.queueServerWork(1, () -> {
                if (Math.random() < 0.3) {
                    if (entity instanceof SonicExeEntity animatable) {
                        animatable.setTexture("lordx");
                    }
                    M.setCustomName(entity, Component.literal("Lord X"));
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/attribute @s forge:nametag_distance base set 0");
                    }
                }
            });
        }
    }
}
