package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class PyramidHeadOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "radio_static", M.getDouble(M.getPersistentData(entity), "radio_static") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "radio_static") == 160.0) {
                M.putDouble(M.getPersistentData(entity), "radio_static", 0.0);
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sh_static")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sh_static")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
            }
            if (M.getBoolean(M.getPersistentData(entity), "trap_toggle")) {
                if (!M.getBoolean(M.getPersistentData(entity), "trap") && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                    M.putBoolean(M.getPersistentData(entity), "trap", true);
                }
                if (M.getBoolean(M.getPersistentData(entity), "trap")) {
                    M.putBoolean(M.getPersistentData(entity), "trap", false);
                    M.putBoolean(M.getPersistentData(entity), "trap_toggle", false);
                    if (entity instanceof PyramidHeadEntity) {
                        ((PyramidHeadEntity) entity).setAnimation("place_trap");
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:pyramidhead_hurt")), SoundSource.HOSTILE, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:pyramidhead_hurt")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                        }
                    }
                    BohMod.queueServerWork(10, () -> {
                        if (world instanceof World _levelx) {
                            if (!M.isClientSide(_levelx)) {
                                M.playSound(_levelx, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.HOSTILE, 1.0F, 1.0F);
                            } else {
                                M.playLocalSound(_levelx, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                            }
                        }
                    });
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
                    }
                    Vec3 _center = new Vec3(x, y, z);
                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(15.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                        if (entityiterator instanceof EntityPlayer && world instanceof WorldServer _level) {
                            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.TORMENT_PYRAMID.get()), _level, BlockPos.containing(M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator)), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
            if (Math.random() < 0.01) {
                M.putBoolean(M.getPersistentData(entity), "trap_toggle", true);
            }
        }
    }
}
