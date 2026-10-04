package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.MichaelMyersEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class MyersAttackProcedure {
    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null && entity instanceof MichaelMyersEntity) {
            EntityLivingBase _center = entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null;
            if (!(_center instanceof EntityLivingBase) || !M.hasEffect(_center, BohModMobEffects.SHAPES_GAZE.get())) {
                Vec3 _centerx = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_centerx, _centerx).inflate(12.5), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof EntityLivingBase _livEnt3
                        && M.hasEffect(_livEnt3, BohModMobEffects.SHAPES_GAZE.get())
                        && entity instanceof EntityLiving _entity
                        && entityiterator instanceof EntityLivingBase _ent) {
                        M.setTarget(_entity, _ent);
                    }
                }
            }
        }
    }
}
