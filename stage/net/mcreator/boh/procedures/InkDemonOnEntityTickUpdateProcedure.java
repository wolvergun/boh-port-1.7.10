package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.InkDemonEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class InkDemonOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (!(entity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.SATURATION)) && Math.random() < 0.02 && Math.random() < 0.02 && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.SATURATION, 30, 0, false, false));
                }
                if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DARKNESS, 30, 0, false, false));
                }
                if (!(entity instanceof InkDemonEntity _datEntL5 && (Boolean) M.getEntityData(_datEntL5).get(InkDemonEntity.DATA_TeleportBendy)) && M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true)) && Math.random() < 0.02) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:squid_ink ~ ~ ~ 0.2 0.5 0.2 .1 20");
                    }
                    if (entity instanceof InkDemonEntity _datEntSetL) {
                        M.set(M.getEntityData(_datEntSetL), InkDemonEntity.DATA_TeleportBendy, true);
                    }
                    if (entity instanceof InkDemonEntity) {
                        ((InkDemonEntity) entity).setAnimation("teleport_out");
                    }
                    Vec3 _center = new Vec3(x, y, z);
                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                        if (entityiterator instanceof EntityPlayer) {
                            Entity _entx = entityiterator;
                            if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/execute at @p[gamemode=survival] rotated ~ 1 run spreadplayers ~ ~ 10 12 false @e[type=boh:ink_demon,limit=1]");
                            }
                        }
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 100, 254, false, false));
                    }
                    BohMod.queueServerWork(20, () -> {
                        if (entity instanceof InkDemonEntity) {
                            ((InkDemonEntity) entity).setAnimation("teleport_in");
                        }
                        Entity _entxx = entity;
                        if (!M.isClientSide(M.level(_entxx)) && M.getServer(_entxx) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_entxx)), new CommandSourceStack(CommandSource.NULL, M.position(_entxx), M.getRotationVector(_entxx), M.level(_entxx) instanceof WorldServer ? (WorldServer) M.level(_entxx) : null, 4, M.getString(M.getName(_entxx)), M.getDisplayName(_entxx), M.getServer(M.level(_entxx)), _entxx), "/particle minecraft:squid_ink ~ ~ ~ 0.5 1 0.5 .15 35");
                        }
                    });
                    BohMod.queueServerWork(35, () -> {
                        if (world instanceof World _level) {
                            if (!M.isClientSide(_level)) {
                                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bendy_scream")), SoundSource.HOSTILE, 1.0F, 1.0F);
                            } else {
                                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bendy_scream")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                            }
                        }
                    });
                    BohMod.queueServerWork(40, () -> {
                        if (entity instanceof InkDemonEntity _datEntSetL) {
                            M.set(M.getEntityData(_datEntSetL), InkDemonEntity.DATA_TeleportBendy, false);
                        }
                    });
                }
            }
        }
    }
}
