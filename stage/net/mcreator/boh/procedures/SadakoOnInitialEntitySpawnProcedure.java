package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SadakoEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SadakoOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 19, 0, false, false));
            }
            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y, z))) == BohModBlocks.ANALOG_TV_SADAKO.get()) {
                Entity _ent = entity;
                M.teleportTo(_ent, x, y, z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                }
                M.lookAt(entity, Anchor.EYES, new Vec3(x - 1.0, y + 1.0, z));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }
                BohMod.queueServerWork(20, () -> {
                    if (entity instanceof SadakoEntity) {
                        ((SadakoEntity) entity).setAnimation("crawl");
                    }
                });
            } else if (M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y, z))) == BohModBlocks.ANALOG_TV_SADAKO.get()) {
                Entity _ent = entity;
                M.teleportTo(_ent, x, y, z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                }
                M.lookAt(entity, Anchor.EYES, new Vec3(x + 1.0, y + 1.0, z));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }
                BohMod.queueServerWork(20, () -> {
                    if (entity instanceof SadakoEntity) {
                        ((SadakoEntity) entity).setAnimation("crawl");
                    }
                });
            } else if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z + 1.0))) == BohModBlocks.ANALOG_TV_SADAKO.get()) {
                Entity _ent = entity;
                M.teleportTo(_ent, x, y, z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                }
                M.lookAt(entity, Anchor.EYES, new Vec3(x, y + 1.0, z - 1.0));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }
                BohMod.queueServerWork(20, () -> {
                    if (entity instanceof SadakoEntity) {
                        ((SadakoEntity) entity).setAnimation("crawl");
                    }
                });
            } else if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z - 1.0))) == BohModBlocks.ANALOG_TV_SADAKO.get()) {
                Entity _ent = entity;
                M.teleportTo(_ent, x, y, z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                }
                M.lookAt(entity, Anchor.EYES, new Vec3(x, y + 1.0, z + 1.0));
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }
                BohMod.queueServerWork(20, () -> {
                    if (entity instanceof SadakoEntity) {
                        ((SadakoEntity) entity).setAnimation("crawl");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_tv")), SoundSource.HOSTILE, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_tv")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                        }
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_idle")), SoundSource.HOSTILE, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_idle")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                        }
                    }
                });
            }
        }
    }
}
