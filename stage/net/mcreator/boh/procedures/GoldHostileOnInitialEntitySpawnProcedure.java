package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class GoldHostileOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.CELEBI.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
            }
        }
    }
}
