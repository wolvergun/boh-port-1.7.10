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

public class TarBallProjectileHitsBlockProcedure {
    public static void execute(World world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (!M.isClientSide(M.level(immediatesourceentity)) && M.getServer(immediatesourceentity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(immediatesourceentity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(immediatesourceentity),
                        M.getRotationVector(immediatesourceentity),
                        M.level(immediatesourceentity) instanceof WorldServer ? (WorldServer)M.level(immediatesourceentity) : null,
                        4,
                        M.getString(M.getName(immediatesourceentity)),
                        M.getDisplayName(immediatesourceentity),
                        M.getServer(M.level(immediatesourceentity)),
                        immediatesourceentity
                    ),
                    "/particle squid_ink ~ ~ ~ 0.2 0.2 0.2 .1 50"
                );
            }

            if (!M.isClientSide(M.level(immediatesourceentity)) && M.getServer(immediatesourceentity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(immediatesourceentity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(immediatesourceentity),
                        M.getRotationVector(immediatesourceentity),
                        M.level(immediatesourceentity) instanceof WorldServer ? (WorldServer)M.level(immediatesourceentity) : null,
                        4,
                        M.getString(M.getName(immediatesourceentity)),
                        M.getDisplayName(immediatesourceentity),
                        M.getServer(M.level(immediatesourceentity)),
                        immediatesourceentity
                    ),
                    "/summon area_effect_cloud ~ ~ ~ {Particle:squid_ink,Potion:2,Radius:3,Duration:200,Effects:[{Id:2,Duration:20,Amplifier:1,Ambient:1b,ShowParticles:1b,ShowIcon:1}]}"
                );
            }
        }
    }
}
