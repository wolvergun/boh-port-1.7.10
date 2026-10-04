package net.mcreator.boh.compat.entity;

import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** 1.20 Arrow (plain arrow shot by procedures). */
public class BohArrow extends BohAbstractArrow implements net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier {

    public BohArrow(EntityType<?> type, World world) {
        super(type, world);
    }

    public BohArrow(World world) {
        super(null, world);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.arrow);
    }
}
