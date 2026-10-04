package net.mcreator.boh.compat.mc.world.item;

import java.util.function.Predicate;

import net.mcreator.boh.compat.M;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** 1.20 ProjectileWeaponItem.getHeldProjectile. */
public final class ProjectileWeaponItem {

    private ProjectileWeaponItem() {}

    public static ItemStack getHeldProjectile(EntityLivingBase e, Predicate<ItemStack> p) {
        ItemStack held = e.getHeldItem();
        if (held != null && p.test(held)) return held;
        if (e instanceof EntityPlayer) {
            for (ItemStack s : ((EntityPlayer) e).inventory.mainInventory) if (s != null && p.test(s)) return s;
        }
        return M.EMPTY;
    }
}
