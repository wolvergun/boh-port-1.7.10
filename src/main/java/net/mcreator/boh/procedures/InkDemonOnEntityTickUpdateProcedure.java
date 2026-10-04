package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.InkDemonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class InkDemonOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
            if (!(entity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.SATURATION))
                && Math.random() < 0.02
                && Math.random() < 0.02
                && entity instanceof EntityLivingBase _entity
                && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.SATURATION, 30, 0, false, false));
            }

            EntityLivingBase _center = entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null;
            if (_center instanceof EntityLivingBase && !M.isClientSide(M.level(_center))) {
                M.addEffect(_center, M.new_PotionEffect(MobEffects.DARKNESS, 30, 0, false, false));
            }

            if (!(entity instanceof InkDemonEntity _datEntL5 && M.getEntityData(_datEntL5).get(InkDemonEntity.DATA_TeleportBendy))
                && M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))
                && Math.random() < 0.02) {
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
                        "/particle minecraft:squid_ink ~ ~ ~ 0.2 0.5 0.2 .1 20"
                    );
                }

                if (entity instanceof InkDemonEntity _datEntSetL) {
                    M.set(M.getEntityData(_datEntSetL), InkDemonEntity.DATA_TeleportBendy, true);
                }

                if (entity instanceof InkDemonEntity) {
                    ((InkDemonEntity)entity).setAnimation("teleport_out");
                }

                Vec3 _centerx = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_centerx, _centerx).inflate(50.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityPlayer && !M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entityiterator)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entityiterator),
                                M.getRotationVector(entityiterator),
                                M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                4,
                                M.getString(M.getName(entityiterator)),
                                M.getDisplayName(entityiterator),
                                M.getServer(M.level(entityiterator)),
                                entityiterator
                            ),
                            "/execute at @p[gamemode=survival] rotated ~ 1 run spreadplayers ~ ~ 10 12 false @e[type=boh:ink_demon,limit=1]"
                        );
                    }
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 100, 254, false, false));
                }

                BohMod.queueServerWork(
                    20,
                    () -> {
                        if (entity instanceof InkDemonEntity) {
                            ((InkDemonEntity)entity).setAnimation("teleport_in");
                        }

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
                                "/particle minecraft:squid_ink ~ ~ ~ 0.5 1 0.5 .15 35"
                            );
                        }
                    }
                );
                BohMod.queueServerWork(
                    35,
                    () -> {
                        if (world instanceof World) {
                            if (!M.isClientSide(world)) {
                                M.playSound(
                                    world,
                                    null,
                                    BlockPos.containing(x, y, z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bendy_scream")),
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
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bendy_scream")),
                                    SoundSource.HOSTILE,
                                    1.0F,
                                    1.0F,
                                    false
                                );
                            }
                        }
                    }
                );
                BohMod.queueServerWork(40, () -> {
                    if (entity instanceof InkDemonEntity _datEntSetL) {
                        M.set(M.getEntityData(_datEntSetL), InkDemonEntity.DATA_TeleportBendy, false);
                    }
                });
            }
        }
    }
}
