package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TinkyWinkyOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (!(entity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.SATURATION))
                    && Math.random() < 0.02
                    && Math.random() < 0.02
                    && entity instanceof EntityLivingBase _entity
                    && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.SATURATION, 30, 0, false, false));
                }

                if (Math.random() < 0.2 && Math.random() < 0.12 && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tinkywinky_chase")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tinkywinky_chase")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }
            }

            if (!(entity instanceof EntityLiving _mob && M.isAggressive(_mob))
                && Math.random() < 0.01
                && Math.random() < 0.005
                && M.isEmptyBlock(world, BlockPos.containing(x, y, z))) {
                M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(BohModBlocks.TUBBY_CUSTARD.get()), 3);
            }
        }
    }
}
