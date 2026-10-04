package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.arguments.Anchor;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SouichiEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SouichiOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.1 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "/execute as @s anchored eyes run particle minecraft:flame ^-.2 ^0.7 ^.4"
                );
            }

            if (Math.random() < 0.1 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "/execute as @s anchored eyes run particle minecraft:flame ^.2 ^0.7 ^.4"
                );
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(12.5), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true))
                    && entityiterator instanceof EntityPlayer
                    && Math.random() < 0.025
                    && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                    if (M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.OAK_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.SPRUCE_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.BIRCH_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.JUNGLE_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.ACACIA_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.DARK_OAK_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.MANGROVE_LOG
                        && M.getBlock(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z))) != Blocks.CHERRY_LOG) {
                        if (M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.OAK_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.SPRUCE_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.BIRCH_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.JUNGLE_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.ACACIA_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.DARK_OAK_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.MANGROVE_LOG
                            && M.getBlock(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z))) != Blocks.CHERRY_LOG) {
                            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.OAK_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.SPRUCE_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.BIRCH_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.JUNGLE_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.ACACIA_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.DARK_OAK_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.MANGROVE_LOG
                                && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0))) != Blocks.CHERRY_LOG) {
                                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.OAK_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.SPRUCE_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.BIRCH_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.JUNGLE_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.ACACIA_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.DARK_OAK_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.MANGROVE_LOG
                                    || M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0))) == Blocks.CHERRY_LOG) {
                                    if (entity instanceof SouichiEntity) {
                                        ((SouichiEntity)entity).setAnimation("hammer");
                                    }

                                    M.teleportTo(entity, x, y, z);
                                    if (entity instanceof EntityPlayerMP _serverPlayer) {
                                        M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(entity), M.getXRot(entity));
                                    }

                                    M.lookAt(entity, Anchor.EYES, new Vec3(x, y + 1.0, z - 1.0));
                                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                                    }

                                    if (world instanceof World) {
                                        if (!M.isClientSide(world)) {
                                            M.playSound(
                                                world,
                                                null,
                                                BlockPos.containing(x, y, z),
                                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                                SoundSource.BLOCKS,
                                                1.0F,
                                                1.0F
                                            );
                                        } else {
                                            M.playLocalSound(
                                                world,
                                                x,
                                                y,
                                                z,
                                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                                SoundSource.BLOCKS,
                                                1.0F,
                                                1.0F,
                                                false
                                            );
                                        }
                                    }

                                    M.levelEvent(
                                        world,
                                        2001,
                                        BlockPos.containing(x, y + 1.0, z - 1.0),
                                        M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z - 1.0)))
                                    );
                                    if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                                        M.setBlock(
                                            world,
                                            BlockPos.containing(x, y + 1.0, z),
                                            (new Object() {
                                                    public BlockState with(BlockState _bs, Direction newValue) {
                                                        if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp
                                                            && M.contains(M.getPossibleValues(_dp), newValue)) {
                                                            return M.setValue(_bs, _dp, newValue);
                                                        } else {
                                                            return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep
                                                                    && M.contains(M.getPossibleValues(_ep), newValue.getAxis())
                                                                ? M.setValue(_bs, _ep, newValue.getAxis())
                                                                : _bs;
                                                        }
                                                    }
                                                })
                                                .with(M.defaultBlockState(BohModBlocks.VOODOO_DOLL.get()), Direction.SOUTH),
                                            3
                                        );
                                    }

                                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                                    }
                                }
                            } else {
                                if (entity instanceof SouichiEntity) {
                                    ((SouichiEntity)entity).setAnimation("hammer");
                                }

                                M.teleportTo(entity, x, y, z);
                                if (entity instanceof EntityPlayerMP _serverPlayer) {
                                    M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(entity), M.getXRot(entity));
                                }

                                M.lookAt(entity, Anchor.EYES, new Vec3(x, y + 1.0, z + 1.0));
                                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                                }

                                if (world instanceof World) {
                                    if (!M.isClientSide(world)) {
                                        M.playSound(
                                            world,
                                            null,
                                            BlockPos.containing(x, y, z),
                                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                            SoundSource.BLOCKS,
                                            1.0F,
                                            1.0F
                                        );
                                    } else {
                                        M.playLocalSound(
                                            world,
                                            x,
                                            y,
                                            z,
                                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                            SoundSource.BLOCKS,
                                            1.0F,
                                            1.0F,
                                            false
                                        );
                                    }
                                }

                                M.levelEvent(
                                    world,
                                    2001,
                                    BlockPos.containing(x, y + 1.0, z + 1.0),
                                    M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z + 1.0)))
                                );
                                if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                                    M.setBlock(
                                        world,
                                        BlockPos.containing(x, y + 1.0, z),
                                        (new Object() {
                                                public BlockState with(BlockState _bs, Direction newValue) {
                                                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp
                                                        && M.contains(M.getPossibleValues(_dp), newValue)) {
                                                        return M.setValue(_bs, _dp, newValue);
                                                    } else {
                                                        return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep
                                                                && M.contains(M.getPossibleValues(_ep), newValue.getAxis())
                                                            ? M.setValue(_bs, _ep, newValue.getAxis())
                                                            : _bs;
                                                    }
                                                }
                                            })
                                            .with(M.defaultBlockState(BohModBlocks.VOODOO_DOLL.get()), Direction.NORTH),
                                        3
                                    );
                                }

                                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                                }
                            }
                        } else {
                            if (entity instanceof SouichiEntity) {
                                ((SouichiEntity)entity).setAnimation("hammer");
                            }

                            M.teleportTo(entity, x, y, z);
                            if (entity instanceof EntityPlayerMP _serverPlayer) {
                                M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(entity), M.getXRot(entity));
                            }

                            M.lookAt(entity, Anchor.EYES, new Vec3(x - 1.0, y + 1.0, z));
                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                            }

                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                        SoundSource.BLOCKS,
                                        1.0F,
                                        1.0F
                                    );
                                } else {
                                    M.playLocalSound(
                                        world,
                                        x,
                                        y,
                                        z,
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                        SoundSource.BLOCKS,
                                        1.0F,
                                        1.0F,
                                        false
                                    );
                                }
                            }

                            M.levelEvent(
                                world,
                                2001,
                                BlockPos.containing(x - 1.0, y + 1.0, z),
                                M.blockStateId(M.getBlockState(world, BlockPos.containing(x - 1.0, y + 1.0, z)))
                            );
                            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                                M.setBlock(
                                    world,
                                    BlockPos.containing(x, y + 1.0, z),
                                    (new Object() {
                                            public BlockState with(BlockState _bs, Direction newValue) {
                                                if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp
                                                    && M.contains(M.getPossibleValues(_dp), newValue)) {
                                                    return M.setValue(_bs, _dp, newValue);
                                                } else {
                                                    return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep
                                                            && M.contains(M.getPossibleValues(_ep), newValue.getAxis())
                                                        ? M.setValue(_bs, _ep, newValue.getAxis())
                                                        : _bs;
                                                }
                                            }
                                        })
                                        .with(M.defaultBlockState(BohModBlocks.VOODOO_DOLL.get()), Direction.EAST),
                                    3
                                );
                            }

                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 600, 0, false, false));
                            }
                        }
                    } else {
                        if (entity instanceof SouichiEntity) {
                            ((SouichiEntity)entity).setAnimation("hammer");
                        }

                        M.teleportTo(entity, x, y, z);
                        if (entity instanceof EntityPlayerMP _serverPlayer) {
                            M.teleport(M.connection(_serverPlayer), x, y, z, M.getYRot(entity), M.getXRot(entity));
                        }

                        M.lookAt(entity, Anchor.EYES, new Vec3(x + 1.0, y + 1.0, z));
                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                        }

                        if (world instanceof World) {
                            if (!M.isClientSide(world)) {
                                M.playSound(
                                    world,
                                    null,
                                    BlockPos.containing(x, y, z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                    SoundSource.BLOCKS,
                                    1.0F,
                                    1.0F
                                );
                            } else {
                                M.playLocalSound(
                                    world,
                                    x,
                                    y,
                                    z,
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.break")),
                                    SoundSource.BLOCKS,
                                    1.0F,
                                    1.0F,
                                    false
                                );
                            }
                        }

                        M.levelEvent(
                            world,
                            2001,
                            BlockPos.containing(x + 1.0, y + 1.0, z),
                            M.blockStateId(M.getBlockState(world, BlockPos.containing(x + 1.0, y + 1.0, z)))
                        );
                        if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y + 1.0, z))) != BohModBlocks.VOODOO_DOLL.get()) {
                            M.setBlock(
                                world,
                                BlockPos.containing(x, y + 1.0, z),
                                (new Object() {
                                        public BlockState with(BlockState _bs, Direction newValue) {
                                            if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp
                                                && M.contains(M.getPossibleValues(_dp), newValue)) {
                                                return M.setValue(_bs, _dp, newValue);
                                            } else {
                                                return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep
                                                        && M.contains(M.getPossibleValues(_ep), newValue.getAxis())
                                                    ? M.setValue(_bs, _ep, newValue.getAxis())
                                                    : _bs;
                                            }
                                        }
                                    })
                                    .with(M.defaultBlockState(BohModBlocks.VOODOO_DOLL.get()), Direction.WEST),
                                3
                            );
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
