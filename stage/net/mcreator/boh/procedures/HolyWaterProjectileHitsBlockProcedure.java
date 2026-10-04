package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class HolyWaterProjectileHitsBlockProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (world instanceof World _level) {
            if (!M.isClientSide(_level)) {
                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:holy_water_splash")), SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:holy_water_splash")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
            }
        }
        if (world instanceof WorldServer _level) {
            M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x + 0.5, y + 1.0, z + 0.5), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/particle minecraft:item boh:holy_water_item ~ ~ ~ .2 .2 .2 0.1 20");
        }
        if (M.canSurvive(M.defaultBlockState(Blocks.FIRE), world, BlockPos.containing(x, y, z))) {
            M.setBlock(world, BlockPos.containing(x, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.HOLY_FIRE.get())), 3);
        }
        if (M.canSurvive(M.defaultBlockState(Blocks.FIRE), world, BlockPos.containing(x + 1.0, y, z))) {
            M.setBlock(world, BlockPos.containing(x + 1.0, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.HOLY_FIRE.get())), 3);
        }
        if (M.canSurvive(M.defaultBlockState(Blocks.FIRE), world, BlockPos.containing(x - 1.0, y, z))) {
            M.setBlock(world, BlockPos.containing(x - 1.0, y + 1.0, z), M.defaultBlockState(((Block) BohModBlocks.HOLY_FIRE.get())), 3);
        }
        if (M.canSurvive(M.defaultBlockState(Blocks.FIRE), world, BlockPos.containing(x, y, z + 1.0))) {
            M.setBlock(world, BlockPos.containing(x, y + 1.0, z + 1.0), M.defaultBlockState(((Block) BohModBlocks.HOLY_FIRE.get())), 3);
        }
        if (M.canSurvive(M.defaultBlockState(Blocks.FIRE), world, BlockPos.containing(x, y, z - 1.0))) {
            M.setBlock(world, BlockPos.containing(x, y + 1.0, z - 1.0), M.defaultBlockState(((Block) BohModBlocks.HOLY_FIRE.get())), 3);
        }
    }
}
