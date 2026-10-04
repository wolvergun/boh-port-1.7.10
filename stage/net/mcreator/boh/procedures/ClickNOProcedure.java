package net.mcreator.boh.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.M;

public class ClickNOProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityPlayer _player) {
                M.closeContainer(_player);
            }
        }
    }
}
