package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BloodSpillProjectileHitsBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         if (world.isEmptyBlock(BlockPos.containing(x, y + 1.0, z))) {
            world.setBlock(BlockPos.containing(x, y + 1.0, z), ((Block)BohModBlocks.GOJIBREATH.get()).defaultBlockState(), 3);
         }

         if (world.isEmptyBlock(BlockPos.containing(x, y + 1.0, z + 1.0))) {
            world.setBlock(BlockPos.containing(x, y + 1.0, z + 1.0), ((Block)BohModBlocks.GOJIBREATH.get()).defaultBlockState(), 3);
         }

         if (world.isEmptyBlock(BlockPos.containing(x, y + 1.0, z - 1.0))) {
            world.setBlock(BlockPos.containing(x, y + 1.0, z - 1.0), ((Block)BohModBlocks.GOJIBREATH.get()).defaultBlockState(), 3);
         }

         if (world.isEmptyBlock(BlockPos.containing(x + 1.0, y + 1.0, z))) {
            world.setBlock(BlockPos.containing(x + 1.0, y + 1.0, z), ((Block)BohModBlocks.GOJIBREATH.get()).defaultBlockState(), 3);
         }

         if (world.isEmptyBlock(BlockPos.containing(x - 1.0, y + 1.0, z))) {
            world.setBlock(BlockPos.containing(x - 1.0, y + 1.0, z), ((Block)BohModBlocks.GOJIBREATH.get()).defaultBlockState(), 3);
         }

         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         Entity _ent = immediatesourceentity;
         if (!_ent.level().isClientSide() && _ent.getServer() != null) {
            _ent.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                     CommandSource.NULL,
                     _ent.position(),
                     _ent.getRotationVector(),
                     _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                     4,
                     _ent.getName().getString(),
                     _ent.getDisplayName(),
                     _ent.level().getServer(),
                     _ent
                  ),
                  "particle minecraft:block boh:gojibreath ~ ~ ~ 0.2 0.2 0.2 1 50"
               );
         }
      }
   }
}
