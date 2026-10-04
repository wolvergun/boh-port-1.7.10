package net.mcreator.boh.compat.mc.world.level.block.entity;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.loot.LootTables;
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

/** 1.20 RandomizableContainerBlockEntity on a 1.7.10 TileEntity + IInventory. */
public abstract class RandomizableContainerBlockEntity extends TileEntity implements IInventory, MenuProvider {

    protected final BlockEntityType<?> type;
    protected BlockPos worldPosition;
    protected boolean remove;
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    private net.minecraft.nbt.NBTTagCompound persistentData;

    public net.minecraft.nbt.NBTTagCompound getPersistentData() {
        if (persistentData == null) persistentData = new NBTTagCompound();
        return persistentData;
    }

    protected RandomizableContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this.type = type;
        this.worldPosition = pos == null ? BlockPos.ZERO : pos;
    }

    // ------------------------------------------------------------------ 1.20 API

    public BlockPos worldPosition() {
        return getBlockPos();
    }

    public BlockEntityType<?> getType() {
        return type;
    }

    public World getLevel() {
        return worldObj;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(xCoord, yCoord, zCoord);
    }

    public BlockState getBlockState() {
        return worldObj == null ? BlockState.of(getBlockType()) : M.getBlockState(worldObj, getBlockPos());
    }

    public boolean hasLevel() {
        return worldObj != null;
    }

    public void load(NBTTagCompound tag) {}

    protected void saveAdditional(NBTTagCompound tag) {}

    public NBTTagCompound saveWithFullMetadata() {
        NBTTagCompound t = new NBTTagCompound();
        writeToNBT(t);
        return t;
    }

    public NBTTagCompound saveWithoutMetadata() {
        NBTTagCompound t = new NBTTagCompound();
        saveAdditional(t);
        return t;
    }

    public NBTTagCompound getUpdateTag() {
        return saveWithoutMetadata();
    }

    public Object getUpdatePacket() {
        return null;
    }

    public void setChanged() {
        markDirty();
    }

    public void setRemoved() {
        remove = true;
    }

    public boolean isRemoved() {
        return remove;
    }

    public void clearRemoved() {
        remove = false;
    }

    public boolean tryLoadLootTable(NBTTagCompound tag) {
        if (tag.hasKey("LootTable", 8)) {
            lootTable = new ResourceLocation(tag.getString("LootTable"));
            lootTableSeed = tag.getLong("LootTableSeed");
            return true;
        }
        return false;
    }

    public boolean trySaveLootTable(NBTTagCompound tag) {
        if (lootTable == null) return false;
        tag.setString("LootTable", lootTable.toString());
        if (lootTableSeed != 0) tag.setLong("LootTableSeed", lootTableSeed);
        return true;
    }

    public void setLootTable(ResourceLocation table, long seed) {
        lootTable = table;
        lootTableSeed = seed;
    }

    public void unpackLootTable(EntityPlayer player) {
        if (lootTable == null || worldObj == null || worldObj.isRemote) return;
        ResourceLocation t = lootTable;
        lootTable = null;
        LootTables.fill(this, t, lootTableSeed == 0 ? worldObj.rand : new java.util.Random(lootTableSeed));
        markDirty();
    }

    protected abstract NonNullList<ItemStack> getItems();

    protected abstract void setItems(NonNullList<ItemStack> items);

    public abstract int getContainerSize();

    protected abstract Component getDefaultName();

    public Component getDisplayName() {
        return getDefaultName();
    }

    public Component getName() {
        return getDisplayName();
    }

    protected Container createMenu(int id, InventoryPlayer inventory) {
        return null;
    }

    @Override
    public Container createMenu(int id, InventoryPlayer inventory, EntityPlayer player) {
        unpackLootTable(player);
        return createMenu(id, inventory);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean isEmpty() {
        for (ItemStack s : getItems()) if (M.legacy(s) != null) return false;
        return true;
    }

    public ItemStack getItem(int slot) {
        unpackLootTable(null);
        return M.stack(getItems().get(slot));
    }

    public ItemStack removeItem(int slot, int amount) {
        unpackLootTable(null);
        ItemStack s = net.mcreator.boh.compat.mc.world.ContainerHelper.removeItem(getItems(), slot, amount);
        if (M.legacy(s) != null) setChanged();
        return s;
    }

    public void setItem(int slot, ItemStack stack) {
        unpackLootTable(null);
        getItems().set(slot, M.stack(stack));
        setChanged();
    }

    public boolean stillValid(EntityPlayer player) {
        return worldObj != null && worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
            && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    public void clearContent() {
        getItems().clear();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return LazyOptional.empty();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        return getCapability(cap, null);
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("ForgeData")) persistentData = tag.getCompoundTag("ForgeData");
        load(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (persistentData != null) tag.setTag("ForgeData", persistentData);
        saveAdditional(tag);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound t = getUpdateTag();
        return t == null ? null : new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, t);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        load(pkt.func_148857_g());
    }

    @Override
    public void invalidate() {
        super.invalidate();
        setRemoved();
    }

    @Override
    public void validate() {
        super.validate();
        clearRemoved();
    }

    @Override
    public int getSizeInventory() {
        return getContainerSize();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return M.legacy(getItem(slot));
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return M.legacy(removeItem(slot, amount));
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack s = getItems().get(slot);
        getItems().set(slot, M.EMPTY);
        return M.legacy(s);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        setItem(slot, stack);
    }

    @Override
    public String getInventoryName() {
        return getDisplayName().getString();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return getMaxStackSize();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p) {
        return stillValid(p);
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return canPlaceItem(slot, M.stack(stack));
    }
}
