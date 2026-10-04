package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class JackalopeOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (Math.random() < 0.3
            && Math.random() < 0.3
            && Math.random() < 0.3
            && M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z)))
                == M.getRandomElement(
                        M.getTag(M.tags(ForgeRegistries.BLOCKS), BlockTags.create(new ResourceLocation("minecraft:maintains_farmland"))), RandomSource.create()
                    )
                    .orElseGet(() -> Blocks.AIR)) {
            M.destroyBlock(world, BlockPos.containing(x, y, z), false);
        }
    }
}
