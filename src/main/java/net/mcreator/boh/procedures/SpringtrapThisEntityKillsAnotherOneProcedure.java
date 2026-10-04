package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SpringtrapThisEntityKillsAnotherOneProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
                    SoundSource.HOSTILE,
                    2.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world,
                    x,
                    y,
                    z,
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
                    SoundSource.HOSTILE,
                    2.0F,
                    1.0F,
                    false
                );
            }
        }

        BohMod.queueServerWork(
            30,
            () -> {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_kill")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_kill")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }
            }
        );
    }
}
