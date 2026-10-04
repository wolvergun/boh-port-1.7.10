package net.mcreator.boh.procedures;

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
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.entity.SquidwardEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SquidwardRightClickedOnEntityProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && entity instanceof SquidwardEntity _datEntL0
            && M.getEntityData(_datEntL0).get(SquidwardEntity.DATA_variant)
            && !(entity instanceof SquidwardEntity _datEntL1 && M.getEntityData(_datEntL1).get(SquidwardEntity.DATA_start))) {
            if (entity instanceof SquidwardEntity _datEntSetL) {
                M.set(M.getEntityData(_datEntSetL), SquidwardEntity.DATA_start, true);
            }

            if (entity instanceof SquidwardEntity) {
                ((SquidwardEntity)entity).setAnimation("shoot");
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 900, 255, false, false));
            }

            BohMod.queueServerWork(
                48,
                () -> {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_cocking")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                }
            );
            BohMod.queueServerWork(
                60,
                () -> {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:shotgun_shoot")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
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
                            "/particle boh:blood_fall ~ ~1.5 ~ 0.1 0.1 0.1 .15 50"
                        );
                    }
                }
            );
            BohMod.queueServerWork(
                75,
                () -> M.hurt(
                    entity,
                    M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                    900.0F
                )
            );
            BohMod.queueServerWork(
                80,
                () -> {
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(
                            BohModEntities.SQUIDWARD_DOOMED.get(),
                            _level,
                            BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)),
                            MobSpawnType.MOB_SUMMONED
                        );
                        if (entityToSpawn != null) {
                            M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                        }
                    }
                }
            );
        }
    }
}
