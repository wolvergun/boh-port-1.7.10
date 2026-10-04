package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class NPC000OnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(8.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (entityiterator instanceof EntityPlayer && !(entityiterator instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, (Potion) BohModMobEffects.NPC_000INFLUENCE.get())) && entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.NPC_000INFLUENCE.get(), 60, 0, false, false));
                }
            }
            M.putDouble(M.getPersistentData(entity), "timer_far", M.getDouble(M.getPersistentData(entity), "timer_far") + 1.0);
            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true)) && M.getDouble(M.getPersistentData(entity), "timer_far") == 8.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:npc_000_near")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:npc_000_near")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "timer_far", 0.0);
            }
        }
    }
}
