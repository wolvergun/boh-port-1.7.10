package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EngagedOnEffectActiveTickProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(300.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof SlenderManEntity) {
                    if ((
                                entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                                    ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                                    : 0
                            )
                            == 2
                        && entityiterator instanceof EntityLivingBase _entity
                        && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 60, 0, false, false));
                    }

                    if ((
                                entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                                    ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                                    : 0
                            )
                            == 4
                        && entityiterator instanceof EntityLivingBase _entity
                        && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
                    }

                    if ((
                                entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                                    ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                                    : 0
                            )
                            == 6
                        && entityiterator instanceof EntityLivingBase _entity
                        && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 60, 2, false, false));
                    }
                }
            }

            if (!M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true))
                && M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true))) {
                if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 1.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 2.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 3.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 4.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 5.0);
                }
            } else if (!M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true))) {
                if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 6.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 7.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 8.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 9.0);
                } else if (Math.random() < 0.1) {
                    M.putDouble(M.getPersistentData(entity), "static_slender", 10.0);
                }
            } else {
                M.putDouble(M.getPersistentData(entity), "static_slender", 0.0);
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true))) {
                M.putDouble(M.getPersistentData(entity), "static_slender", 0.0);
                if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                    M.performPrefixedCommand(
                        M.getCommands(M.getServer(entity)),
                        new CommandSourceStack(
                            CommandSource.NULL,
                            M.position(entity),
                            M.getRotationVector(entity),
                            M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                            4,
                            M.getString(M.getName(entity)),
                            M.getDisplayName(entity),
                            M.getServer(M.level(entity)),
                            entity
                        ),
                        "/spreadplayers ~ ~ 30 16 true @e[type=boh:slender_man]"
                    );
                }
            }
        }
    }
}
