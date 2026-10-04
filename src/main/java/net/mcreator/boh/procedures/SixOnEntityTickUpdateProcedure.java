package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SixOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "lines_six")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_six", true);
            }

            if (!M.getBoolean(M.getPersistentData(entity), "trigger_six") && M.getBoolean(M.getPersistentData(entity), "lines_six")) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:six_oye")),
                            SoundSource.HOSTILE,
                            2.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:six_oye")), SoundSource.HOSTILE, 2.0F, 1.0F, false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "trigger_six", true);
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_six", false);
                M.putBoolean(M.getPersistentData(entity), "trigger_six", false);
            }

            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                EntityLivingBase var11 = entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null;
                if (var11 instanceof EntityLivingBase && !M.isClientSide(M.level(var11))) {
                    M.addEffect(var11, M.new_PotionEffect(BohModMobEffects.MAD.get(), 1200, 0, false, false));
                }
            }
        }
    }
}
