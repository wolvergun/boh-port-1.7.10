package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PokerNightBlockDestroyedByPlayerProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (world instanceof WorldServer _level) {
            M.performPrefixedCommand(
                M.getCommands(M.getServer(_level)),
                M.withSuppressedOutput(
                    new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)
                ),
                "/stopsound @a block boh:pasta_night"
            );
        }

        if (Math.random() < 0.5) {
            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(BohModEntities.HYPNO.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                }
            }
        } else if (Math.random() < 0.5) {
            if (world instanceof WorldServer _levelx) {
                Entity entityToSpawn = M.spawn(BohModEntities.MX.get(), _levelx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                }
            }
        } else if (world instanceof WorldServer _levelxx) {
            Entity entityToSpawn = M.spawn(BohModEntities.SONIC_EXE.get(), _levelxx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
            }
        }
    }
}
