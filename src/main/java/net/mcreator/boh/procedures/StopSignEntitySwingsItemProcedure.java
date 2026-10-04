package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.item.StopSignItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class StopSignEntitySwingsItemProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null && !(entity instanceof EntityPlayer _plrCldCheck1 && M.isOnCooldown(M.getCooldowns(_plrCldCheck1), M.getItem(itemstack)))) {
            M.putDouble(M.getPersistentData(entity), "stop_sign", M.getDouble(M.getPersistentData(entity), "stop_sign") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "stop_sign") == 1.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stopsign_charge")),
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stopsign_charge")),
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if (entity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 20);
                }

                if (M.getItem(itemstack) instanceof StopSignItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "hold");
                }
            } else if (M.getDouble(M.getPersistentData(entity), "stop_sign") == 2.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stopsign_swing")),
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stopsign_swing")),
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                BohMod.queueServerWork(
                    5,
                    () -> {
                        for (int index0 = 0; index0 < 5; index0++) {
                            M.levelEvent(
                                world,
                                2001,
                                BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -2, 2), y, z + Mth.nextInt(RandomSource.create(), -2, 2)),
                                M.blockStateId(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z)))
                            );
                        }

                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (M.getItem(entityiterator instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != M.getItem(itemstack)) {
                                M.hurt(
                                    entityiterator,
                                    M.new_DamageSource(
                                        M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)
                                    ),
                                    15.0F
                                );
                                if (world instanceof World) {
                                    if (!M.isClientSide(world)) {
                                        M.playSound(
                                            world,
                                            null,
                                            BlockPos.containing(x, y, z),
                                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stopsign_hit")),
                                            SoundSource.PLAYERS,
                                            1.0F,
                                            1.0F
                                        );
                                    } else {
                                        M.playLocalSound(
                                            world,
                                            x,
                                            y,
                                            z,
                                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stopsign_hit")),
                                            SoundSource.PLAYERS,
                                            1.0F,
                                            1.0F,
                                            false
                                        );
                                    }
                                }

                                if (M.hurt(itemstack, 2, RandomSource.create(), null)) {
                                    M.shrink(itemstack, 1);
                                    M.setDamageValue(itemstack, 0);
                                }
                            }
                        }
                    }
                );
                M.putDouble(M.getPersistentData(entity), "stop_sign", 0.0);
                if (entity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 100);
                }

                if (M.getItem(itemstack) instanceof StopSignItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "release");
                }
            }
        }
    }
}
