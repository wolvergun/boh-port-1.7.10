package net.mcreator.boh.compat.entity;

import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BohArrow extends BohAbstractArrow implements ItemSupplier {
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
