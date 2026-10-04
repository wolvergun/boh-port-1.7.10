package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.entity.SonicExeEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SonicExeOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(500, () -> {
                if (Math.random() < 0.33 && world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(BohModEntities.TAILS.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
            });
            BohMod.queueServerWork(
                1,
                () -> {
                    if (Math.random() < 0.3) {
                        if (entity instanceof SonicExeEntity animatable) {
                            animatable.setTexture("lordx");
                        }

                        M.setCustomName(entity, Component.literal("Lord X"));
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
                                "/attribute @s forge:nametag_distance base set 0"
                            );
                        }
                    }
                }
            );
        }
    }
}
