package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SadakoOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(7.5), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (!(entityiterator instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, BohModMobEffects.SADAKO_EFFECT.get()))
                    && entityiterator instanceof EntityPlayer
                    && entityiterator instanceof EntityLivingBase _entity
                    && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.SADAKO_EFFECT.get(), 60, 0, false, false));
                }
            }

            Vec3 _center_r52 = new Vec3(x, y, z);

            for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r52, _center_r52).inflate(3.5), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r52)))
                .toList()) {
                if (!(entityiteratorx instanceof EntityLivingBase _livEnt5 && M.hasEffect(_livEnt5, MobEffects.WITHER))
                    && entityiteratorx instanceof EntityPlayer
                    && entityiteratorx instanceof EntityLivingBase _entity
                    && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WITHER, 60, 0, false, false));
                }
            }

            M.putDouble(M.getPersistentData(entity), "tick_music", M.getDouble(M.getPersistentData(entity), "tick_music") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "tick_music") == 660.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_idle")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sadako_idle")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "tick_music", 0.0);
            }
        }
    }
}
