package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class HolyFireBlockAddedProcedure {

    public static void execute(World world, double x, double y, double z) {
        BohMod.queueServerWork(40, () -> {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundSource.HOSTILE, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.extinguish_fire")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
            M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(Blocks.AIR), 3);
        });
    }
}
