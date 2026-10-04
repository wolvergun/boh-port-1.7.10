package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GasterOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z) {
        BohModVariables.MapVariables.get(world).spawn_gaster = 1.0;
        BohModVariables.MapVariables.get(world).syncData(world);
        if (world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                );
            }
        }
    }
}
