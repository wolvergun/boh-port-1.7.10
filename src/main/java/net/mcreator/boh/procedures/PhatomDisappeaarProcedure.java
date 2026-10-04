package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.PhantomBBEntity;
import net.mcreator.boh.entity.PhantomChicaEntity;
import net.mcreator.boh.entity.PhantomFoxyEntity;
import net.mcreator.boh.entity.PhantomFreddyEntity;
import net.mcreator.boh.entity.PhantomMangleEntity;
import net.mcreator.boh.entity.PhantomPuppetEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PhatomDisappeaarProcedure {
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
            Entity ridingEntities = null;
            if (M.is(M.getType(sourceentity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("boh:phantoms")))) {
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
                        "/particle minecraft:squid_ink ~ ~ ~ 0.55 .55 0.55 0 100"
                    );
                }

                if (!M.isClientSide(M.level(sourceentity))) {
                    M.discard(sourceentity);
                }

                if (sourceentity instanceof PhantomBBEntity) {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_bb_laugh")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_bb_laugh")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                }

                if (!(sourceentity instanceof PhantomFoxyEntity) && !(sourceentity instanceof PhantomBBEntity)) {
                    if (!(sourceentity instanceof PhantomFreddyEntity) && !(sourceentity instanceof PhantomChicaEntity)) {
                        if (sourceentity instanceof PhantomMangleEntity) {
                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_mangle")),
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
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_mangle")),
                                        SoundSource.HOSTILE,
                                        1.0F,
                                        1.0F,
                                        false
                                    );
                                }
                            }

                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 200, 2, false, false));
                            }

                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DARKNESS, 200, 0, false, false));
                            }
                        } else if (sourceentity instanceof PhantomPuppetEntity) {
                            if (world instanceof World) {
                                if (!M.isClientSide(world)) {
                                    M.playSound(
                                        world,
                                        null,
                                        BlockPos.containing(x, y, z),
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_puppet")),
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
                                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_puppet")),
                                        SoundSource.HOSTILE,
                                        1.0F,
                                        1.0F,
                                        false
                                    );
                                }
                            }

                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DARKNESS, 200, 0, false, false));
                            }

                            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                                M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.PHATOM_PUPPET_BLINDNESS.get(), 200, 0, false, false));
                            }
                        }
                    } else {
                        if (world instanceof World) {
                            if (!M.isClientSide(world)) {
                                M.playSound(
                                    world,
                                    null,
                                    BlockPos.containing(x, y, z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
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
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
                                    SoundSource.HOSTILE,
                                    1.0F,
                                    1.0F,
                                    false
                                );
                            }
                        }

                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.BLINDNESS, 200, 0, false, false));
                        }
                    }
                } else {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:springtrap_jumpscare")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 200, 2, false, false));
                    }
                }
            }
        }
    }
}
