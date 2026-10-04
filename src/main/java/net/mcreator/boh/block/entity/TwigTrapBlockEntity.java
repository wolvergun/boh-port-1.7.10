package net.mcreator.boh.block.entity;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.items.IItemHandler;
import net.mcreator.boh.compat.forge.items.wrapper.SidedInvWrapper;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.mcreator.boh.compat.mc.world.ContainerHelper;
import net.mcreator.boh.compat.mc.world.WorldlyContainer;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.ChestMenu;
import net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.init.BohModBlockEntities;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TwigTrapBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, M.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public TwigTrapBlockEntity(BlockPos position, BlockState state) {
        super(BohModBlockEntities.TWIG_TRAP.get(), position, state);
    }

    @Override
    public void load(NBTTagCompound compound) {
        super.load(compound);
        if (!M.tryLoadLootTable(this, compound)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), M.EMPTY);
        }

        ContainerHelper.loadAllItems(compound, this.stacks);
    }

    @Override
    public void saveAdditional(NBTTagCompound compound) {
        super.saveAdditional(compound);
        if (!M.trySaveLootTable(this, compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return M.saveWithFullMetadata(this);
    }

    @Override
    public int getContainerSize() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!M.isEmpty(itemstack)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("twig_trap");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    public AbstractContainerMenu createMenu(int id, InventoryPlayer inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Twig Trap");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return !this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER
            ? this.handlers[facing.ordinal()].cast()
            : super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
            M.invalidate(handler);
        }
    }

    public TwigTrapBlockEntity() {
        this(BlockPos.ZERO, null);
    }
}
