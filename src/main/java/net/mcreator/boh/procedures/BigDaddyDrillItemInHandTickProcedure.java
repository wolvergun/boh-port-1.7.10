package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BigDaddyDrillItemInHandTickProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null && M.getBoolean(M.getOrCreateTag(itemstack), "dash")) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.5), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (M.getItem(entityiterator instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != M.getItem(itemstack)) {
                    M.hurt(
                        entityiterator,
                        M.new_DamageSource(
                            M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.PLAYER_ATTACK), entity
                        ),
                        5.0F
                    );
                }
            }
        }
    }
}
