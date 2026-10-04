package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TheGreatKnifeEntitySwingsItemProcedure {
    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null && entity instanceof EntityPlayer _player) {
            M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 50);
        }
    }
}
