package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TimerOverlayEffectExpiresProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity instanceof EntityPlayer _player) {
            ItemStack _stktoremove = M.new_ItemStack(BohModItems.FACEHUGGER_FACE.get());
            M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
        }
    }
}
