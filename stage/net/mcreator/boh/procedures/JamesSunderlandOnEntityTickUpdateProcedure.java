package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class JamesSunderlandOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.isEmpty(M.getEntitiesOfClass(world, PyramidHeadEntity.class, AABB.ofSize(new Vec3(x, y, z), 35.0, 35.0, 35.0), e -> true)) && Math.random() < 0.01 && Math.random() < 0.01 && Math.random() < 0.01 && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true))) {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.INTO_THE_FOG.get(), 1000, 0, false, false));
                    }
                }
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.PYRAMID_HEAD.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                    }
                }
            }
        }
    }
}
