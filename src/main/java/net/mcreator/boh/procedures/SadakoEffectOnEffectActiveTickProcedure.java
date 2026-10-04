package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SadakoEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SadakoEffectOnEffectActiveTickProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.01) {
                M.hurt(
                    entity,
                    M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                    2.0F
                );
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, SadakoEntity.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true)) && Math.random() < 0.01) {
                M.hurt(
                    entity,
                    M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                    6.0F
                );
            }

            if ((entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F) <= 5.0F
                && entity instanceof EntityLivingBase _entity
                && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 60, 1, false, false));
            }

            if ((entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F) < 2.0F) {
                if (!M.getBoolean(M.getPersistentData(entity), "sound_kill")) {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_kill")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_kill")),
                                SoundSource.AMBIENT,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    M.putBoolean(M.getPersistentData(entity), "sound_kill", true);
                }

                M.hurt(
                    entity,
                    M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                    99.0F
                );
            }
        }
    }
}
