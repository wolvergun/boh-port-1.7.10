package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.level.ClipContext;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.ClipBlock;
import net.mcreator.boh.compat.mc.world.level.ClipFluid;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class CameraObscuraRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (!(entity instanceof EntityPlayer _plrCldCheck1 && M.isOnCooldown(M.getCooldowns(_plrCldCheck1), M.getItem(itemstack)))) {
                if (entity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 350);
                }
                ItemStack _ist = itemstack;
                if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                    M.shrink(_ist, 1);
                    M.setDamageValue(_ist, 0);
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:camera_obscura")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:camera_obscura")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }
                Vec3 _center = new Vec3(M.getX(M.getBlockPos(M.clip(M.level(entity), new ClipContext(M.getEyePosition(entity, 1.0F), M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(2.0)), ClipBlock.OUTLINE, ClipFluid.NONE, entity)))), M.getY(M.getBlockPos(M.clip(M.level(entity), new ClipContext(M.getEyePosition(entity, 1.0F), M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(2.0)), ClipBlock.OUTLINE, ClipFluid.NONE, entity)))), M.getZ(M.getBlockPos(M.clip(M.level(entity), new ClipContext(M.getEyePosition(entity, 1.0F), M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(2.0)), ClipBlock.OUTLINE, ClipFluid.NONE, entity)))));
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (M.getItem((entityiterator instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) != M.getItem(itemstack)) {
                        if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 200, 255));
                        }
                        if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 200, 255));
                        }
                        Entity _ent = entityiterator;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:flashbanged 0 20");
                        }
                        Entity _ent_r12 = entity;
                        if (!M.isClientSide(M.level(_ent_r12)) && M.getServer(_ent_r12) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r12)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r12), M.getRotationVector(_ent_r12), M.level(_ent_r12) instanceof WorldServer ? (WorldServer) M.level(_ent_r12) : null, 4, M.getString(M.getName(_ent_r12)), M.getDisplayName(_ent_r12), M.getServer(M.level(_ent_r12)), _ent_r12), "effect give @s kurolib:flashbanged 0 20");
                        }
                    }
                }
            }
        }
    }
}
