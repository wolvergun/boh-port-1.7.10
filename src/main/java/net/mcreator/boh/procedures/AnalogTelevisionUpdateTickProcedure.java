package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AnalogTelevisionUpdateTickProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (Math.random() < 0.5) {
            if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_BILL.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var10 = M.getValues(_bso).entrySet().iterator();

                while (var10.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var10.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var27) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_BILLY.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var81 = M.getValues(_bso).entrySet().iterator();

                while (var81.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var81.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var26) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_COURAGE.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var82 = M.getValues(_bso).entrySet().iterator();

                while (var82.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var82.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var25) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_COVE.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var83 = M.getValues(_bso).entrySet().iterator();

                while (var83.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var83.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var24) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_FLESHPIT.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var84 = M.getValues(_bso).entrySet().iterator();

                while (var84.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var84.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var23) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_GEMINI.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var85 = M.getValues(_bso).entrySet().iterator();

                while (var85.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var85.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var22) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_LOCAL_58.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var86 = M.getValues(_bso).entrySet().iterator();

                while (var86.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var86.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var21) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_MAX.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var87 = M.getValues(_bso).entrySet().iterator();

                while (var87.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var87.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var20) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_NEEDLE.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var88 = M.getValues(_bso).entrySet().iterator();

                while (var88.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var88.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var19) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_NO_MORE.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var89 = M.getValues(_bso).entrySet().iterator();

                while (var89.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var89.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var18) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_OPERATOR.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var90 = M.getValues(_bso).entrySet().iterator();

                while (var90.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var90.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var17) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            } else if (Math.random() < 0.035) {
                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_SHAMROCK.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var91 = M.getValues(_bso).entrySet().iterator();

                while (var91.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var91.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var16) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }
            } else if (Math.random() < 0.035) {
                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_WYOMING.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var92 = M.getValues(_bso).entrySet().iterator();

                while (var92.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var92.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var15) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }
            } else if (Math.random() < 0.005) {
                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.ANALOG_TV_BOILED.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var93 = M.getValues(_bso).entrySet().iterator();

                while (var93.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var93.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var14) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")),
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false
                        );
                    }
                }
            }
        }
    }
}
