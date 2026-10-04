package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AngerRangeXenomorphProcedure {
    public static boolean execute(World world, double x, double y, double z, Entity entity) {
        boolean var10000;
        if (entity == null) {
            var10000 = false;
        } else {
            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true))) {
                EntityLivingBase var10 = entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null;
                if (M.getItem(var10 instanceof EntityLivingBase ? M.getItemBySlot(var10, EquipmentSlot.HEAD) : M.EMPTY) == BohModItems.FACEHUGGER_FACE.get()) {
                    return false;
                }
            }

            var10000 = true;
        }

        return var10000;
    }
}
