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

public class WerewolfDummyOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!(world instanceof World _lvl0 && M.isDay(_lvl0))) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.WEREWOLF.get()), _level, BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.getYRot(entity));
                        M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                        M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                        M.setXRot(entityToSpawn, M.getXRot(entity));
                        M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                    }
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.howl")), SoundSource.HOSTILE, 2.0F, 0.3F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.howl")), SoundSource.HOSTILE, 2.0F, 0.3F, false);
                    }
                }
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            }
        }
    }
}
