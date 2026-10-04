package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public class AnalogTVSadakoBlockAddedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      if ((new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.getValue(_dp);
                  } else {
                     return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Axis
                        ? Direction.fromAxisAndDirection((Axis)_bs.getValue(_ep), AxisDirection.POSITIVE)
                        : Direction.NORTH;
                  }
               }
            })
            .getDirection(blockstate)
         == Direction.SOUTH) {
         BohMod.queueServerWork(2400, () -> {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.SADAKO.get()).spawn(_level, BlockPos.containing(x, y, z + 1.0), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(20, () -> {
               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.ANALOG_TELEVISION.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var10 = _bso.getValues().entrySet().iterator();

               while (var10.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var10.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var14) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            });
         });
      } else if ((new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.getValue(_dp);
                  } else {
                     return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Axis
                        ? Direction.fromAxisAndDirection((Axis)_bs.getValue(_ep), AxisDirection.POSITIVE)
                        : Direction.NORTH;
                  }
               }
            })
            .getDirection(blockstate)
         == Direction.NORTH) {
         BohMod.queueServerWork(2400, () -> {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.SADAKO.get()).spawn(_level, BlockPos.containing(x, y, z - 1.0), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(20, () -> {
               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.ANALOG_TELEVISION.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var10 = _bso.getValues().entrySet().iterator();

               while (var10.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var10.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var14) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            });
         });
      } else if ((new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.getValue(_dp);
                  } else {
                     return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Axis
                        ? Direction.fromAxisAndDirection((Axis)_bs.getValue(_ep), AxisDirection.POSITIVE)
                        : Direction.NORTH;
                  }
               }
            })
            .getDirection(blockstate)
         == Direction.WEST) {
         BohMod.queueServerWork(2400, () -> {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.SADAKO.get()).spawn(_level, BlockPos.containing(x - 1.0, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(20, () -> {
               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.ANALOG_TELEVISION.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var10 = _bso.getValues().entrySet().iterator();

               while (var10.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var10.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var14) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            });
         });
      } else if ((new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.getValue(_dp);
                  } else {
                     return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Axis
                        ? Direction.fromAxisAndDirection((Axis)_bs.getValue(_ep), AxisDirection.POSITIVE)
                        : Direction.NORTH;
                  }
               }
            })
            .getDirection(blockstate)
         == Direction.EAST) {
         BohMod.queueServerWork(2400, () -> {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.SADAKO.get()).spawn(_level, BlockPos.containing(x + 1.0, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(20, () -> {
               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.ANALOG_TELEVISION.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var10 = _bso.getValues().entrySet().iterator();

               while (var10.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var10.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var14) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            });
         });
      }

      BohMod.queueServerWork(
         2300,
         () -> {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_24")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_24")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                  );
               }
            }
         }
      );
   }
}
