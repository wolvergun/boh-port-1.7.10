package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.TakenPillarEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TakenPillarOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof TakenPillarEntity) {
                ((TakenPillarEntity)entity).setAnimation("rise");
            }

            BohMod.queueServerWork(
                2,
                () -> {
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
                            "spreadplayers ~ ~ 1 4 under 4 true @e[type=boh:taken_pillar,limit=1,distance=0..3]"
                        );
                    }
                }
            );
            BohMod.queueServerWork(20, () -> M.putBoolean(M.getPersistentData(entity), "damage", true));
            BohMod.queueServerWork(120, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:screampillar")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:screampillar")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                    );
                }
            }
        }
    }
}
