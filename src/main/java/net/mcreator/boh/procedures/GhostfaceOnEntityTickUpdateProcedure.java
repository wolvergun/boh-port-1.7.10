package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.GhostfaceDecoyEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GhostfaceOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _livEnt0
                && M.hasEffect(_livEnt0, MobEffects.SATURATION)
                && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true))
                && entity instanceof EntityLivingBase _entity) {
                M.removeEffect(_entity, MobEffects.SATURATION);
            }

            if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer
                && M.isEmpty(M.getEntitiesOfClass(world, GhostfaceDecoyEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true))
                && world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(BohModEntities.GHOSTFACE_DECOY.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                }
            }

            if (entity instanceof EntityLivingBase _livEnt7 && M.hasEffect(_livEnt7, MobEffects.SATURATION)) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 20, 1, false, false));
                }

                M.putDouble(M.getPersistentData(entity), "timer_ghostface", M.getDouble(M.getPersistentData(entity), "timer_ghostface") + 1.0);
            }

            if (M.getDouble(M.getPersistentData(entity), "timer_ghostface") >= 500.0 && entity instanceof EntityLivingBase _entity) {
                M.removeEffect(_entity, MobEffects.SATURATION);
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))) {
                M.putDouble(M.getPersistentData(entity), "timer_ghostface", 0.0);
            }
        }
    }
}
