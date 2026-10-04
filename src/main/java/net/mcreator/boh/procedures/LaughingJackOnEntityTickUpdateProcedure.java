package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.LaughingJackEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LaughingJackOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof LaughingJackEntity _datEntSetI) {
                M.set(
                    M.getEntityData(_datEntSetI),
                    LaughingJackEntity.DATA_laughingjack_cooldown,
                    (entity instanceof LaughingJackEntity _datEntI ? M.getEntityData(_datEntI).get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) + 1
                );
            }

            if ((entity instanceof LaughingJackEntity _datEntI ? M.getEntityData(_datEntI).get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) == 1
                && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:laghingjack_ambience")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:laghingjack_ambience")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if ((entity instanceof LaughingJackEntity _datEntIx ? M.getEntityData(_datEntIx).get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) == 709
                && entity instanceof LaughingJackEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), LaughingJackEntity.DATA_laughingjack_cooldown, 0);
            }
        }
    }
}
