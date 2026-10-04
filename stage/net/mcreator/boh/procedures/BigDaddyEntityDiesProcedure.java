package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BigDaddyEntityDiesProcedure {

    public static void execute(World world, double x, double y, double z) {
        Vec3 _center = new Vec3(x, y, z);
        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
            if (entityiterator instanceof LittleSisterEntity) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator)), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:little_sister_bd_dies")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:little_sister_bd_dies")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
                if (entityiterator instanceof EntityLiving _entity) {
                    M.moveTo(M.getNavigation(_entity), x, y, z, 1.2);
                }
                BohMod.queueServerWork(200, () -> {
                    if (entityiterator instanceof EntityLivingBase _entityx && !M.isClientSide(M.level(_entityx))) {
                        M.addEffect(_entityx, M.new_PotionEffect(MobEffects.DIG_SPEED, 600, 0, false, false));
                    }
                });
            }
        }
    }
}
