package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public class InHeadFacehuggerProcedure {
    public static boolean execute(Entity entity) {
        boolean var10000;
        if (entity == null) {
            var10000 = false;
        } else {
            EntityLivingBase var3 = entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null;
            var10000 = M.getItem(var3 instanceof EntityLivingBase ? M.getItemBySlot(var3, EquipmentSlot.HEAD) : M.EMPTY) != BohModItems.FACEHUGGER_FACE.get();
        }

        return var10000;
    }
}
