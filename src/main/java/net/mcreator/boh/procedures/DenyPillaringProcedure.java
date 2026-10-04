package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class DenyPillaringProcedure {
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
        if (entity != null
            && sourceentity != null
            && M.getY(sourceentity) > M.getY(entity) + 2.0
            && M.is(M.getType(entity), TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_anti_pillaring")))) {
            if (event != null && M.isCancelable(event)) {
                M.setCanceled(event, true);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.damage")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.damage")),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (Math.random() < 0.7) {
                if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("C O W A R D ."), true);
                }
            } else if (Math.random() < 0.7) {
                if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("S T O P H I D I N G."), true);
                }
            } else if (Math.random() < 0.7) {
                if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("N U H U H U H."), true);
                }
            } else {
                if (sourceentity instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("L E T S M A K E T H I S F A I R."), true);
                }

                M.teleportTo(sourceentity, M.getX(entity), M.getY(sourceentity), M.getZ(entity));
                if (sourceentity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(
                        M.connection(_serverPlayer), M.getX(entity), M.getY(sourceentity), M.getZ(entity), M.getYRot(sourceentity), M.getXRot(sourceentity)
                    );
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_laughing")),
                            SoundSource.PLAYERS,
                            1.0F,
                            0.1F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:ben_laughing")),
                            SoundSource.PLAYERS,
                            1.0F,
                            0.1F,
                            false
                        );
                    }
                }
            }
        }
    }
}
