package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.ParticleTypes;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HolyFireOnTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (Math.random() < 0.2) {
            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, ParticleTypes.LARGE_SMOKE, x + 0.5, y, z + 0.5, 2, 0.2, 0.0, 0.2, 0.01);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.ambient")),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.ambient")),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }
        }

        if (!M.canSurvive(M.defaultBlockState(Blocks.FIRE), world, BlockPos.containing(x, y, z))) {
            BlockPos _bp = BlockPos.containing(x, y, z);
            BlockState _bs = M.defaultBlockState(Blocks.AIR);
            BlockState _bso = M.getBlockState(world, _bp);
            UnmodifiableIterator var10 = M.getValues(_bso).entrySet().iterator();

            while (var10.hasNext()) {
                Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var10.next();
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
    }
}
