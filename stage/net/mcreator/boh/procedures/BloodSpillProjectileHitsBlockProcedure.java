package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BloodSpillProjectileHitsBlockProcedure {

    public static void execute(World world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z))) {
                M.setBlock(world, BlockPos.containing(x, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.GOJIBREATH.get())), 3);
            }
            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z + 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y + 1.0, z + 1.0), M.defaultBlockState(((Block) BohModBlocks.GOJIBREATH.get())), 3);
            }
            if (M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z - 1.0))) {
                M.setBlock(world, BlockPos.containing(x, y + 1.0, z - 1.0), M.defaultBlockState(((Block) BohModBlocks.GOJIBREATH.get())), 3);
            }
            if (M.isEmptyBlock(world, BlockPos.containing(x + 1.0, y + 1.0, z))) {
                M.setBlock(world, BlockPos.containing(x + 1.0, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.GOJIBREATH.get())), 3);
            }
            if (M.isEmptyBlock(world, BlockPos.containing(x - 1.0, y + 1.0, z))) {
                M.setBlock(world, BlockPos.containing(x - 1.0, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.GOJIBREATH.get())), 3);
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            Entity _ent = immediatesourceentity;
            if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "particle minecraft:block boh:gojibreath ~ ~ ~ 0.2 0.2 0.2 1 50");
            }
        }
    }
}
