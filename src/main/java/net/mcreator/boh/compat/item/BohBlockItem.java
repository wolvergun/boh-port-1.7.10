package net.mcreator.boh.compat.item;

import java.util.function.Consumer;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.level.block.SlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BohBlockItem extends ItemBlock {
    public BohBlockItem(Block block) {
        super(block);
    }

    public BohBlockItem(Block block, Properties properties) {
        super(block);
        if (properties != null) {
            this.setMaxStackSize(properties.maxStackSize);
        }
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hx, float hy, float hz) {
        if (this.field_150939_a instanceof SlabBlock && ((SlabBlock)this.field_150939_a).tryMerge(stack, player, w, x, y, z, side, hy)) {
            return true;
        } else if (this.field_150939_a instanceof BlockDoor) {
            if (side != 1) {
                return false;
            } else {
                y++;
                if (!player.canPlayerEdit(x, y, z, side, stack) || !player.canPlayerEdit(x, y + 1, z, side, stack)) {
                    return false;
                } else if (!this.field_150939_a.canPlaceBlockAt(w, x, y, z)) {
                    return false;
                } else {
                    int facing = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5) & 3;
                    ItemDoor.placeDoorBlock(w, x, y, z, facing, this.field_150939_a);
                    stack.stackSize--;
                    return true;
                }
            }
        } else {
            return super.onItemUse(stack, player, w, x, y, z, side, hx, hy, hz);
        }
    }
}
