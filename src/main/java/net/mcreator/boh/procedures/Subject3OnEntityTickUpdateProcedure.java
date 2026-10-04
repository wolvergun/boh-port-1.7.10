package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.Subject3Entity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Subject3OnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof Subject3Entity _datEntSetI) {
                M.set(
                    M.getEntityData(_datEntSetI),
                    Subject3Entity.DATA_subject_3_cooldown,
                    (entity instanceof Subject3Entity _datEntI ? M.getEntityData(_datEntI).get(Subject3Entity.DATA_subject_3_cooldown) : 0) + 1
                );
            }

            if ((entity instanceof Subject3Entity _datEntI ? M.getEntityData(_datEntI).get(Subject3Entity.DATA_subject_3_cooldown) : 0) == 1
                && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:subject3_chase")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:subject3_chase")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if ((entity instanceof Subject3Entity _datEntIx ? M.getEntityData(_datEntIx).get(Subject3Entity.DATA_subject_3_cooldown) : 0) == 782
                && entity instanceof Subject3Entity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), Subject3Entity.DATA_subject_3_cooldown, 0);
            }
        }
    }
}
