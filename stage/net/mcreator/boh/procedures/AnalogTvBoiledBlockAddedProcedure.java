package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class AnalogTvBoiledBlockAddedProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (world instanceof World _level) {
            if (!M.isClientSide(_level)) {
                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_boiled")), SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_boiled")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
        BohMod.queueServerWork(600, () -> {
            if (world instanceof World _levelx) {
                if (!M.isClientSide(_levelx)) {
                    M.playSound(_levelx, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_off")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_levelx, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_off")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            BlockPos _bp = BlockPos.containing(x, y, z);
            BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.ANALOG_TELEVISION.get()));
            BlockState _bso = M.getBlockState(world, _bp);
            UnmodifiableIterator var10 = M.getValues(_bso).entrySet().iterator();
            while (var10.hasNext()) {
                Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var10.next();
                Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                if (_property != null && _bs.getValue(_property) != null) {
                    try {
                        _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                    } catch (Exception var14) {
                    }
                }
            }
            M.setBlock(world, _bp, _bs, 3);
        });
        BohMod.queueServerWork(700, () -> {
            if (world instanceof WorldServer _levelx) {
                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.BOILED_ONE.get()), _levelx, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                }
            }
        });
    }
}
