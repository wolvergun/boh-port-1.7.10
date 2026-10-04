package net.mcreator.boh.block.entity;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.mcreator.boh.block.PokerNightBlock;
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
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.GeckoLibUtil;
import net.mcreator.boh.geo.GeoBlockEntity;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.init.BohModBlockEntities;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PokerNightTileEntity extends RandomizableContainerBlockEntity implements GeoBlockEntity, WorldlyContainer {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, M.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
    String prevAnim = "0";

    public PokerNightTileEntity(BlockPos pos, BlockState state) {
        super(BohModBlockEntities.POKER_NIGHT.get(), pos, state);
    }

    private PlayState predicate(AnimationState event) {
        String animationprocedure = M.getBlockState(this).getValue(PokerNightBlock.ANIMATION) + "";
        return animationprocedure.equals("0") ? event.setAndContinue(RawAnimation.begin().thenLoop(animationprocedure)) : PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState event) {
        String animationprocedure = M.getBlockState(this).getValue(PokerNightBlock.ANIMATION) + "";
        if (!animationprocedure.equals("0") && event.getController().getAnimationState() == AnimationController.State.STOPPED
            || !animationprocedure.equals(this.prevAnim) && !animationprocedure.equals("0")) {
            if (!animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }

            event.getController().setAnimation(RawAnimation.begin().thenPlay(animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (M.getProperty(M.getStateDefinition(M.getBlock(M.getBlockState(this))), "animation") instanceof IntegerProperty _integerProp) {
                    M.setBlock(M.level(this), M.getBlockPos(this), M.setValue(M.getBlockState(this), _integerProp, 0), 3);
                }

                event.getController().forceAnimationReset();
            }
        } else if (animationprocedure.equals("0")) {
            this.prevAnim = "0";
            return PlayState.STOP;
        }

        this.prevAnim = animationprocedure;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
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
        return Component.literal("poker_night");
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
        return Component.literal("Poker Night");
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

    public PokerNightTileEntity() {
        this(BlockPos.ZERO, null);
    }
}
