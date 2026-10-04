package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.VampireBatEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class VampireOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z))
                && world instanceof World
                && M.isDay(world)
                && M.getRemainingFireTicks(entity) < 0
                && !M.isRaining(M.getLevelData(world))) {
                M.setSecondsOnFire(entity, 5);
            }

            if (entity instanceof EntityLiving _mob
                && M.isAggressive(_mob)
                && M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))) {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }

                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.VAMPIRE_BAT.get(),
                        _level,
                        BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)),
                        MobSpawnType.MOB_SUMMONED
                    );
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.getYRot(entity));
                        M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                        M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                        M.setXRot(entityToSpawn, M.getXRot(entity));
                        M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                    }
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    BohMod.queueServerWork(2, () -> {
                        if (entityiterator instanceof VampireBatEntity && entityiterator instanceof EntityLivingBase _entity) {
                            M.setHealth(_entity, entity instanceof EntityLivingBase _livEnt ? M.getHealth(_livEnt) : -1.0F);
                        }
                    });
                }
            }
        }
    }
}
