package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ChestbursterOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(
                7200,
                () -> {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }

                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(BohModEntities.XENOMORPH.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chestburster_kill")),
                                SoundSource.PLAYERS,
                                1.0F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chestburster_kill")),
                                SoundSource.PLAYERS,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                }
            );
        }
    }
}
