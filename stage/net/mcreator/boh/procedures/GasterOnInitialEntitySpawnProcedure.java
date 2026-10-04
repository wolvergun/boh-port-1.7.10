package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class GasterOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z) {
        BohModVariables.MapVariables.get(world).spawn_gaster = 1.0;
        BohModVariables.MapVariables.get(world).syncData(world);
        if (world instanceof World _level) {
            if (!M.isClientSide(_level)) {
                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")), SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
            }
        }
    }
}
