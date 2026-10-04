package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SquidwardDoomedEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SquidwardDoomedOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof SquidwardDoomedEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), SquidwardDoomedEntity.DATA_cooldown_squidward, (entity instanceof SquidwardDoomedEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(SquidwardDoomedEntity.DATA_cooldown_squidward) : 0) + 1);
            }
            if ((entity instanceof SquidwardDoomedEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(SquidwardDoomedEntity.DATA_cooldown_squidward) : 0) == 1 && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:squidward_redmist")), SoundSource.HOSTILE, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:squidward_redmist")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
            if ((entity instanceof SquidwardDoomedEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(SquidwardDoomedEntity.DATA_cooldown_squidward) : 0) == 200 && entity instanceof SquidwardDoomedEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), SquidwardDoomedEntity.DATA_cooldown_squidward, 0);
            }
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.RED_MIST.get(), 60, 0, false, false));
                }
            }
        }
    }
}
