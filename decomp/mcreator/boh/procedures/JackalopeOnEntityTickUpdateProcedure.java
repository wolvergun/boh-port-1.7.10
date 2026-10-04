package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class JackalopeOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (Math.random() < 0.3
         && Math.random() < 0.3
         && Math.random() < 0.3
         && world.getBlockState(BlockPos.containing(x, y, z)).getBlock()
            == ForgeRegistries.BLOCKS
               .tags()
               .getTag(BlockTags.create(new ResourceLocation("minecraft:maintains_farmland")))
               .getRandomElement(RandomSource.create())
               .orElseGet(() -> Blocks.AIR)) {
         world.destroyBlock(BlockPos.containing(x, y, z), false);
      }
   }
}
