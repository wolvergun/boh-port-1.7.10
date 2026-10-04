package net.mcreator.boh.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.boh.init.BohModMenus;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.ContainerLevelAccess;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.mcreator.boh.compat.forge.items.IItemHandler;
import net.mcreator.boh.compat.forge.items.ItemStackHandler;
import net.mcreator.boh.compat.M;

public class WitnessUIMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

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

    public WitnessUIMenu(int id, InventoryPlayer inv, FriendlyByteBuf extraData) {
        super((MenuType) BohModMenus.WITNESS_UI.get(), id);
        this.entity = M.player(inv);
        this.world = M.level(M.player(inv));
        this.internal = new ItemStackHandler(0);
        BlockPos pos = null;
        if (extraData != null) {
            pos = M.readBlockPos(extraData);
            this.x = M.getX(pos);
            this.y = M.getY(pos);
            this.z = M.getZ(pos);
            this.access = ContainerLevelAccess.create(this.world, pos);
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
        return M.EMPTY;
    }

    public Map<Integer, Slot> get() {
        return this.customSlots;
    }
}
