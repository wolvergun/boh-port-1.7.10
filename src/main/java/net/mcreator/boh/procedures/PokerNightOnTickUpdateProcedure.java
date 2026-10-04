package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PokerNightOnTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z) {
        if ((new Object() {
            public double getValue(World world, BlockPos pos, String tag) {
                TileEntity blockEntity = M.getBlockEntity(world, pos);
                return blockEntity != null ? M.getDouble(M.getPersistentData(blockEntity), tag) : -1.0;
            }
        }).getValue(world, BlockPos.containing(x, y, z), "timer_song") == 1.0 && world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:pasta_night")),
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:pasta_night")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                );
            }
        }

        if (!M.isClientSide(world)) {
            BlockPos _bp = BlockPos.containing(x, y, z);
            TileEntity _blockEntity = M.getBlockEntity(world, _bp);
            BlockState _bs = M.getBlockState(world, _bp);
            if (_blockEntity != null) {
                M.putDouble(M.getPersistentData(_blockEntity), "timer_song", (new Object() {
                    public double getValue(World world, BlockPos pos, String tag) {
                        TileEntity blockEntity = M.getBlockEntity(world, pos);
                        return blockEntity != null ? M.getDouble(M.getPersistentData(blockEntity), tag) : -1.0;
                    }
                }).getValue(world, BlockPos.containing(x, y, z), "timer_song") + 1.0);
            }

            if (world instanceof World) {
                M.sendBlockUpdated(world, _bp, _bs, _bs, 3);
            }
        }

        if (!M.isClientSide(world)) {
            BlockPos _bpx = BlockPos.containing(x, y, z);
            TileEntity _blockEntityx = M.getBlockEntity(world, _bpx);
            BlockState _bsx = M.getBlockState(world, _bpx);
            if (_blockEntityx != null) {
                M.putDouble(M.getPersistentData(_blockEntityx), "timer_pow", (new Object() {
                    public double getValue(World world, BlockPos pos, String tag) {
                        TileEntity blockEntity = M.getBlockEntity(world, pos);
                        return blockEntity != null ? M.getDouble(M.getPersistentData(blockEntity), tag) : -1.0;
                    }
                }).getValue(world, BlockPos.containing(x, y, z), "timer_pow") + 1.0);
            }

            if (world instanceof World) {
                M.sendBlockUpdated(world, _bpx, _bsx, _bsx, 3);
            }
        }

        if ((new Object() {
            public double getValue(World world, BlockPos pos, String tag) {
                TileEntity blockEntity = M.getBlockEntity(world, pos);
                return blockEntity != null ? M.getDouble(M.getPersistentData(blockEntity), tag) : -1.0;
            }
        }).getValue(world, BlockPos.containing(x, y, z), "timer_song") == 3286.0 && !M.isClientSide(world)) {
            BlockPos _bpxx = BlockPos.containing(x, y, z);
            TileEntity _blockEntityxx = M.getBlockEntity(world, _bpxx);
            BlockState _bsxx = M.getBlockState(world, _bpxx);
            if (_blockEntityxx != null) {
                M.putDouble(M.getPersistentData(_blockEntityxx), "timer_song", 0.0);
            }

            if (world instanceof World) {
                M.sendBlockUpdated(world, _bpxx, _bsxx, _bsxx, 3);
            }
        }

        if ((new Object() {
            public double getValue(World world, BlockPos pos, String tag) {
                TileEntity blockEntity = M.getBlockEntity(world, pos);
                return blockEntity != null ? M.getDouble(M.getPersistentData(blockEntity), tag) : -1.0;
            }
        }).getValue(world, BlockPos.containing(x, y, z), "timer_pow") == 20.0) {
            if (!M.isClientSide(world)) {
                BlockPos _bpxxx = BlockPos.containing(x, y, z);
                TileEntity _blockEntityxxx = M.getBlockEntity(world, _bpxxx);
                BlockState _bsxxx = M.getBlockState(world, _bpxxx);
                if (_blockEntityxxx != null) {
                    M.putDouble(M.getPersistentData(_blockEntityxxx), "timer_pow", 0.0);
                }

                if (world instanceof World) {
                    M.sendBlockUpdated(world, _bpxxx, _bsxxx, _bsxxx, 3);
                }
            }

            if (Math.random() < 0.1) {
                int _value = 1;
                BlockPos _pos = BlockPos.containing(x, y, z);
                BlockState _bsxxxx = M.getBlockState(world, _pos);
                if (M.getProperty(M.getStateDefinition(M.getBlock(_bsxxxx)), "animation") instanceof IntegerProperty _integerProp
                    && M.contains(M.getPossibleValues(_integerProp), _value)) {
                    M.setBlock(world, _pos, M.setValue(_bsxxxx, _integerProp, _value), 3);
                }

                BohMod.queueServerWork(
                    10,
                    () -> {
                        if (world instanceof World) {
                            if (!M.isClientSide(world)) {
                                M.playSound(
                                    world,
                                    null,
                                    BlockPos.containing(x, y, z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                                    SoundSource.BLOCKS,
                                    0.4F,
                                    1.0F
                                );
                            } else {
                                M.playLocalSound(
                                    world,
                                    x,
                                    y,
                                    z,
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                                    SoundSource.BLOCKS,
                                    0.4F,
                                    1.0F,
                                    false
                                );
                            }
                        }
                    }
                );
                BohMod.queueServerWork(20, () -> {
                    BlockPos _posx = BlockPos.containing(x, y, z);
                    BlockState _bsxxxxx = M.getBlockState(world, _posx);
                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bsxxxxx)), "animation") instanceof IntegerProperty _integerPropx) {
                        M.setBlock(world, _posx, M.setValue(_bsxxxxx, _integerPropx, 0), 3);
                    }
                });
            }
        }
    }
}
