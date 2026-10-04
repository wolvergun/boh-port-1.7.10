package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class HideAndSeekOnEffectActiveTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.25) {
                if (Math.random() < 0.02) {
                    M.putDouble(M.getPersistentData(entity), "exe_static", 1.0);
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_static")), SoundSource.AMBIENT, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_static")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        }
                    }
                } else if (Math.random() < 0.02) {
                    M.putDouble(M.getPersistentData(entity), "exe_static", 2.0);
                } else if (Math.random() < 0.02) {
                    M.putDouble(M.getPersistentData(entity), "exe_static", 3.0);
                } else if (Math.random() < 0.02) {
                    M.putDouble(M.getPersistentData(entity), "exe_static", 4.0);
                } else if (Math.random() < 0.02) {
                    M.putDouble(M.getPersistentData(entity), "exe_static", 5.0);
                } else {
                    M.putDouble(M.getPersistentData(entity), "exe_static", 0.0);
                }
            } else {
                M.putDouble(M.getPersistentData(entity), "exe_apparison", 0.0);
            }
            if (Math.random() < 0.5) {
                if (Math.random() < 0.024) {
                    M.putDouble(M.getPersistentData(entity), "exe_apparison", 1.0);
                } else if (Math.random() < 0.024) {
                    M.putDouble(M.getPersistentData(entity), "exe_apparison", 2.0);
                } else if (Math.random() < 0.024) {
                    M.putDouble(M.getPersistentData(entity), "exe_apparison", 3.0);
                } else if (Math.random() < 0.024) {
                    M.putDouble(M.getPersistentData(entity), "exe_apparison", 4.0);
                } else {
                    M.putDouble(M.getPersistentData(entity), "exe_apparison", 0.0);
                }
            } else {
                M.putDouble(M.getPersistentData(entity), "exe_apparison", 0.0);
            }
            if (M.getDouble(M.getPersistentData(entity), "monitor_spawn") == 0.0 && Math.random() < 0.0015) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.EXE_MONITOR.get()), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "monitor_spawn", 1.0);
            }
        }
    }
}
