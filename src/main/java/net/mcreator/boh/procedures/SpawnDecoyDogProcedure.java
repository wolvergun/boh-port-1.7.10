package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class SpawnDecoyDogProcedure {
    @SubscribeEvent
    public void onEntitySpawned(EntityJoinWorldEvent event) {
        execute(event, M.getLevel(event), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null && entity instanceof EntityWolf && !(entity instanceof EntityTameable _tamEnt && M.isTame(_tamEnt))) {
            BohMod.queueServerWork(2, () -> {
                if (Math.random() < 0.02) {
                    if (!M.isClientSide(M.level(entity))) {
                        M.discard(entity);
                    }

                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(BohModEntities.DECOY_DOG.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                    }
                }
            });
        }
    }
}
