package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class AnalogTVOnBlockRightClickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_on")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == M.getItem(M.EMPTY)) {
                if (Math.random() < 0.1) {
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.ANALOG_TV_SIX.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var13 = M.getValues(_bso).entrySet().iterator();
                    while (var13.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var13.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var19) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                } else {
                    BlockPos _bp = BlockPos.containing(x, y, z);
                    BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.ANALOG_TV_STATIC.get()));
                    BlockState _bso = M.getBlockState(world, _bp);
                    UnmodifiableIterator var30 = M.getValues(_bso).entrySet().iterator();
                    while (var30.hasNext()) {
                        Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var30.next();
                        Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                        if (_property != null && _bs.getValue(_property) != null) {
                            try {
                                _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                            } catch (Exception var18) {
                            }
                        }
                    }
                    M.setBlock(world, _bp, _bs, 3);
                }
            } else if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == BohModItems.VHS_TAPE.get()) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_put_tape")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tv_put_tape")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockState _bs = M.defaultBlockState(((Block) BohModBlocks.ANALOG_TV_SADAKO.get()));
                BlockState _bso = M.getBlockState(world, _bp);
                UnmodifiableIterator var31 = M.getValues(_bso).entrySet().iterator();
                while (var31.hasNext()) {
                    Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var31.next();
                    Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                    if (_property != null && _bs.getValue(_property) != null) {
                        try {
                            _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                        } catch (Exception var17) {
                        }
                    }
                }
                M.setBlock(world, _bp, _bs, 3);
                if (entity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove = M.new_ItemStack(BohModItems.VHS_TAPE.get());
                    M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
                }
                BohMod.queueServerWork(6000, () -> {
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.egg")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.egg")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y + 1.0, z, M.new_ItemStack(BohModItems.VHS_TAPE.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                });
            }
        }
    }
}
