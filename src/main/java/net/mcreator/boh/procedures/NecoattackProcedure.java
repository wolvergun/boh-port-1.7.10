package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.NecoArcEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class NecoattackProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(
                event,
                M.level(M.getEntity(event)),
                M.getX(M.getEntity(event)),
                M.getY(M.getEntity(event)),
                M.getZ(M.getEntity(event)),
                M.getEntity(M.getSource(event))
            );
        }
    }

    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        execute(null, world, x, y, z, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null && sourceentity instanceof NecoArcEntity && world instanceof WorldServer _level) {
            EntityLightningBolt entityToSpawn = M.create(EntityType.LIGHTNING_BOLT, _level);
            M.moveTo(entityToSpawn, Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));
            M.addFreshEntity(_level, entityToSpawn);
        }
    }
}
