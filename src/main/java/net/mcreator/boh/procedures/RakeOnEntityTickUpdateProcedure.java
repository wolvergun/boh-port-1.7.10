package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RakeOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "lines_rake")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_rake", true);
            }

            if (!M.getBoolean(M.getPersistentData(entity), "trigger_rake") && M.getBoolean(M.getPersistentData(entity), "lines_rake")) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rake_scream")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rake_scream")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "trigger_rake", true);
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_rake", false);
                M.putBoolean(M.getPersistentData(entity), "trigger_rake", false);
            }
        }
    }
}
