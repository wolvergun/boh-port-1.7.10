package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class HolyWaterProjectileHitsBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof Level _level) {
         if (!_level.isClientSide()) {
            _level.playSound(
               null,
               BlockPos.containing(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:holy_water_splash")),
               SoundSource.PLAYERS,
               1.0F,
               1.0F
            );
         } else {
            _level.playLocalSound(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:holy_water_splash")),
               SoundSource.PLAYERS,
               1.0F,
               1.0F,
               false
            );
         }
      }

      if (world instanceof ServerLevel _level) {
         _level.getServer()
            .getCommands()
            .performPrefixedCommand(
               new CommandSourceStack(
                     CommandSource.NULL, new Vec3(x + 0.5, y + 1.0, z + 0.5), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null
                  )
                  .withSuppressedOutput(),
               "/particle minecraft:item boh:holy_water_item ~ ~ ~ .2 .2 .2 0.1 20"
            );
      }

      if (Blocks.FIRE.defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z))) {
         world.setBlock(BlockPos.containing(x, y + 1.0, z), ((Block)BohModBlocks.HOLY_FIRE.get()).defaultBlockState(), 3);
      }

      if (Blocks.FIRE.defaultBlockState().canSurvive(world, BlockPos.containing(x + 1.0, y, z))) {
         world.setBlock(BlockPos.containing(x + 1.0, y + 1.0, z), ((Block)BohModBlocks.HOLY_FIRE.get()).defaultBlockState(), 3);
      }

      if (Blocks.FIRE.defaultBlockState().canSurvive(world, BlockPos.containing(x - 1.0, y, z))) {
         world.setBlock(BlockPos.containing(x - 1.0, y + 1.0, z), ((Block)BohModBlocks.HOLY_FIRE.get()).defaultBlockState(), 3);
      }

      if (Blocks.FIRE.defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z + 1.0))) {
         world.setBlock(BlockPos.containing(x, y + 1.0, z + 1.0), ((Block)BohModBlocks.HOLY_FIRE.get()).defaultBlockState(), 3);
      }

      if (Blocks.FIRE.defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z - 1.0))) {
         world.setBlock(BlockPos.containing(x, y + 1.0, z - 1.0), ((Block)BohModBlocks.HOLY_FIRE.get()).defaultBlockState(), 3);
      }
   }
}
