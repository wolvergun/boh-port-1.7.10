package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class OvamorphOpenUpdateTickProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (!(new Object() {
            public boolean getValue(World world, BlockPos pos, String tag) {
                TileEntity blockEntity = M.getBlockEntity(world, pos);
                return blockEntity != null ? M.getBoolean(M.getPersistentData(blockEntity), tag) : false;
            }
        }).getValue(world, BlockPos.containing(x, y, z), "change2")) {
            if (!(new Object() {
                public boolean getValue(World world, BlockPos pos, String tag) {
                    TileEntity blockEntity = M.getBlockEntity(world, pos);
                    return blockEntity != null ? M.getBoolean(M.getPersistentData(blockEntity), tag) : false;
                }
            }).getValue(world, BlockPos.containing(x, y, z), "change")) {
                if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 11.0, 11.0, 11.0), e -> true))) {
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(BohModBlocks.OVAMORPH.get());
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator _level = M.getValues(_bso).entrySet().iterator();

                    while (_level.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)_level.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var14) {
                            }
                        }
                    }

                    M.setBlock(world, _bp, _bs, 3);
                }

                if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 5.0, 5.0, 5.0), e -> true))
                    && !M.isClientSide(world)) {
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    TileEntity _blockEntity = M.getBlockEntity(world, _bp);
                    BlockState _bs = M.getBlockState(world, _bp);
                    if (_blockEntity != null) {
                        M.putBoolean(M.getPersistentData(_blockEntity), "change", true);
                    }

                    if (world instanceof World) {
                        M.sendBlockUpdated(world, _bp, _bs, _bs, 3);
                    }
                }
            }

            if ((new Object() {
                public boolean getValue(World world, BlockPos pos, String tag) {
                    TileEntity blockEntity = M.getBlockEntity(world, pos);
                    return blockEntity != null ? M.getBoolean(M.getPersistentData(blockEntity), tag) : false;
                }
            }).getValue(world, BlockPos.containing(x, y, z), "change")) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.FACEHUGGER.get(), _level, BlockPos.containing(x + 0.5, y + 2.0, z + 0.5), MobSpawnType.MOB_SUMMONED
                    );
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                if (!M.isClientSide(world)) {
                    BlockPos _bpx = BlockPos.containing(x, y, z);
                    TileEntity _blockEntityx = M.getBlockEntity(world, _bpx);
                    BlockState _bsx = M.getBlockState(world, _bpx);
                    if (_blockEntityx != null) {
                        M.putBoolean(M.getPersistentData(_blockEntityx), "change2", true);
                    }

                    if (world instanceof World) {
                        M.sendBlockUpdated(world, _bpx, _bsx, _bsx, 3);
                    }
                }
            }
        }
    }
}
