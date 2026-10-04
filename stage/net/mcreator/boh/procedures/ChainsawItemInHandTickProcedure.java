package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.item.ChainsawItem;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class ChainsawItemInHandTickProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.isSprinting(entity)) {
                if (Math.random() < 0.5) {
                    ItemStack _ist = itemstack;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }
                }
                if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == M.getItem(itemstack)) {
                    if (Math.random() < 0.2) {
                        Entity _ent = entity;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute as @s run particle minecraft:smoke ^-.2 ^1.1 ^.5 0.0 0.0 0.0 0.01 1 force");
                        }
                    }
                } else if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getOffhandItem(_livEnt) : M.EMPTY)) == M.getItem(itemstack) && Math.random() < 0.2) {
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute as @s run particle minecraft:smoke ^-.2 ^1.1 ^.5 0.0 0.0 0.0 0.01 1 force");
                    }
                }
                M.putDouble(M.getOrCreateTag(itemstack), "timer_sound", M.getDouble(M.getOrCreateTag(itemstack), "timer_sound") + 1.0);
                if (M.getDouble(M.getOrCreateTag(itemstack), "timer_sound") == 14.0) {
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")), SoundSource.PLAYERS, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                        }
                    }
                    M.putDouble(M.getOrCreateTag(itemstack), "timer_sound", 0.0);
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 20, 1, false, false));
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_RESISTANCE, 20, 2, false, false));
                }
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (M.getItem((entityiterator instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) != M.getItem(itemstack)) {
                        M.hurt(entityiterator, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.PLAYER_ATTACK), entity), 2.5F);
                        if (Math.random() < 0.2) {
                            Entity _ent = entity;
                            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "effect give @s kurolib:bleeding 0 6");
                            }
                        }
                    }
                }
                if (M.getItem(itemstack) instanceof ChainsawItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "active");
                }
            } else if (M.getItem(itemstack) instanceof ChainsawItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "idle");
            }
        }
    }
}
