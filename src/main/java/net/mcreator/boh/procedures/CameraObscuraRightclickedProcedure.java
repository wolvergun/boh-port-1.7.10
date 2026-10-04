package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.level.ClipBlock;
import net.mcreator.boh.compat.mc.world.level.ClipContext;
import net.mcreator.boh.compat.mc.world.level.ClipFluid;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CameraObscuraRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null && !(entity instanceof EntityPlayer _plrCldCheck1 && M.isOnCooldown(M.getCooldowns(_plrCldCheck1), M.getItem(itemstack)))) {
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 350);
            }

            if (M.hurt(itemstack, 1, RandomSource.create(), null)) {
                M.shrink(itemstack, 1);
                M.setDamageValue(itemstack, 0);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:camera_obscura")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:camera_obscura")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            Vec3 _center = new Vec3(
                M.getX(
                    M.getBlockPos(
                        M.clip(
                            M.level(entity),
                            new ClipContext(
                                M.getEyePosition(entity, 1.0F),
                                M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(2.0)),
                                ClipBlock.OUTLINE,
                                ClipFluid.NONE,
                                entity
                            )
                        )
                    )
                ),
                M.getY(
                    M.getBlockPos(
                        M.clip(
                            M.level(entity),
                            new ClipContext(
                                M.getEyePosition(entity, 1.0F),
                                M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(2.0)),
                                ClipBlock.OUTLINE,
                                ClipFluid.NONE,
                                entity
                            )
                        )
                    )
                ),
                M.getZ(
                    M.getBlockPos(
                        M.clip(
                            M.level(entity),
                            new ClipContext(
                                M.getEyePosition(entity, 1.0F),
                                M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(2.0)),
                                ClipBlock.OUTLINE,
                                ClipFluid.NONE,
                                entity
                            )
                        )
                    )
                )
            );

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (M.getItem(entityiterator instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != M.getItem(itemstack)) {
                    if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 200, 255));
                    }

                    if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 200, 255));
                    }

                    if (!M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entityiterator)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entityiterator),
                                M.getRotationVector(entityiterator),
                                M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                4,
                                M.getString(M.getName(entityiterator)),
                                M.getDisplayName(entityiterator),
                                M.getServer(M.level(entityiterator)),
                                entityiterator
                            ),
                            "effect give @s kurolib:flashbanged 0 20"
                        );
                    }

                    if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                            "effect give @s kurolib:flashbanged 0 20"
                        );
                    }
                }
            }
        }
    }
}
