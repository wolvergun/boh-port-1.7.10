package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.AxisDirection;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class PipeBurstOnTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, BlockState blockstate) {
        if (Math.random() < 0.2) {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            if ((new Object() {

                public Direction getDirection(BlockState _bs) {
                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp) {
                        return (Direction) _bs.getValue(_dp);
                    } else {
                        return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.getPossibleValues(_ep).toArray()[0] instanceof Axis ? Direction.fromAxisAndDirection((Axis) _bs.getValue(_ep), AxisDirection.POSITIVE) : Direction.NORTH;
                    }
                }
            }).getDirection(blockstate) == Direction.EAST) {
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x + 0.5, y, z + 0.5), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/particle minecraft:poof ~1 ~.5 ~ .6 0 0 .01 5");
                }
                Vec3 _center = new Vec3(x + 1.0, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.ON_FIRE)), 3.0F);
                    }
                }
            } else if ((new Object() {

                public Direction getDirection(BlockState _bs) {
                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp) {
                        return (Direction) _bs.getValue(_dp);
                    } else {
                        return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.getPossibleValues(_ep).toArray()[0] instanceof Axis ? Direction.fromAxisAndDirection((Axis) _bs.getValue(_ep), AxisDirection.POSITIVE) : Direction.NORTH;
                    }
                }
            }).getDirection(blockstate) == Direction.WEST) {
                Vec3 _center = new Vec3(x - 1.0, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.ON_FIRE)), 3.0F);
                    }
                }
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x + 0.5, y, z + 0.5), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/particle minecraft:poof ~-1 ~.5 ~ .6 0 0 .01 5");
                }
            } else if ((new Object() {

                public Direction getDirection(BlockState _bs) {
                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp) {
                        return (Direction) _bs.getValue(_dp);
                    } else {
                        return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.getPossibleValues(_ep).toArray()[0] instanceof Axis ? Direction.fromAxisAndDirection((Axis) _bs.getValue(_ep), AxisDirection.POSITIVE) : Direction.NORTH;
                    }
                }
            }).getDirection(blockstate) == Direction.SOUTH) {
                Vec3 _center = new Vec3(x, y, z + 1.0);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.ON_FIRE)), 3.0F);
                    }
                }
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x + 0.5, y, z + 0.5), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/particle minecraft:poof ~ ~.5 ~1 0 0 0.6 .01 5");
                }
            } else if ((new Object() {

                public Direction getDirection(BlockState _bs) {
                    if (M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "facing") instanceof DirectionProperty _dp) {
                        return (Direction) _bs.getValue(_dp);
                    } else {
                        return M.getProperty(M.getStateDefinition(M.getBlock(_bs)), "axis") instanceof EnumProperty _ep && M.getPossibleValues(_ep).toArray()[0] instanceof Axis ? Direction.fromAxisAndDirection((Axis) _bs.getValue(_ep), AxisDirection.POSITIVE) : Direction.NORTH;
                    }
                }
            }).getDirection(blockstate) == Direction.NORTH) {
                Vec3 _center = new Vec3(x, y, z - 1.0);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.ON_FIRE)), 3.0F);
                    }
                }
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x + 0.5, y, z + 0.5), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/particle minecraft:poof ~ ~.5 ~-1 0 0 0.6 .01 5");
                }
            }
        }
    }
}
