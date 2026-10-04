package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class StripBOHWoodProcedure {
   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getHand() == event.getEntity().getUsedItemHand()) {
         execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.WOODEN_AXE
            || (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.STONE_AXE
            || (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.IRON_AXE
            || (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.GOLDEN_AXE
            || (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.DIAMOND_AXE
            || (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.NETHERITE_AXE) {
            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.BLACK_WALNUT_LOG.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.BLACK_WALNUT_STRIPPED_LOG.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var18 = _bso.getValues().entrySet().iterator();

               while (var18.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var18.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var29) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.DOGWOOD_LOG.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.DOGWOOD_STRIPPED_LOG.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var83 = _bso.getValues().entrySet().iterator();

               while (var83.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var83.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var28) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.SASSAFRAS_LOG.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.SASSAFRAS_STRIPPED_LOG.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var84 = _bso.getValues().entrySet().iterator();

               while (var84.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var84.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var27) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.BLACK_WALNUT_WOOD.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.BLACK_WALNUT_STRIPPED_WOOD.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var85 = _bso.getValues().entrySet().iterator();

               while (var85.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var85.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var26) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.DOGWOOD_WOOD.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.DOGWOOD_STRIPPED_WOOD.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var86 = _bso.getValues().entrySet().iterator();

               while (var86.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var86.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var25) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.SASSAFRAS_WOOD.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.SASSAFRAS_STRIPPED_WOOD.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var87 = _bso.getValues().entrySet().iterator();

               while (var87.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var87.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var24) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.SINISTREE_WOOD.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.SINISTREE_STRIPPED_WOOD.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var88 = _bso.getValues().entrySet().iterator();

               while (var88.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var88.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var23) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.SINISTREE_LOG.get()) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.swing(InteractionHand.MAIN_HAND, true);
               }

               ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }

               if (world instanceof Level _level && _level.isClientSide()) {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }

               BlockPos _bp = BlockPos.containing(x, y, z);
               BlockState _bs = ((Block)BohModBlocks.SINISTREE_STRIPPED_LOG.get()).defaultBlockState();
               BlockState _bso = world.getBlockState(_bp);
               UnmodifiableIterator var89 = _bso.getValues().entrySet().iterator();

               while (var89.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var89.next();
                  Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                  if (_property != null && _bs.getValue(_property) != null) {
                     try {
                        _bs = (BlockState)_bs.setValue(_property, entry.getValue());
                     } catch (Exception var22) {
                     }
                  }
               }

               world.setBlock(_bp, _bs, 3);
            }
         }
      }
   }
}
