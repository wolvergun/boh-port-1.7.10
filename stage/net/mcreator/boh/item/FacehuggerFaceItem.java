package net.mcreator.boh.item;

import net.mcreator.boh.procedures.FacehuggerFaceItemInInventoryTickProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.item.BohItem;

public class FacehuggerFaceItem extends BohItem {

    public FacehuggerFaceItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        FacehuggerFaceItemInInventoryTickProcedure.execute(world, entity, itemstack);
    }
}
