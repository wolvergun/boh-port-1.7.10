package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class GoldHostileEntityDiesProcedure {

    public static void execute(World world, double x, double y, double z) {
        BohModVariables.MapVariables.get(world).spawn_gold = 0.0;
        BohModVariables.MapVariables.get(world).syncData(world);
        if (world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_4.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
            }
        }
        if (world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_3.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
            }
        }
        if (world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_1.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
            }
        }
        if (world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.UNOWN_1.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
            }
        }
    }
}
