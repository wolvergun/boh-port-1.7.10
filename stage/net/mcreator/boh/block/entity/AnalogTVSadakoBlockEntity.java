package net.mcreator.boh.block.entity;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlockEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.mcreator.boh.compat.mc.world.ContainerHelper;
import net.mcreator.boh.compat.mc.world.WorldlyContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.ChestMenu;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.items.IItemHandler;
import net.mcreator.boh.compat.forge.items.wrapper.SidedInvWrapper;
import net.mcreator.boh.compat.M;

public class AnalogTVSadakoBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {

    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, M.EMPTY);

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public AnalogTVSadakoBlockEntity(BlockPos position, BlockState state) {
        super((BlockEntityType) BohModBlockEntities.ANALOG_TV_SADAKO.get(), position, state);
    }

    public void load(NBTTagCompound compound) {
        super.load(compound);
        if (!M.tryLoadLootTable(this, compound)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), M.EMPTY);
        }
        ContainerHelper.loadAllItems(compound, this.stacks);
    }

    public void saveAdditional(NBTTagCompound compound) {
        super.saveAdditional(compound);
        if (!M.trySaveLootTable(this, compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public NBTTagCompound getUpdateTag() {
        return M.saveWithFullMetadata(this);
    }

    public int getContainerSize() {
        return this.stacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!M.isEmpty(itemstack)) {
                return false;
            }
        }
        return true;
    }

    public Component getDefaultName() {
        return Component.literal("analog_tv_sadako");
    }

    public int getMaxStackSize() {
        return 64;
    }

    public AbstractContainerMenu createMenu(int id, InventoryPlayer inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    public Component getDisplayName() {
        return Component.literal("Analog TV");
    }

    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return !this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER ? this.handlers[facing.ordinal()].cast() : super.getCapability(capability, facing);
    }

    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
            M.invalidate(handler);
        }
    }

    public AnalogTVSadakoBlockEntity() {
        this(BlockPos.ZERO, null);
    }
}
