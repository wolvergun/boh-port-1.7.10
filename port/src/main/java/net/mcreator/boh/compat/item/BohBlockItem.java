package net.mcreator.boh.compat.item;

import net.mcreator.boh.compat.mc.world.level.block.SlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/** Block item; places doors as two halves and merges slabs into double slabs like 1.20. */
public class BohBlockItem extends ItemBlock {

    public BohBlockItem(Block block) {
        super(block);
    }

    public BohBlockItem(Block block, net.mcreator.boh.compat.mc.world.item.Properties properties) {
        super(block);
        if (properties != null) setMaxStackSize(properties.maxStackSize);
    }

    /** 1.20 client extensions (custom item renderers of block items). */
    public void initializeClient(java.util.function.Consumer<net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions> consumer) {}

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hx, float hy, float hz) {
        if (field_150939_a instanceof SlabBlock && ((SlabBlock) field_150939_a).tryMerge(stack, player, w, x, y, z, side, hy)) return true;
        if (field_150939_a instanceof BlockDoor) {
            if (side != 1) return false;
            y++;
            if (!player.canPlayerEdit(x, y, z, side, stack) || !player.canPlayerEdit(x, y + 1, z, side, stack)) return false;
            if (!field_150939_a.canPlaceBlockAt(w, x, y, z)) return false;
            int facing = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
            ItemDoor.placeDoorBlock(w, x, y, z, facing, field_150939_a);
            stack.stackSize--;
            return true;
        }
        return super.onItemUse(stack, player, w, x, y, z, side, hx, hy, hz);
    }
}
