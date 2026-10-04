package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.ParticleTypes;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class AnglerOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "attack_angler", M.getDouble(M.getPersistentData(entity), "attack_angler") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "attack_angler") >= 44.0) {
                M.putDouble(M.getPersistentData(entity), "attack_angler", 0.0);
                if (!M.isClientSide(world) && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:angler_attack")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:angler_attack")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }
            }

            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, BohModParticleTypes.ANGLER_PARTICLE.get(), M.getX(entity), M.getY(entity) + 1.5, M.getZ(entity), 1, 0.0, 0.0, 0.0, 0.0);
            }

            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, ParticleTypes.SQUID_INK, M.getX(entity), M.getY(entity) + 1.5, M.getZ(entity), 10, 1.0, 1.0, 1.0, 0.05);
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(15.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof EntityPlayer
                    && !M.isShiftKeyDown(entityiterator)
                    && !(entity instanceof EntityLiving _mob && M.isAggressive(_mob))
                    && entity instanceof EntityLiving _entity
                    && entityiterator instanceof EntityLivingBase _ent) {
                    M.setTarget(_entity, _ent);
                }
            }

            Vec3 _center_r2 = new Vec3(x, y, z);

            for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r2, _center_r2).inflate(5.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r2)))
                .toList()) {
                if (entityiteratorx instanceof EntityPlayer
                    && !(entity instanceof EntityLiving _mob && M.isAggressive(_mob))
                    && entity instanceof EntityLiving _entity
                    && entityiteratorx instanceof EntityLivingBase _ent) {
                    M.setTarget(_entity, _ent);
                }
            }
        }
    }
}
