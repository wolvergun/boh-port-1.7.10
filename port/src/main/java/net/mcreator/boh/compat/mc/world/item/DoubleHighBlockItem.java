package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.item.BohBlockItem;
import net.minecraft.block.Block;

/** 1.20 DoubleHighBlockItem (doors): BohBlockItem places doors as two halves. */
public class DoubleHighBlockItem extends BohBlockItem {

    public DoubleHighBlockItem(Block block, Properties props) {
        super(block, props);
    }
}
