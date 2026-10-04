package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SquidwardEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SquidwardOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && !(entity instanceof SquidwardEntity _datEntL0 && M.getEntityData(_datEntL0).get(SquidwardEntity.DATA_start))) {
            if (!(entity instanceof SquidwardEntity _datEntL1 && M.getEntityData(_datEntL1).get(SquidwardEntity.DATA_variant))
                && entity instanceof SquidwardEntity animatable) {
                animatable.setTexture("squidward");
            }

            if (entity instanceof SquidwardEntity _datEntL3
                && M.getEntityData(_datEntL3).get(SquidwardEntity.DATA_variant)
                && entity instanceof SquidwardEntity animatable) {
                animatable.setTexture("squidward_mist");
            }

            if (entity instanceof SquidwardEntity _datEntL5 && M.getEntityData(_datEntL5).get(SquidwardEntity.DATA_variant)) {
                if (entity instanceof SquidwardEntity _datEntSetI) {
                    M.set(
                        M.getEntityData(_datEntSetI),
                        SquidwardEntity.DATA_cooldown_squidward,
                        (entity instanceof SquidwardEntity _datEntI ? M.getEntityData(_datEntI).get(SquidwardEntity.DATA_cooldown_squidward) : 0) + 1
                    );
                }

                if ((entity instanceof SquidwardEntity _datEntI ? M.getEntityData(_datEntI).get(SquidwardEntity.DATA_cooldown_squidward) : 0) == 1
                    && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:squidward_redmist")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:squidward_redmist")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if ((entity instanceof SquidwardEntity _datEntIx ? M.getEntityData(_datEntIx).get(SquidwardEntity.DATA_cooldown_squidward) : 0) == 200
                    && entity instanceof SquidwardEntity _datEntSetI) {
                    M.set(M.getEntityData(_datEntSetI), SquidwardEntity.DATA_cooldown_squidward, 0);
                }
            }

            if (!(entity instanceof SquidwardEntity _datEntL12 && M.getEntityData(_datEntL12).get(SquidwardEntity.DATA_variant))
                && Math.random() < 0.005
                && entity instanceof SquidwardEntity _datEntSetL) {
                M.set(M.getEntityData(_datEntSetL), SquidwardEntity.DATA_variant, true);
            }

            if (entity instanceof SquidwardEntity _datEntL14
                && M.getEntityData(_datEntL14).get(SquidwardEntity.DATA_variant)
                && Math.random() < 0.005
                && entity instanceof SquidwardEntity _datEntSetL) {
                M.set(M.getEntityData(_datEntSetL), SquidwardEntity.DATA_variant, false);
            }

            if (entity instanceof SquidwardEntity _datEntL16 && M.getEntityData(_datEntL16).get(SquidwardEntity.DATA_variant)) {
                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(8.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.RED_MIST.get(), 60, 0, false, false));
                    }
                }
            }
        }
    }
}
