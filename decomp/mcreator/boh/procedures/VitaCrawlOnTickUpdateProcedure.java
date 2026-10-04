package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class VitaCrawlOnTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      int _value = (blockstate.getBlock().getStateDefinition().getProperty("timer_grow") instanceof IntegerProperty _getip1 ? (Integer)blockstate.getValue(_getip1) : -1)
         + 1;
      BlockPos _pos = BlockPos.containing(x, y, z);
      BlockState _bs = world.getBlockState(_pos);
      if (_bs.getBlock().getStateDefinition().getProperty("timer_grow") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value)) {
         world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
      }

      if ((blockstate.getBlock().getStateDefinition().getProperty("timer_grow") instanceof IntegerProperty _getip4 ? (Integer)blockstate.getValue(_getip4) : -1) == 1000) {
         world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
         if (Math.random() < 0.7) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.VITA_MIMIC.get())
                  .spawn(_level, BlockPos.containing(x + 0.5, y + 1.0, z + 0.5), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setYRot(world.getRandom().nextFloat() * 360.0F);
               }
            }
         } else if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = ((EntityType)BohModEntities.TRIMMING.get())
               .spawn(_level, BlockPos.containing(x + 0.5, y + 1.0, z + 0.5), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
               entityToSpawn.setYRot(world.getRandom().nextFloat() * 360.0F);
            }
         }
      }
   }
}
