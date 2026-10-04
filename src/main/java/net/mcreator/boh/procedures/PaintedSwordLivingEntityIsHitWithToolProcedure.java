package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PaintedSwordLivingEntityIsHitWithToolProcedure {
    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:painted_sword_hit")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:painted_sword_hit")),
                        SoundSource.PLAYERS,
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:painted_sword_sweep")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:painted_sword_sweep")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (!M.isClientSide(M.level(sourceentity)) && M.getServer(sourceentity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(sourceentity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(sourceentity),
                        M.getRotationVector(sourceentity),
                        M.level(sourceentity) instanceof WorldServer ? (WorldServer)M.level(sourceentity) : null,
                        4,
                        M.getString(M.getName(sourceentity)),
                        M.getDisplayName(sourceentity),
                        M.getServer(M.level(sourceentity)),
                        sourceentity
                    ),
                    "/execute as @p at @s run particle boh:painted_sword_sweep ^ ^1.35 ^1 0 0 0 0 1"
                );
            }
        }
    }
}
