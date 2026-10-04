package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.forge.event.entity.player.RightClickBlock;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class StripBOHWoodProcedure {

    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event) {
        if (M.getHand(event) == M.getUsedItemHand(M.getEntity(event))) {
            execute(event, M.getLevel(event), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getEntity(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.WOODEN_AXE || M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.STONE_AXE || M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.IRON_AXE || M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.GOLDEN_AXE || M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.DIAMOND_AXE || M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.NETHERITE_AXE) {
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.BLACK_WALNUT_LOG.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.BLACK_WALNUT_STRIPPED_LOG.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var18 = M.getValues(_bso).entrySet().iterator();
                    while (var18.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var18.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var29) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.DOGWOOD_LOG.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.DOGWOOD_STRIPPED_LOG.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var83 = M.getValues(_bso).entrySet().iterator();
                    while (var83.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var83.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var28) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SASSAFRAS_LOG.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.SASSAFRAS_STRIPPED_LOG.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var84 = M.getValues(_bso).entrySet().iterator();
                    while (var84.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var84.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var27) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.BLACK_WALNUT_WOOD.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.BLACK_WALNUT_STRIPPED_WOOD.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var85 = M.getValues(_bso).entrySet().iterator();
                    while (var85.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var85.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var26) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.DOGWOOD_WOOD.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.DOGWOOD_STRIPPED_WOOD.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var86 = M.getValues(_bso).entrySet().iterator();
                    while (var86.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var86.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var25) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SASSAFRAS_WOOD.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.SASSAFRAS_STRIPPED_WOOD.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var87 = M.getValues(_bso).entrySet().iterator();
                    while (var87.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var87.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var24) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SINISTREE_WOOD.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.SINISTREE_STRIPPED_WOOD.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var88 = M.getValues(_bso).entrySet().iterator();
                    while (var88.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var88.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var23) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SINISTREE_LOG.get()) {
                    if (entity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    ItemStack _ist = entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                    if (world instanceof World _level && M.isClientSide(_level)) {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.SINISTREE_STRIPPED_LOG.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var89 = M.getValues(_bso).entrySet().iterator();
                    while (var89.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var89.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var22) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
            }
        }
    }
}
