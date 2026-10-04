package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.player.RightClickBlock;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
        if (entity != null
            && (
                M.getItem(entity instanceof EntityLivingBase _livEntxxxxx ? M.getMainHandItem(_livEntxxxxx) : M.EMPTY) == Items.WOODEN_AXE
                    || M.getItem(entity instanceof EntityLivingBase _livEntxxxx ? M.getMainHandItem(_livEntxxxx) : M.EMPTY) == Items.STONE_AXE
                    || M.getItem(entity instanceof EntityLivingBase _livEntxxx ? M.getMainHandItem(_livEntxxx) : M.EMPTY) == Items.IRON_AXE
                    || M.getItem(entity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY) == Items.GOLDEN_AXE
                    || M.getItem(entity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY) == Items.DIAMOND_AXE
                    || M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == Items.NETHERITE_AXE
            )) {
            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.BLACK_WALNUT_LOG.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _ist = entity instanceof EntityLivingBase _livEntxxxxxx ? M.getMainHandItem(_livEntxxxxxx) : M.EMPTY;
                if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                    M.shrink(_ist, 1);
                    M.setDamageValue(_ist, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.BLACK_WALNUT_STRIPPED_LOG.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var18 = M.getValues(_bso).entrySet().iterator();

                while (var18.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var18.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var30) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.DOGWOOD_LOG.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istx = entity instanceof EntityLivingBase _livEntxxxxxxx ? M.getMainHandItem(_livEntxxxxxxx) : M.EMPTY;
                if (M.hurt(_istx, 1, RandomSource.create(), null)) {
                    M.shrink(_istx, 1);
                    M.setDamageValue(_istx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.DOGWOOD_STRIPPED_LOG.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var83 = M.getValues(_bso).entrySet().iterator();

                while (var83.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var83.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var29) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SASSAFRAS_LOG.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istxx = entity instanceof EntityLivingBase _livEntxxxxxxxx ? M.getMainHandItem(_livEntxxxxxxxx) : M.EMPTY;
                if (M.hurt(_istxx, 1, RandomSource.create(), null)) {
                    M.shrink(_istxx, 1);
                    M.setDamageValue(_istxx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.SASSAFRAS_STRIPPED_LOG.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var84 = M.getValues(_bso).entrySet().iterator();

                while (var84.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var84.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var28) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.BLACK_WALNUT_WOOD.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istxxx = entity instanceof EntityLivingBase _livEntxxxxxxxxx ? M.getMainHandItem(_livEntxxxxxxxxx) : M.EMPTY;
                if (M.hurt(_istxxx, 1, RandomSource.create(), null)) {
                    M.shrink(_istxxx, 1);
                    M.setDamageValue(_istxxx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.BLACK_WALNUT_STRIPPED_WOOD.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var85 = M.getValues(_bso).entrySet().iterator();

                while (var85.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var85.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var27) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.DOGWOOD_WOOD.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istxxxx = entity instanceof EntityLivingBase _livEntxxxxxxxxxx ? M.getMainHandItem(_livEntxxxxxxxxxx) : M.EMPTY;
                if (M.hurt(_istxxxx, 1, RandomSource.create(), null)) {
                    M.shrink(_istxxxx, 1);
                    M.setDamageValue(_istxxxx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.DOGWOOD_STRIPPED_WOOD.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var86 = M.getValues(_bso).entrySet().iterator();

                while (var86.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var86.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var26) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SASSAFRAS_WOOD.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istxxxxx = entity instanceof EntityLivingBase _livEntxxxxxxxxxxx ? M.getMainHandItem(_livEntxxxxxxxxxxx) : M.EMPTY;
                if (M.hurt(_istxxxxx, 1, RandomSource.create(), null)) {
                    M.shrink(_istxxxxx, 1);
                    M.setDamageValue(_istxxxxx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.SASSAFRAS_STRIPPED_WOOD.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var87 = M.getValues(_bso).entrySet().iterator();

                while (var87.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var87.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var25) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SINISTREE_WOOD.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istxxxxxx = entity instanceof EntityLivingBase _livEntxxxxxxxxxxxx ? M.getMainHandItem(_livEntxxxxxxxxxxxx) : M.EMPTY;
                if (M.hurt(_istxxxxxx, 1, RandomSource.create(), null)) {
                    M.shrink(_istxxxxxx, 1);
                    M.setDamageValue(_istxxxxxx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.SINISTREE_STRIPPED_WOOD.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var88 = M.getValues(_bso).entrySet().iterator();

                while (var88.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var88.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var24) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SINISTREE_LOG.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }

                ItemStack _istxxxxxxx = entity instanceof EntityLivingBase _livEntxxxxxxxxxxxxx ? M.getMainHandItem(_livEntxxxxxxxxxxxxx) : M.EMPTY;
                if (M.hurt(_istxxxxxxx, 1, RandomSource.create(), null)) {
                    M.shrink(_istxxxxxxx, 1);
                    M.setDamageValue(_istxxxxxxx, 0);
                }

                if (world instanceof World && M.isClientSide(world)) {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                    );
                }

                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(BohModBlocks.SINISTREE_STRIPPED_LOG.get());
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var89 = M.getValues(_bso).entrySet().iterator();

                while (var89.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var89.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var23) {
                        }
                    }
                }

                M.setBlock(world, _bp, _bs, 3);
            }
        }
    }
}
