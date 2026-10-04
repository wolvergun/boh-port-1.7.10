package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class OvamorphUpdateTickProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true))) {
            BlockPos _bp = BlockPos.containing(x, y, z);
            BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.OVAMORPH_OPEN.get()));
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
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ovamorph_open")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ovamorph_open")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
