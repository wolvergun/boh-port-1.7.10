package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SouichiEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class SouichiOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.1) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute as @s anchored eyes run particle minecraft:flame ^-.2 ^0.7 ^.4");
                }
            }
            if (Math.random() < 0.1) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute as @s anchored eyes run particle minecraft:flame ^.2 ^0.7 ^.4");
                }
            }
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(12.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true)) && entityiterator instanceof EntityPlayer && Math.random() < 0.025 && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                    if (M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.OAK_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.SPRUCE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.BIRCH_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.JUNGLE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.ACACIA_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.DARK_OAK_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.MANGROVE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.CHERRY_LOG) {
                        if (M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.OAK_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.SPRUCE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.BIRCH_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.JUNGLE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.ACACIA_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.DARK_OAK_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.MANGROVE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.CHERRY_LOG) {
                            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.OAK_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.SPRUCE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.BIRCH_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.JUNGLE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.ACACIA_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.DARK_OAK_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.MANGROVE_LOG && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.CHERRY_LOG) {
                                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.OAK_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.SPRUCE_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.BIRCH_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.JUNGLE_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.ACACIA_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.DARK_OAK_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.MANGROVE_LOG || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.CHERRY_LOG) {
                                    if (entity instanceof SouichiEntity) {
                                        ((SouichiEntity) entity).setAnimation("hammer");
                                    }
                                    Entity _ent = entity;
                                    M.teleportTo(_ent, x, y, z);
                                    if (_ent instanceof EntityPlayerMP _serverPlayer) {
                                        M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                                    }
                                    M.lookAt(entity, Anchor.EYES, new Vec3(x, y + 1.0, z - 1.0));
                                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                                    }
                                    if (world instanceof World _level) {
                                        if (!M.isClientSide(_level)) {
                                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                        } else {
                                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                        }
                                    }
                                    M.levelEvent(world, 2001, BlockPos.containing(x, y + 1.0, z - 1.0), M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))));
                                    if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                                        M.setBlock(world, BlockPos.containing(x, y + 1.0, z), (new Object() {

                                            public BlockState with(BlockState _bs, Direction newValue) {
                                                if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp && M.contains(M.getPossibleValues(_dp), newValue)) {
                                                    return (BlockState) M.setValue(_bs, _dp, newValue);
                                                } else {
                                                    return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.contains(M.getPossibleValues(_ep), newValue.getAxis()) ? (BlockState) M.setValue(_bs, _ep, newValue.getAxis()) : _bs;
                                                }
                                            }
                                        }).with(M.defaultBlockState(((Block) BohModBlocks.VOODOO_DOLL.get())), Direction.SOUTH), 3);
                                    }
                                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                                    }
                                }
                            } else {
                                if (entity instanceof SouichiEntity) {
                                    ((SouichiEntity) entity).setAnimation("hammer");
                                }
                                Entity _ent = entity;
                                M.teleportTo(_ent, x, y, z);
                                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                                    M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                                }
                                M.lookAt(entity, Anchor.EYES, new Vec3(x, y + 1.0, z + 1.0));
                                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                                }
                                if (world instanceof World _level) {
                                    if (!M.isClientSide(_level)) {
                                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }
                                M.levelEvent(world, 2001, BlockPos.containing(x, y + 1.0, z + 1.0), M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))));
                                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                                    M.setBlock(world, BlockPos.containing(x, y + 1.0, z), (new Object() {

                                        public BlockState with(BlockState _bs, Direction newValue) {
                                            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp && M.contains(M.getPossibleValues(_dp), newValue)) {
                                                return (BlockState) M.setValue(_bs, _dp, newValue);
                                            } else {
                                                return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.contains(M.getPossibleValues(_ep), newValue.getAxis()) ? (BlockState) M.setValue(_bs, _ep, newValue.getAxis()) : _bs;
                                            }
                                        }
                                    }).with(M.defaultBlockState(((Block) BohModBlocks.VOODOO_DOLL.get())), Direction.NORTH), 3);
                                }
                                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                                }
                            }
                        } else {
                            if (entity instanceof SouichiEntity) {
                                ((SouichiEntity) entity).setAnimation("hammer");
                            }
                            Entity _ent = entity;
                            M.teleportTo(_ent, x, y, z);
                            if (_ent instanceof EntityPlayerMP _serverPlayer) {
                                M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                            }
                            M.lookAt(entity, Anchor.EYES, new Vec3(x - 1.0, y + 1.0, z));
                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                            }
                            if (world instanceof World _level) {
                                if (!M.isClientSide(_level)) {
                                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }
                            M.levelEvent(world, 2001, BlockPos.containing(x - 1.0, y + 1.0, z), M.blockStateId(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))));
                            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                                M.setBlock(world, BlockPos.containing(x, y + 1.0, z), (new Object() {

                                    public BlockState with(BlockState _bs, Direction newValue) {
                                        if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp && M.contains(M.getPossibleValues(_dp), newValue)) {
                                            return (BlockState) M.setValue(_bs, _dp, newValue);
                                        } else {
                                            return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.contains(M.getPossibleValues(_ep), newValue.getAxis()) ? (BlockState) M.setValue(_bs, _ep, newValue.getAxis()) : _bs;
                                        }
                                    }
                                }).with(M.defaultBlockState(((Block) BohModBlocks.VOODOO_DOLL.get())), Direction.EAST), 3);
                            }
                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                            }
                        }
                    } else {
                        if (entity instanceof SouichiEntity) {
                            ((SouichiEntity) entity).setAnimation("hammer");
                        }
                        Entity _ent = entity;
                        M.teleportTo(_ent, x, y, z);
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(_ent), M.getXRot(_ent));
                        }
                        M.lookAt(entity, Anchor.EYES, new Vec3(x + 1.0, y + 1.0, z));
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                        }
                        if (world instanceof World _level) {
                            if (!M.isClientSide(_level)) {
                                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            } else {
                                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                            }
                        }
                        M.levelEvent(world, 2001, BlockPos.containing(x + 1.0, y + 1.0, z), M.blockStateId(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))));
                        if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                            M.setBlock(world, BlockPos.containing(x, y + 1.0, z), (new Object() {

                                public BlockState with(BlockState _bs, Direction newValue) {
                                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp && M.contains(M.getPossibleValues(_dp), newValue)) {
                                        return (BlockState) M.setValue(_bs, _dp, newValue);
                                    } else {
                                        return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.contains(M.getPossibleValues(_ep), newValue.getAxis()) ? (BlockState) M.setValue(_bs, _ep, newValue.getAxis()) : _bs;
                                    }
                                }
                            }).with(M.defaultBlockState(((Block) BohModBlocks.VOODOO_DOLL.get())), Direction.WEST), 3);
                        }
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                        }
                    }
                }
            }
        }
    }
}
