package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class TinkyWinkyOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            if (!(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(MobEffects.SATURATION))
               && Math.random() < 0.02
               && Math.random() < 0.02
               && entity instanceof LivingEntity _entity
               && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 30, 0, false, false));
            }

            if (Math.random() < 0.2 && Math.random() < 0.12 && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tinkywinky_chase")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tinkywinky_chase")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }

         if (!(entity instanceof Mob _mob && _mob.isAggressive()) && Math.random() < 0.01 && Math.random() < 0.005 && world.isEmptyBlock(BlockPos.containing(x, y, z))) {
            world.setBlock(BlockPos.containing(x, y, z), ((Block)BohModBlocks.TUBBY_CUSTARD.get()).defaultBlockState(), 3);
         }
      }
   }
}
