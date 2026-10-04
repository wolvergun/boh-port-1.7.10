package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.M;

public class DreamCatcherOnTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z) {
        Vec3 _center = new Vec3(x, y, z);
        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
            if (entityiterator instanceof EntityPlayer && entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.SAFE_AND_SOUND.get(), 60, 0));
            }
        }
    }
}
