package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ClickNOProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity instanceof EntityPlayer _player) {
            M.closeContainer(_player);
        }
    }
}
