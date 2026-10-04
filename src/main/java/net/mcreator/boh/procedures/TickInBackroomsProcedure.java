package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.world.Dimensions;
import net.mcreator.boh.entity.LifeformEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TickInBackroomsProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.getX(M.player(event)), M.getY(M.player(event)), M.getZ(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && M.dimension(M.level(entity)) == Dimensions.dimensionKey(new ResourceLocation("boh:level_0"))
            && M.isEmpty(M.getEntitiesOfClass(world, LifeformEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000.0, 1000.0, 1000.0), e -> true))
            && Math.random() < 0.005
            && world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(BohModEntities.LIFEFORM.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
            }
        }
    }
}
