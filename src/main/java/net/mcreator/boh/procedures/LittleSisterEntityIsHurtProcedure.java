package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.BigDaddyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LittleSisterEntityIsHurtProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && sourceentity instanceof EntityLivingBase) {
            M.setShiftKeyDown(entity, true);
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.CONFUSION, 1200, 0, false, false));
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof BigDaddyEntity) {
                    if (Math.random() < 0.25 && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:little_sister_hurt_near_bd")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:little_sister_hurt_near_bd")),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (entityiterator instanceof EntityLiving _entity && sourceentity instanceof EntityLivingBase _ent) {
                        M.setTarget(_entity, _ent);
                    }
                }
            }
        }
    }
}
