package net.mcreator.boh.procedures;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.tags.ItemTags;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.forge.common.capabilities.ForgeCapabilities;
import net.mcreator.boh.compat.forge.items.IItemHandlerModifiable;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class ComputerOnTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, BlockState blockstate) {
        if (M.getItem((new Object() {

            public ItemStack getItemStack(World world, BlockPos pos, int slotid) {
                AtomicReference<ItemStack> _retval = new AtomicReference<>(M.EMPTY);
                TileEntity _ent = M.getBlockEntity(world, pos);
                if (_ent != null) {
                    M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> M.set(_retval, M.copy(M.getStackInSlot(capability, slotid))));
                }
                return _retval.get();
            }
        }).getItemStack(world, BlockPos.containing(x, y, z), 0)) == Items.BLACK_DYE && M.getItem((new Object() {

            public ItemStack getItemStack(World world, BlockPos pos, int slotid) {
                AtomicReference<ItemStack> _retval = new AtomicReference<>(M.EMPTY);
                TileEntity _ent = M.getBlockEntity(world, pos);
                if (_ent != null) {
                    M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> M.set(_retval, M.copy(M.getStackInSlot(capability, slotid))));
                }
                return _retval.get();
            }
        }).getItemStack(world, BlockPos.containing(x, y, z), 1)) == BohModItems.HAUNTED_PAPER.get() && ((new Object() {

            public int getAmount(World world, BlockPos pos, int slotid) {
                AtomicInteger _retval = new AtomicInteger(0);
                TileEntity _ent = M.getBlockEntity(world, pos);
                if (_ent != null) {
                    M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> M.set(_retval, M.getCount(M.getStackInSlot(capability, slotid))));
                }
                return _retval.get();
            }
        }).getAmount(world, BlockPos.containing(x, y, z), 2) == 0 || M.getItem((new Object() {

            public ItemStack getItemStack(World world, BlockPos pos, int slotid) {
                AtomicReference<ItemStack> _retval = new AtomicReference<>(M.EMPTY);
                TileEntity _ent = M.getBlockEntity(world, pos);
                if (_ent != null) {
                    M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> M.set(_retval, M.copy(M.getStackInSlot(capability, slotid))));
                }
                return _retval.get();
            }
        }).getItemStack(world, BlockPos.containing(x, y, z), 2)) == M.getItem(M.EMPTY) && (new Object() {

            public int getAmount(World world, BlockPos pos, int slotid) {
                AtomicInteger _retval = new AtomicInteger(0);
                TileEntity _ent = M.getBlockEntity(world, pos);
                if (_ent != null) {
                    M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> M.set(_retval, M.getCount(M.getStackInSlot(capability, slotid))));
                }
                return _retval.get();
            }
        }).getAmount(world, BlockPos.containing(x, y, z), 2) <= 63)) {
            int _value = 23;
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = M.getBlockState(world, _pos);
            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "downloading_time") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _value)) {
                M.setBlock(world, _pos, (BlockState) M.setValue(_bs, _integerProp, _value), 3);
            }
            int _value_r15 = (M.getProperty(M.getStateDefinition(M.getBlock(blockstate)), "downloading_time") instanceof IntegerProperty _getip11 ? (Integer) blockstate.getValue(_getip11) : -1) + 1;
            BlockPos _pos_r16 = BlockPos.containing(x, y, z);
            BlockState _bs_r17 = M.getBlockState(world, _pos_r16);
            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs_r17)), "downloading_time") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _value_r15)) {
                M.setBlock(world, _pos_r16, (BlockState) M.setValue(_bs_r17, _integerProp, _value_r15), 3);
            }
            if (!M.isClientSide(world) && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_load")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_load")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            if ((M.getProperty(M.getStateDefinition(M.getBlock(blockstate)), "downloading_time") instanceof IntegerProperty _getip16 ? (Integer) blockstate.getValue(_getip16) : -1) >= 23) {
                TileEntity _ent = M.getBlockEntity(world, BlockPos.containing(x, y, z));
                if (_ent != null) {
                    int _slotid = 0;
                    int _amount = 1;
                    M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable) {
                            ItemStack _stk = M.copy(M.getStackInSlot(capability, 0));
                            M.shrink(_stk, 1);
                            M.setStackInSlot(((IItemHandlerModifiable) capability), 0, _stk);
                        }
                    });
                }
                TileEntity _entx = M.getBlockEntity(world, BlockPos.containing(x, y, z));
                if (_entx != null) {
                    int _slotid = 1;
                    int _amount = 1;
                    M.getCapability(_entx, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable) {
                            ItemStack _stk = M.copy(M.getStackInSlot(capability, 1));
                            M.shrink(_stk, 1);
                            M.setStackInSlot(((IItemHandlerModifiable) capability), 1, _stk);
                        }
                    });
                }
                TileEntity _entxx = M.getBlockEntity(world, BlockPos.containing(x, y, z));
                if (_entxx != null) {
                    int _slotid = 2;
                    ItemStack _setstack = M.copy(M.new_ItemStack(M.getRandomElement(M.getTag(M.tags(ForgeRegistries.ITEMS), ItemTags.create(new ResourceLocation("forge:boh_documents"))), RandomSource.create()).orElseGet(() -> Items.AIR)));
                    M.setCount(_setstack, (new Object() {

                        public int getAmount(World world, BlockPos pos, int slotid) {
                            AtomicInteger _retval = new AtomicInteger(0);
                            TileEntity _ent = M.getBlockEntity(world, pos);
                            if (_ent != null) {
                                M.getCapability(_ent, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> M.set(_retval, M.getCount(M.getStackInSlot(capability, slotid))));
                            }
                            return _retval.get();
                        }
                    }).getAmount(world, BlockPos.containing(x, y, z), 2) + 1);
                    M.getCapability(_entxx, ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable) {
                            M.setStackInSlot(((IItemHandlerModifiable) capability), 2, _setstack);
                        }
                    });
                }
                if (!M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_complete")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_complete")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
                int _valuex = 0;
                BlockPos _posx = BlockPos.containing(x, y, z);
                BlockState _bsx = M.getBlockState(world, _posx);
                if (M.getProperty(M.getStateDefinition(M.getBlock(_bsx)), "downloading_time") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _valuex)) {
                    M.setBlock(world, _posx, (BlockState) M.setValue(_bsx, _integerProp, _valuex), 3);
                }
            }
        } else {
            int _value = 0;
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = M.getBlockState(world, _pos);
            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "downloading_time") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _value)) {
                M.setBlock(world, _pos, (BlockState) M.setValue(_bs, _integerProp, _value), 3);
            }
        }
        if ((M.getProperty(M.getStateDefinition(M.getBlock(blockstate)), "downloading_time") instanceof IntegerProperty _getip27 ? (Integer) blockstate.getValue(_getip27) : -1) == 0) {
            int _value = 1;
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = M.getBlockState(world, _pos);
            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "blockstate") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _value)) {
                M.setBlock(world, _pos, (BlockState) M.setValue(_bs, _integerProp, _value), 3);
            }
        } else if ((M.getProperty(M.getStateDefinition(M.getBlock(blockstate)), "downloading_time") instanceof IntegerProperty _getip30 ? (Integer) blockstate.getValue(_getip30) : -1) <= 1) {
            int _value = 2;
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = M.getBlockState(world, _pos);
            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "blockstate") instanceof IntegerProperty _integerProp && M.contains(M.getPossibleValues(_integerProp), _value)) {
                M.setBlock(world, _pos, (BlockState) M.setValue(_bs, _integerProp, _value), 3);
            }
        }
    }
}
