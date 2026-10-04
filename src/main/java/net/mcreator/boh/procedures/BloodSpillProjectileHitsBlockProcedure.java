package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BloodSpillProjectileHitsBlockProcedure {
    public static void execute(World world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z))) {
                M.setBlock(world, BlockPos.containing(x, y + 1.0, z), M.defaultBlockState(BohModBlocks.GOJIBREATH.get()), 3);
            }

            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z + 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y + 1.0, z + 1.0), M.defaultBlockState(BohModBlocks.GOJIBREATH.get()), 3);
            }

            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z - 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y + 1.0, z - 1.0), M.defaultBlockState(BohModBlocks.GOJIBREATH.get()), 3);
            }

            if (M.isEmptyBlock(world, BlockPos.containing(x + 1.0, y + 1.0, z))) {
                M.setBlock(world, BlockPos.containing(x + 1.0, y + 1.0, z), M.defaultBlockState(BohModBlocks.GOJIBREATH.get()), 3);
            }

            if (M.isEmptyBlock(world, BlockPos.containing(x - 1.0, y + 1.0, z))) {
                M.setBlock(world, BlockPos.containing(x - 1.0, y + 1.0, z), M.defaultBlockState(BohModBlocks.GOJIBREATH.get()), 3);
            }

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
                    "particle minecraft:block boh:gojibreath ~ ~ ~ 0.2 0.2 0.2 1 50"
                );
            }
        }
    }
}
