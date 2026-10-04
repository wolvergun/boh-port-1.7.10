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

public class MichaelDaviesOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "lines_michael")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", true);
            }

            if (!M.getBoolean(M.getPersistentData(entity), "throlgular") && M.getBoolean(M.getPersistentData(entity), "lines_michael")) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:michael_spotted")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:michael_spotted")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "throlgular", true);
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", false);
                M.putBoolean(M.getPersistentData(entity), "throlgular", false);
            }
        }
    }
}
