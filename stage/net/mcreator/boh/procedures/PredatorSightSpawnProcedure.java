package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.entity.PredatorSightEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.ExplosionInteraction;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class PredatorSightSpawnProcedure {

    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.getDouble(M.getPersistentData(entity), "predator_lockon") == 100.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_confirm")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_confirm")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_tracking")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_tracking")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.PREDATOR_SIGHT.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                    }
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.SIGHT_OF_THE_PREDATOR.get(), 1000, 0, false, false));
                }
            } else if (M.getDouble(M.getPersistentData(entity), "predator_lockon") == 300.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_tracking")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_tracking")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof PredatorSightEntity && entityiterator instanceof PredatorSightEntity animatable) {
                        animatable.setTexture("predator_sight_2");
                    }
                }
            } else if (M.getDouble(M.getPersistentData(entity), "predator_lockon") == 500.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_tracking")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_tracking")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof PredatorSightEntity && entityiterator instanceof PredatorSightEntity animatable) {
                        animatable.setTexture("predator_sight_3");
                    }
                }
            } else if (M.getDouble(M.getPersistentData(entity), "predator_lockon") == 700.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_lockon")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_lockon")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof PredatorSightEntity && entityiterator instanceof PredatorSightEntity animatable) {
                        animatable.setTexture("predator_sight_4");
                    }
                }
            } else if (M.getDouble(M.getPersistentData(entity), "predator_lockon") == 900.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_explosion")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:predator_caster_explosion")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
                if (world instanceof World _level && !M.isClientSide(_level)) {
                    M.explode(_level, null, x, y, z, 4.0F, ExplosionInteraction.NONE);
                }
            }
        }
    }
}
