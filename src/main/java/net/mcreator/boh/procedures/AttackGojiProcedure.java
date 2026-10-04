package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.BruceEntity;
import net.mcreator.boh.entity.GojiEntity;
import net.mcreator.boh.init.BohModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AttackGojiProcedure {
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
            if (sourceentity instanceof GojiEntity) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_attack")),
                            SoundSource.HOSTILE,
                            2.0F,
                            0.9F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_attack")),
                            SoundSource.HOSTILE,
                            2.0F,
                            0.9F,
                            false
                        );
                    }
                }

                if (world instanceof WorldServer _level) {
                    M.sendParticles(_level, BohModParticleTypes.BITE_PARTICLE.get(), x, y + M.getBbHeight(entity) / 2.0F, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }

            if (sourceentity instanceof BruceEntity) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_attack")),
                            SoundSource.HOSTILE,
                            2.0F,
                            0.9F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rexy_attack")),
                            SoundSource.HOSTILE,
                            2.0F,
                            0.9F,
                            false
                        );
                    }
                }

                if (world instanceof WorldServer _level) {
                    M.sendParticles(_level, BohModParticleTypes.BITE_PARTICLE.get(), x, y + M.getBbHeight(entity) / 2.0F, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
