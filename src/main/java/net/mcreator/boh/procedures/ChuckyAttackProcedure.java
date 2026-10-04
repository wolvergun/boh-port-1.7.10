package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.ChuckyEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ChuckyAttackProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
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
        if (entity != null && sourceentity != null && sourceentity instanceof ChuckyEntity && entity instanceof EntityPlayer) {
            if (Math.random() < 0.25) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chucky_attack")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chucky_attack")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }
            }

            M.setDeltaMovement(
                sourceentity,
                new Vec3(
                    M.getDeltaMovement(sourceentity).x() + M.getLookAngle(sourceentity).x,
                    M.getDeltaMovement(sourceentity).y() + M.getLookAngle(sourceentity).y + 0.2,
                    M.getDeltaMovement(sourceentity).z() + M.getLookAngle(sourceentity).z
                )
            );
            if (Math.random() < 0.1) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chucky_grab")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chucky_grab")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(
                        BohModEntities.CHUCKY_GRAB.get(),
                        _level,
                        BlockPos.containing(M.getX(entity), M.getY(entity), M.getZ(entity)),
                        MobSpawnType.MOB_SUMMONED
                    );
                    if (entityToSpawn != null) {
                        M.setYRot(entityToSpawn, M.getXRot(entity));
                        M.setYBodyRot(entityToSpawn, M.getXRot(entity));
                        M.setYHeadRot(entityToSpawn, M.getXRot(entity));
                        M.setXRot(entityToSpawn, M.getYRot(entity));
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.EFFECT_CHUCKY_GRAB.get(), 1200, 0, false, false));
                }

                if (!M.isClientSide(M.level(sourceentity))) {
                    M.discard(sourceentity);
                }
            }
        }
    }
}
