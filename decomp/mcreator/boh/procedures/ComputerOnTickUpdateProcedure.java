package net.mcreator.boh.procedures;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;

public class ComputerOnTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      if ((new Object() {
         public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
            AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
            BlockEntity _ent = world.getBlockEntity(pos);
            if (_ent != null) {
               _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy()));
            }

            return _retval.get();
         }
      }).getItemStack(world, BlockPos.containing(x, y, z), 0).getItem() == Items.BLACK_DYE && (new Object() {
         public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
            AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
            BlockEntity _ent = world.getBlockEntity(pos);
            if (_ent != null) {
               _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy()));
            }

            return _retval.get();
         }
      }).getItemStack(world, BlockPos.containing(x, y, z), 1).getItem() == BohModItems.HAUNTED_PAPER.get() && ((new Object() {
         public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
            AtomicInteger _retval = new AtomicInteger(0);
            BlockEntity _ent = world.getBlockEntity(pos);
            if (_ent != null) {
               _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).getCount()));
            }

            return _retval.get();
         }
      }).getAmount(world, BlockPos.containing(x, y, z), 2) == 0 || (new Object() {
         public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
            AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
            BlockEntity _ent = world.getBlockEntity(pos);
            if (_ent != null) {
               _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).copy()));
            }

            return _retval.get();
         }
      }).getItemStack(world, BlockPos.containing(x, y, z), 2).getItem() == ItemStack.EMPTY.getItem() && (new Object() {
         public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
            AtomicInteger _retval = new AtomicInteger(0);
            BlockEntity _ent = world.getBlockEntity(pos);
            if (_ent != null) {
               _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).getCount()));
            }

            return _retval.get();
         }
      }).getAmount(world, BlockPos.containing(x, y, z), 2) <= 63)) {
         int _value = 23;
         BlockPos _pos = BlockPos.containing(x, y, z);
         BlockState _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }

         _value = (
               blockstate.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _getip11 ? (Integer)blockstate.getValue(_getip11) : -1
            )
            + 1;
         _pos = BlockPos.containing(x, y, z);
         _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }

         if (!world.isClientSide() && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_load")),
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_load")), SoundSource.BLOCKS, 1.0F, 1.0F, false
               );
            }
         }

         if ((blockstate.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _getip16 ? (Integer)blockstate.getValue(_getip16) : -1)
            >= 23) {
            BlockEntity _ent = world.getBlockEntity(BlockPos.containing(x, y, z));
            if (_ent != null) {
               int _slotid = 0;
               int _amount = 1;
               _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  if (capability instanceof IItemHandlerModifiable) {
                     ItemStack _stk = capability.getStackInSlot(0).copy();
                     _stk.shrink(1);
                     ((IItemHandlerModifiable)capability).setStackInSlot(0, _stk);
                  }
               });
            }

            BlockEntity _entx = world.getBlockEntity(BlockPos.containing(x, y, z));
            if (_entx != null) {
               int _slotid = 1;
               int _amount = 1;
               _entx.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  if (capability instanceof IItemHandlerModifiable) {
                     ItemStack _stk = capability.getStackInSlot(1).copy();
                     _stk.shrink(1);
                     ((IItemHandlerModifiable)capability).setStackInSlot(1, _stk);
                  }
               });
            }

            BlockEntity _entxx = world.getBlockEntity(BlockPos.containing(x, y, z));
            if (_entxx != null) {
               int _slotid = 2;
               ItemStack _setstack = new ItemStack(
                     (ItemLike)ForgeRegistries.ITEMS
                        .tags()
                        .getTag(ItemTags.create(new ResourceLocation("forge:boh_documents")))
                        .getRandomElement(RandomSource.create())
                        .orElseGet(() -> Items.AIR)
                  )
                  .copy();
               _setstack.setCount(
                  (new Object() {
                           public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
                              AtomicInteger _retval = new AtomicInteger(0);
                              BlockEntity _ent = world.getBlockEntity(pos);
                              if (_ent != null) {
                                 _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
                                    .ifPresent(capability -> _retval.set(capability.getStackInSlot(slotid).getCount()));
                              }

                              return _retval.get();
                           }
                        })
                        .getAmount(world, BlockPos.containing(x, y, z), 2)
                     + 1
               );
               _entxx.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  if (capability instanceof IItemHandlerModifiable) {
                     ((IItemHandlerModifiable)capability).setStackInSlot(2, _setstack);
                  }
               });
            }

            if (!world.isClientSide() && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_complete")),
                     SoundSource.BLOCKS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:computer_complete")),
                     SoundSource.BLOCKS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            int _valuex = 0;
            BlockPos _posx = BlockPos.containing(x, y, z);
            BlockState _bsx = world.getBlockState(_posx);
            if (_bsx.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_valuex)) {
               world.setBlock(_posx, (BlockState)_bsx.setValue(_integerProp, _valuex), 3);
            }
         }
      } else {
         int _value = 0;
         BlockPos _pos = BlockPos.containing(x, y, z);
         BlockState _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }
      }

      if ((blockstate.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _getip27 ? (Integer)blockstate.getValue(_getip27) : -1)
         == 0) {
         int _value = 1;
         BlockPos _pos = BlockPos.containing(x, y, z);
         BlockState _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }
      } else if ((
            blockstate.getBlock().getStateDefinition().getProperty("downloading_time") instanceof IntegerProperty _getip30 ? (Integer)blockstate.getValue(_getip30) : -1
         )
         <= 1) {
         int _value = 2;
         BlockPos _pos = BlockPos.containing(x, y, z);
         BlockState _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }
      }
   }
}
