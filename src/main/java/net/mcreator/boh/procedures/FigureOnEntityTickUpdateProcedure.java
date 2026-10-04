package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FigureOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof EntityPlayer
                    && !M.isShiftKeyDown(entityiterator)
                    && !(entity instanceof EntityLiving _mob && M.isAggressive(_mob))
                    && entity instanceof EntityLiving _entity) {
                    M.moveTo(M.getNavigation(_entity), M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator), 1.0);
                }
            }

            Vec3 _center_r24 = new Vec3(x, y, z);

            for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r24, _center_r24).inflate(2.5), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r24)))
                .toList()) {
                if (entityiteratorx instanceof EntityPlayer
                    && !(entity instanceof EntityLiving _mob && M.isAggressive(_mob))
                    && entity instanceof EntityLiving _entity
                    && entityiteratorx instanceof EntityLivingBase _ent) {
                    M.setTarget(_entity, _ent);
                }
            }

            if (!(entity instanceof EntityLiving _mob && M.isAggressive(_mob)) && Math.random() < 0.1 && Math.random() < 0.05 && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_idle")),
                        SoundSource.NEUTRAL,
                        (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_idle")),
                        SoundSource.NEUTRAL,
                        (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                        1.0F,
                        false
                    );
                }
            }

            if (entity instanceof EntityLiving _mobx && M.isAggressive(_mobx) && Math.random() < 0.1 && Math.random() < 0.05 && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_aggro")),
                        SoundSource.NEUTRAL,
                        (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_aggro")),
                        SoundSource.NEUTRAL,
                        (float)Mth.nextDouble(RandomSource.create(), 0.1, 1.5),
                        1.0F,
                        false
                    );
                }
            }

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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_scream")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_scream")),
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

            if (M.getDeltaMovement(entity).horizontalDistanceSqr() > 1.0E-6) {
                M.putDouble(M.getPersistentData(entity), "timer_step", M.getDouble(M.getPersistentData(entity), "timer_step") + 1.0);
            } else {
                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }

            if (entity instanceof EntityLiving _mobxx && M.isAggressive(_mobxx)) {
                if (entity instanceof EntityLiving _mobxxx && M.isAggressive(_mobxxx) && M.getDouble(M.getPersistentData(entity), "timer_step") == 7.0) {
                    if (!M.isClientSide(world) && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
                }
            } else if (M.getDouble(M.getPersistentData(entity), "timer_step") == 12.0) {
                if (!M.isClientSide(world) && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:figure_step")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "timer_step", 0.0);
            }

            if (entity instanceof EntityLiving _mobxxx
                && M.isAggressive(_mobxxx)
                && !(entity instanceof EntityLivingBase _livEnt45 && M.hasEffect(_livEnt45, MobEffects.SATURATION))
                && Math.random() < 0.02
                && Math.random() < 0.02
                && entity instanceof EntityLivingBase _entity
                && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.SATURATION, 30, 0, false, false));
            }
        }
    }
}
