package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.entity.KrampusEntity;
import net.mcreator.boh.entity.SeedEaterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class AttackCHILDProcedure {

    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if ((entity instanceof SeedEaterEntity || entity instanceof KrampusEntity) && !((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase _livEnt3 && M.isBaby(_livEnt3))) {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(12.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityLivingBase _livEnt4 && M.isBaby(_livEnt4) && entity instanceof EntityLiving _entity && entityiterator instanceof EntityLivingBase _ent) {
                        M.setTarget(_entity, _ent);
                    }
                }
            }
        }
    }
}
