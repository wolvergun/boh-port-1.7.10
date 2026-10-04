package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.InkDemonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HurtByInkDemonProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(
                event,
                M.level(M.getEntity(event)),
                M.getX(M.getEntity(event)),
                M.getY(M.getEntity(event)),
                M.getZ(M.getEntity(event)),
                M.getEntity(event),
                M.getEntity(M.getSource(event))
            );
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, MobEffects.SATURATION) && sourceentity instanceof InkDemonEntity) {
                if (sourceentity instanceof InkDemonEntity) {
                    ((InkDemonEntity)sourceentity).setAnimation("grab");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }

                if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 254, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 100, 254, false, false));
                }

                if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 100, 254, false, false));
                }

                BohMod.queueServerWork(
                    14,
                    () -> {
                        if (sourceentity instanceof InkDemonEntity) {
                            ((InkDemonEntity)sourceentity).setAnimation("choke");
                        }

                        BohMod.queueServerWork(
                            40,
                            () -> {
                                M.hurt(
                                    entity,
                                    M.new_DamageSource(
                                        M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC),
                                        sourceentity
                                    ),
                                    20.0F
                                );
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
                                        "/particle minecraft:block redstone_block ~ ~1.6 ~ 0.2 0.2 0.2 .05 50 force"
                                    );
                                }

                                if (world instanceof World) {
                                    if (!M.isClientSide(world)) {
                                        M.playSound(
                                            world,
                                            null,
                                            BlockPos.containing(x, y, z),
                                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:xenomorph_kill")),
                                            SoundSource.HOSTILE,
                                            1.0F,
                                            1.0F
                                        );
                                    } else {
                                        M.playLocalSound(
                                            world,
                                            x,
                                            y,
                                            z,
                                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:xenomorph_kill")),
                                            SoundSource.HOSTILE,
                                            1.0F,
                                            1.0F,
                                            false
                                        );
                                    }
                                }
                            }
                        );
                    }
                );
            }

            if (!(sourceentity instanceof EntityLivingBase _livEnt14 && M.hasEffect(_livEnt14, MobEffects.SATURATION))
                && sourceentity instanceof InkDemonEntity
                && sourceentity instanceof InkDemonEntity) {
                ((InkDemonEntity)sourceentity).setAnimation("attack");
            }
        }
    }
}
