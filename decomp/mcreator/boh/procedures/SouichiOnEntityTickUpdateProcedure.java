package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SouichiEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SouichiOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.1) {
            Entity _ent = entity;
            if (!_ent.level().isClientSide() && _ent.getServer() != null) {
               _ent.getServer()
                  .getCommands()
                  .performPrefixedCommand(
                     new CommandSourceStack(
                        CommandSource.NULL,
                        _ent.position(),
                        _ent.getRotationVector(),
                        _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                        4,
                        _ent.getName().getString(),
                        _ent.getDisplayName(),
                        _ent.level().getServer(),
                        _ent
                     ),
                     "/execute as @s anchored eyes run particle minecraft:flame ^-.2 ^0.7 ^.4"
                  );
            }
         }

         if (Math.random() < 0.1) {
            Entity _ent = entity;
            if (!_ent.level().isClientSide() && _ent.getServer() != null) {
               _ent.getServer()
                  .getCommands()
                  .performPrefixedCommand(
                     new CommandSourceStack(
                        CommandSource.NULL,
                        _ent.position(),
                        _ent.getRotationVector(),
                        _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                        4,
                        _ent.getName().getString(),
                        _ent.getDisplayName(),
                        _ent.level().getServer(),
                        _ent
                     ),
                     "/execute as @s anchored eyes run particle minecraft:flame ^.2 ^0.7 ^.4"
                  );
            }
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12.5), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true).isEmpty()
               && entityiterator instanceof Player
               && Math.random() < 0.025
               && world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() != BohModBlocks.VOODOO_DOLL.get()) {
               if (world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.OAK_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.SPRUCE_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.BIRCH_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.JUNGLE_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.ACACIA_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.DARK_OAK_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.MANGROVE_LOG
                  && world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z)).getBlock() != Blocks.CHERRY_LOG) {
                  if (world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.OAK_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.SPRUCE_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.BIRCH_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.JUNGLE_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.ACACIA_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.DARK_OAK_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.MANGROVE_LOG
                     && world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z)).getBlock() != Blocks.CHERRY_LOG) {
                     if (world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.OAK_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.SPRUCE_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.BIRCH_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.JUNGLE_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.ACACIA_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.DARK_OAK_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.MANGROVE_LOG
                        && world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0)).getBlock() != Blocks.CHERRY_LOG) {
                        if (world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.OAK_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.SPRUCE_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.BIRCH_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.JUNGLE_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.ACACIA_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.DARK_OAK_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.MANGROVE_LOG
                           || world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0)).getBlock() == Blocks.CHERRY_LOG) {
                           if (entity instanceof SouichiEntity) {
                              ((SouichiEntity)entity).setAnimation("hammer");
                           }

                           Entity _ent = entity;
                           _ent.teleportTo(x, y, z);
                           if (_ent instanceof ServerPlayer _serverPlayer) {
                              _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
                           }

                           entity.lookAt(Anchor.EYES, new Vec3(x, y + 1.0, z - 1.0));
                           if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                              _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                           }

                           if (world instanceof Level _level) {
                              if (!_level.isClientSide()) {
                                 _level.playSound(
                                    null,
                                    BlockPos.containing(x, y, z),
                                    (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                    SoundSource.BLOCKS,
                                    1.0F,
                                    1.0F
                                 );
                              } else {
                                 _level.playLocalSound(
                                    x,
                                    y,
                                    z,
                                    (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                    SoundSource.BLOCKS,
                                    1.0F,
                                    1.0F,
                                    false
                                 );
                              }
                           }

                           world.levelEvent(2001, BlockPos.containing(x, y + 1.0, z - 1.0), Block.getId(world.getBlockState(BlockPos.containing(x, y + 1.0, z - 1.0))));
                           if (world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() != BohModBlocks.VOODOO_DOLL.get()) {
                              world.setBlock(
                                 BlockPos.containing(x, y + 1.0, z),
                                 (new Object() {
                                       public BlockState with(BlockState _bs, Direction newValue) {
                                          if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue)
                                             )
                                           {
                                             return (BlockState)_bs.setValue(_dp, newValue);
                                          } else {
                                             return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep
                                                   && _ep.getPossibleValues().contains(newValue.getAxis())
                                                ? (BlockState)_bs.setValue(_ep, newValue.getAxis())
                                                : _bs;
                                          }
                                       }
                                    })
                                    .with(((Block)BohModBlocks.VOODOO_DOLL.get()).defaultBlockState(), Direction.SOUTH),
                                 3
                              );
                           }

                           if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                              _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0, false, false));
                           }
                        }
                     } else {
                        if (entity instanceof SouichiEntity) {
                           ((SouichiEntity)entity).setAnimation("hammer");
                        }

                        Entity _ent = entity;
                        _ent.teleportTo(x, y, z);
                        if (_ent instanceof ServerPlayer _serverPlayer) {
                           _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
                        }

                        entity.lookAt(Anchor.EYES, new Vec3(x, y + 1.0, z + 1.0));
                        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                           _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                        }

                        if (world instanceof Level _level) {
                           if (!_level.isClientSide()) {
                              _level.playSound(
                                 null,
                                 BlockPos.containing(x, y, z),
                                 (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                 SoundSource.BLOCKS,
                                 1.0F,
                                 1.0F
                              );
                           } else {
                              _level.playLocalSound(
                                 x,
                                 y,
                                 z,
                                 (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                 SoundSource.BLOCKS,
                                 1.0F,
                                 1.0F,
                                 false
                              );
                           }
                        }

                        world.levelEvent(2001, BlockPos.containing(x, y + 1.0, z + 1.0), Block.getId(world.getBlockState(BlockPos.containing(x, y + 1.0, z + 1.0))));
                        if (world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() != BohModBlocks.VOODOO_DOLL.get()) {
                           world.setBlock(
                              BlockPos.containing(x, y + 1.0, z),
                              (new Object() {
                                    public BlockState with(BlockState _bs, Direction newValue) {
                                       if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue)) {
                                          return (BlockState)_bs.setValue(_dp, newValue);
                                       } else {
                                          return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep
                                                && _ep.getPossibleValues().contains(newValue.getAxis())
                                             ? (BlockState)_bs.setValue(_ep, newValue.getAxis())
                                             : _bs;
                                       }
                                    }
                                 })
                                 .with(((Block)BohModBlocks.VOODOO_DOLL.get()).defaultBlockState(), Direction.NORTH),
                              3
                           );
                        }

                        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                           _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0, false, false));
                        }
                     }
                  } else {
                     if (entity instanceof SouichiEntity) {
                        ((SouichiEntity)entity).setAnimation("hammer");
                     }

                     Entity _ent = entity;
                     _ent.teleportTo(x, y, z);
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
                     }

                     entity.lookAt(Anchor.EYES, new Vec3(x - 1.0, y + 1.0, z));
                     if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                     }

                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                              SoundSource.BLOCKS,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                              SoundSource.BLOCKS,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }

                     world.levelEvent(2001, BlockPos.containing(x - 1.0, y + 1.0, z), Block.getId(world.getBlockState(BlockPos.containing(x - 1.0, y + 1.0, z))));
                     if (world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() != BohModBlocks.VOODOO_DOLL.get()) {
                        world.setBlock(
                           BlockPos.containing(x, y + 1.0, z),
                           (new Object() {
                                 public BlockState with(BlockState _bs, Direction newValue) {
                                    if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue)) {
                                       return (BlockState)_bs.setValue(_dp, newValue);
                                    } else {
                                       return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep
                                             && _ep.getPossibleValues().contains(newValue.getAxis())
                                          ? (BlockState)_bs.setValue(_ep, newValue.getAxis())
                                          : _bs;
                                    }
                                 }
                              })
                              .with(((Block)BohModBlocks.VOODOO_DOLL.get()).defaultBlockState(), Direction.EAST),
                           3
                        );
                     }

                     if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0, false, false));
                     }
                  }
               } else {
                  if (entity instanceof SouichiEntity) {
                     ((SouichiEntity)entity).setAnimation("hammer");
                  }

                  Entity _ent = entity;
                  _ent.teleportTo(x, y, z);
                  if (_ent instanceof ServerPlayer _serverPlayer) {
                     _serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
                  }

                  entity.lookAt(Anchor.EYES, new Vec3(x + 1.0, y + 1.0, z));
                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                           SoundSource.BLOCKS,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                           SoundSource.BLOCKS,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  world.levelEvent(2001, BlockPos.containing(x + 1.0, y + 1.0, z), Block.getId(world.getBlockState(BlockPos.containing(x + 1.0, y + 1.0, z))));
                  if (world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() != BohModBlocks.VOODOO_DOLL.get()) {
                     world.setBlock(
                        BlockPos.containing(x, y + 1.0, z),
                        (new Object() {
                              public BlockState with(BlockState _bs, Direction newValue) {
                                 if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue)) {
                                    return (BlockState)_bs.setValue(_dp, newValue);
                                 } else {
                                    return _bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep
                                          && _ep.getPossibleValues().contains(newValue.getAxis())
                                       ? (BlockState)_bs.setValue(_ep, newValue.getAxis())
                                       : _bs;
                                 }
                              }
                           })
                           .with(((Block)BohModBlocks.VOODOO_DOLL.get()).defaultBlockState(), Direction.WEST),
                        3
                     );
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0, false, false));
                  }
               }
            }
         }
      }
   }
}
