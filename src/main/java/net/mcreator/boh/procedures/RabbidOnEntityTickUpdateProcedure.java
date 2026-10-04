package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.RabbidEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RabbidOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!(world instanceof World) || !M.isDay(world)) {
                M.setShiftKeyDown(entity, true);
                M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                }
            }

            if ((!(world instanceof World) || !M.isDay(world)) && M.getDouble(M.getPersistentData(entity), "state_ai") == 0.0 && entity instanceof RabbidEntity
                )
             {
                ((RabbidEntity)entity).setAnimation("sleep");
            }

            if (world instanceof World && M.isDay(world)) {
                M.setShiftKeyDown(entity, false);
            }

            if (world instanceof World && M.isDay(world)) {
                if (Math.random() < 0.12 && Math.random() < 0.012) {
                    M.putBoolean(M.getPersistentData(entity), "scream", true);
                }

                if (M.getBoolean(M.getPersistentData(entity), "scream")) {
                    M.putBoolean(M.getPersistentData(entity), "scream_sound", true);
                    M.putBoolean(M.getPersistentData(entity), "scream", false);
                    if (entity instanceof RabbidEntity) {
                        ((RabbidEntity)entity).setAnimation("scream");
                    }

                    if (entity instanceof RabbidEntity animatable) {
                        animatable.setTexture("rabbids_scream");
                    }

                    BohMod.queueServerWork(10, () -> {
                        if (entity instanceof RabbidEntity animatable) {
                            animatable.setTexture("rabbids");
                        }
                    });
                }

                if (M.getBoolean(M.getPersistentData(entity), "scream")) {
                    M.makeStuckInBlock(entity, M.defaultBlockState(Blocks.AIR), new Vec3(0.25, 0.05, 0.25));
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                    }
                }

                if (M.getBoolean(M.getPersistentData(entity), "scream_sound")) {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rabbids")),
                                SoundSource.AMBIENT,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rabbids")),
                                SoundSource.AMBIENT,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    M.putBoolean(M.getPersistentData(entity), "scream_sound", false);
                }
            }
        }
    }
}
