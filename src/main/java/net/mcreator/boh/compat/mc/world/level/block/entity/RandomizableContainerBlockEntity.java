package net.mcreator.boh.compat.mc.world.level.block.entity;

import java.util.Random;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.loot.LootTables;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.ContainerHelper;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class RandomizableContainerBlockEntity extends TileEntity implements IInventory, MenuProvider {
    protected final BlockEntityType<?> type;
    protected BlockPos worldPosition;
    protected boolean remove;
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    private NBTTagCompound persistentData;

    public NBTTagCompound getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NBTTagCompound();
        }

        return this.persistentData;
    }

    protected RandomizableContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this.type = type;
        this.worldPosition = pos == null ? BlockPos.ZERO : pos;
    }

    public BlockPos worldPosition() {
        return this.getBlockPos();
    }

    public BlockEntityType<?> getType() {
        return this.type;
    }

    public World getLevel() {
        return this.worldObj;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(this.xCoord, this.yCoord, this.zCoord);
    }

    public BlockState getBlockState() {
        return this.worldObj == null ? BlockState.of(this.getBlockType()) : M.getBlockState(this.worldObj, this.getBlockPos());
    }

    public boolean hasLevel() {
        return this.worldObj != null;
    }

    public void load(NBTTagCompound tag) {
    }

    protected void saveAdditional(NBTTagCompound tag) {
    }

    public NBTTagCompound saveWithFullMetadata() {
        NBTTagCompound t = new NBTTagCompound();
        this.writeToNBT(t);
        return t;
    }

    public NBTTagCompound saveWithoutMetadata() {
        NBTTagCompound t = new NBTTagCompound();
        this.saveAdditional(t);
        return t;
    }

    public NBTTagCompound getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public Object getUpdatePacket() {
        return null;
    }

    public void setChanged() {
        this.markDirty();
    }

    public void setRemoved() {
        this.remove = true;
    }

    public boolean isRemoved() {
        return this.remove;
    }

    public void clearRemoved() {
        this.remove = false;
    }

    public boolean tryLoadLootTable(NBTTagCompound tag) {
        if (tag.hasKey("LootTable", 8)) {
            this.lootTable = new ResourceLocation(tag.getString("LootTable"));
            this.lootTableSeed = tag.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    public boolean trySaveLootTable(NBTTagCompound tag) {
        if (this.lootTable == null) {
            return false;
        } else {
            tag.setString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                tag.setLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public void setLootTable(ResourceLocation table, long seed) {
        this.lootTable = table;
        this.lootTableSeed = seed;
    }

    public void unpackLootTable(EntityPlayer player) {
        if (this.lootTable != null && this.worldObj != null && !this.worldObj.isRemote) {
            ResourceLocation t = this.lootTable;
            this.lootTable = null;
            LootTables.fill(this, t, this.lootTableSeed == 0L ? this.worldObj.rand : new Random(this.lootTableSeed));
            this.markDirty();
        }
    }

    protected abstract NonNullList<ItemStack> getItems();

    protected abstract void setItems(NonNullList<ItemStack> var1);

    public abstract int getContainerSize();

    protected abstract Component getDefaultName();

    @Override
    public Component getDisplayName() {
        return this.getDefaultName();
    }

    public Component getName() {
        return this.getDisplayName();
    }

    protected Container createMenu(int id, InventoryPlayer inventory) {
        return null;
    }

    @Override
    public Container createMenu(int id, InventoryPlayer inventory, EntityPlayer player) {
        this.unpackLootTable(player);
        return this.createMenu(id, inventory);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean isEmpty() {
        for (ItemStack s : this.getItems()) {
            if (M.legacy(s) != null) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int slot) {
        this.unpackLootTable(null);
        return M.stack(this.getItems().get(slot));
    }

    public ItemStack removeItem(int slot, int amount) {
        this.unpackLootTable(null);
        ItemStack s = ContainerHelper.removeItem(this.getItems(), slot, amount);
        if (M.legacy(s) != null) {
            this.setChanged();
        }

        return s;
    }

    public void setItem(int slot, ItemStack stack) {
        this.unpackLootTable(null);
        this.getItems().set(slot, M.stack(stack));
        this.setChanged();
    }

    public boolean stillValid(EntityPlayer player) {
        return this.worldObj != null
            && this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
            && player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }

    public void clearContent() {
        this.getItems().clear();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return LazyOptional.empty();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        return this.getCapability(cap, null);
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("ForgeData")) {
            this.persistentData = tag.getCompoundTag("ForgeData");
        }

        this.load(tag);
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (this.persistentData != null) {
            tag.setTag("ForgeData", this.persistentData);
        }

        this.saveAdditional(tag);
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound t = this.getUpdateTag();
        return t == null ? null : new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, t);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.load(pkt.func_148857_g());
    }

    public void invalidate() {
        super.invalidate();
        this.setRemoved();
    }

    public void validate() {
        super.validate();
        this.clearRemoved();
    }

    public int getSizeInventory() {
        return this.getContainerSize();
    }

    public ItemStack getStackInSlot(int slot) {
        return M.legacy(this.getItem(slot));
    }

    public ItemStack decrStackSize(int slot, int amount) {
        return M.legacy(this.removeItem(slot, amount));
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack s = this.getItems().get(slot);
        this.getItems().set(slot, M.EMPTY);
        return M.legacy(s);
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.setItem(slot, stack);
    }

    public String getInventoryName() {
        return this.getDisplayName().getString();
    }

    public boolean hasCustomInventoryName() {
        return true;
    }

    public int getInventoryStackLimit() {
        return this.getMaxStackSize();
    }

    public boolean isUseableByPlayer(EntityPlayer p) {
        return this.stillValid(p);
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return this.canPlaceItem(slot, M.stack(stack));
    }
}
