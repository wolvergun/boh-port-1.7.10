package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.M;

public class VitaCrawlOnTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, BlockState blockstate) {
        int _value = (M.getProperty(M.getStateDefinition(M.getBlock(blockstate)), "timer_grow") instanceof IntegerProperty _getip1 ? (Integer) blockstate.getValue(_getip1) : -1) + 1;
        BlockPos _pos = BlockPos.containing(x, y, z);
        BlockState _bs = M.getBlockState(world, _pos);
        if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "timer_grow") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _value)) {
            M.setBlock(world, _pos, (BlockState) M.setValue(_bs, _integerProp, _value), 3);
        }
        if ((M.getProperty(M.getStateDefinition(M.getBlock(blockstate)), "timer_grow") instanceof IntegerProperty _getip4 ? (Integer) blockstate.getValue(_getip4) : -1) == 1000) {
            M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(Blocks.AIR), 3);
            if (Math.random() < 0.7) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.VITA_MIMIC.get()), _level, BlockPos.containing(x + 0.5, y + 1.0, z + 0.5), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.nextFloat(M.getRandom(world)) * 360.0F);
                    }
                }
            } else if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.TRIMMING.get()), _level, BlockPos.containing(x + 0.5, y + 1.0, z + 0.5), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setYRot(entityToSpawn, M.nextFloat(M.getRandom(world)) * 360.0F);
                }
            }
        }
    }
}
