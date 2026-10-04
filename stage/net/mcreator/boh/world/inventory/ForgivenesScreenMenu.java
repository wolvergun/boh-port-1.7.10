package net.mcreator.boh.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModMenus;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.ContainerLevelAccess;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities;
import net.mcreator.boh.compat.forge.items.IItemHandler;
import net.mcreator.boh.compat.forge.items.ItemStackHandler;
import net.mcreator.boh.compat.forge.items.SlotItemHandler;
import net.mcreator.boh.compat.M;

public class ForgivenesScreenMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

    public static final HashMap<String, Object> guistate = new HashMap<>();

    public final World world;

    public final EntityPlayer entity;

    public int x;

    public int y;

    public int z;

    private ContainerLevelAccess access = ContainerLevelAccess.NULL;

    private IItemHandler internal;

    private final Map<Integer, Slot> customSlots = new HashMap<>();

    private boolean bound = false;

    private Supplier<Boolean> boundItemMatcher = null;

    private Entity boundEntity = null;

    private TileEntity boundBlockEntity = null;

    public ForgivenesScreenMenu(int id, InventoryPlayer inv, FriendlyByteBuf extraData) {
        super((MenuType) BohModMenus.FORGIVENES_SCREEN.get(), id);
        this.entity = M.player(inv);
        this.world = M.level(M.player(inv));
        this.internal = new ItemStackHandler(1);
        BlockPos pos = null;
        if (extraData != null) {
            pos = M.readBlockPos(extraData);
            this.x = M.getX(pos);
            this.y = M.getY(pos);
            this.z = M.getZ(pos);
            this.access = ContainerLevelAccess.create(this.world, pos);
        }
        if (pos != null) {
            if (extraData.readableBytes() == 1) {
                byte hand = M.readByte(extraData);
                ItemStack itemstack = hand == 0 ? M.getMainHandItem(this.entity) : M.getOffhandItem(this.entity);
                this.boundItemMatcher = () -> itemstack == (hand == 0 ? M.getMainHandItem(this.entity) : M.getOffhandItem(this.entity));
                M.getCapability(itemstack, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                    this.internal = capability;
                    this.bound = true;
                });
            } else if (extraData.readableBytes() > 1) {
                M.readByte(extraData);
                this.boundEntity = M.getEntity(this.world, M.readVarInt(extraData));
                if (this.boundEntity != null) {
                    M.getCapability(this.boundEntity, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        this.internal = capability;
                        this.bound = true;
                    });
                }
            } else {
                this.boundBlockEntity = M.getBlockEntity(this.world, pos);
                if (this.boundBlockEntity != null) {
                    M.getCapability(this.boundBlockEntity, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        this.internal = capability;
                        this.bound = true;
                    });
                }
            }
        }
        this.customSlots.put(0, M.addSlot(this, new SlotItemHandler(this.internal, 0, 14, 36) {

            private final int slot = 0;

            private int x;

            private int y;

            {
                this.x = ForgivenesScreenMenu.this.x;
                this.y = ForgivenesScreenMenu.this.y;
            }

            public boolean mayPlace(ItemStack stack) {
                return M.asItem(((Block) BohModBlocks.KINDNESS_FLOWER.get())) == M.getItem(stack);
            }
        }));
        for (int si = 0; si < 3; si++) {
            for (int sj = 0; sj < 9; sj++) {
                M.addSlot(this, new Slot(inv, sj + (si + 1) * 9, 15 + sj * 18, 60 + si * 18));
            }
        }
        for (int si = 0; si < 9; si++) {
            M.addSlot(this, new Slot(inv, si, 15 + si * 18, 118));
        }
    }

    public boolean stillValid(EntityPlayer player) {
        if (this.bound) {
            if (this.boundItemMatcher != null) {
                return this.boundItemMatcher.get();
            }
            if (this.boundBlockEntity != null) {
                return AbstractContainerMenu.stillValid(this.access, player, M.getBlock(M.getBlockState(this.boundBlockEntity)));
            }
            if (this.boundEntity != null) {
                return M.isAlive(this.boundEntity);
            }
        }
        return true;
    }

    public ItemStack quickMoveStack(EntityPlayer playerIn, int index) {
        ItemStack itemstack = M.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && M.hasItem(slot)) {
            ItemStack itemstack1 = M.getItem(slot);
            itemstack = M.copy(itemstack1);
            if (index < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
                    return M.EMPTY;
                }
                M.onQuickCraft(slot, itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                if (index < 28) {
                    if (!this.moveItemStackTo(itemstack1, 28, this.slots.size(), true)) {
                        return M.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 1, 28, false)) {
                    return M.EMPTY;
                }
                return M.EMPTY;
            }
            if (M.getCount(itemstack1) == 0) {
                M.set(slot, M.EMPTY);
            } else {
                M.setChanged(slot);
            }
            if (M.getCount(itemstack1) == M.getCount(itemstack)) {
                return M.EMPTY;
            }
            M.onTake(slot, playerIn, itemstack1);
        }
        return itemstack;
    }

    protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
        boolean flag = false;
        int i = p_38905_;
        if (p_38907_) {
            i = p_38906_ - 1;
        }
        if (M.isStackable(p_38904_)) {
            while (!M.isEmpty(p_38904_) && (p_38907_ ? i >= p_38905_ : i < p_38906_)) {
                Slot slot = (Slot) this.slots.get(i);
                ItemStack itemstack = M.getItem(slot);
                if (M.mayPlace(slot, itemstack) && !M.isEmpty(itemstack) && M.isSameItemSameTags(p_38904_, itemstack)) {
                    int j = M.getCount(itemstack) + M.getCount(p_38904_);
                    int maxSize = Math.min(M.getMaxStackSize(slot), M.getMaxStackSize(p_38904_));
                    if (j <= maxSize) {
                        M.setCount(p_38904_, 0);
                        M.setCount(itemstack, j);
                        M.set(slot, itemstack);
                        flag = true;
                    } else if (M.getCount(itemstack) < maxSize) {
                        M.shrink(p_38904_, maxSize - M.getCount(itemstack));
                        M.setCount(itemstack, maxSize);
                        M.set(slot, itemstack);
                        flag = true;
                    }
                }
                if (p_38907_) {
                    i--;
                } else {
                    i++;
                }
            }
        }
        if (!M.isEmpty(p_38904_)) {
            if (p_38907_) {
                i = p_38906_ - 1;
            } else {
                i = p_38905_;
            }
            while (p_38907_ ? i >= p_38905_ : i < p_38906_) {
                Slot slot1 = (Slot) this.slots.get(i);
                ItemStack itemstack1 = M.getItem(slot1);
                if (M.isEmpty(itemstack1) && M.mayPlace(slot1, p_38904_)) {
                    if (M.getCount(p_38904_) > M.getMaxStackSize(slot1)) {
                        M.setByPlayer(slot1, M.split(p_38904_, M.getMaxStackSize(slot1)));
                    } else {
                        M.setByPlayer(slot1, M.split(p_38904_, M.getCount(p_38904_)));
                    }
                    M.setChanged(slot1);
                    flag = true;
                    break;
                }
                if (p_38907_) {
                    i--;
                } else {
                    i++;
                }
            }
        }
        return flag;
    }

    public void removed(EntityPlayer playerIn) {
        super.removed(playerIn);
        if (!this.bound && playerIn instanceof EntityPlayerMP serverPlayer) {
            if (M.isAlive(serverPlayer) && !M.hasDisconnected(serverPlayer)) {
                for (int i = 0; i < M.getSlots(this.internal); i++) {
                    M.placeItemBackInInventory(M.getInventory(playerIn), M.extractItem(this.internal, i, M.getCount(M.getStackInSlot(this.internal, i)), false));
                }
            } else {
                for (int j = 0; j < M.getSlots(this.internal); j++) {
                    M.drop(playerIn, M.extractItem(this.internal, j, M.getCount(M.getStackInSlot(this.internal, j)), false), false);
                }
            }
        }
    }

    public Map<Integer, Slot> get() {
        return this.customSlots;
    }
}
