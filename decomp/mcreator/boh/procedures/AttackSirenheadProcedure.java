package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.LightHeadEntity;
import net.mcreator.boh.entity.SirenHeadEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class AttackSirenheadProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().level(),
            event.getEntity().getX(),
            event.getEntity().getY(),
            event.getEntity().getZ(),
            event.getEntity(),
            event.getSource().getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      execute(null, world, x, y, z, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (sourceentity instanceof LightHeadEntity || sourceentity instanceof SirenHeadEntity) {
            BohMod.queueServerWork(
               6,
               () -> {
                  for (int index0 = 0; index0 < 5; index0++) {
                     world.levelEvent(
                        2001,
                        BlockPos.containing(
                           Mth.nextDouble(RandomSource.create(), -1.0, 1.0)
                              + sourceentity.level()
                                 .clip(
                                    new ClipContext(
                                       sourceentity.getEyePosition(1.0F),
                                       sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(5.0)),
                                       Block.OUTLINE,
                                       Fluid.NONE,
                                       sourceentity
                                    )
                                 )
                                 .getBlockPos()
                                 .getX(),
                           y,
                           Mth.nextDouble(RandomSource.create(), -1.0, 1.0)
                              + sourceentity.level()
                                 .clip(
                                    new ClipContext(
                                       sourceentity.getEyePosition(1.0F),
                                       sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(5.0)),
                                       Block.OUTLINE,
                                       Fluid.NONE,
                                       sourceentity
                                    )
                                 )
                                 .getBlockPos()
                                 .getZ()
                        ),
                        net.minecraft.world.level.block.Block.getId(
                           world.getBlockState(
                              BlockPos.containing(
                                 sourceentity.level()
                                    .clip(
                                       new ClipContext(
                                          sourceentity.getEyePosition(1.0F),
                                          sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(5.0)),
                                          Block.OUTLINE,
                                          Fluid.NONE,
                                          sourceentity
                                       )
                                    )
                                    .getBlockPos()
                                    .getX(),
                                 y - 1.0,
                                 sourceentity.level()
                                    .clip(
                                       new ClipContext(
                                          sourceentity.getEyePosition(1.0F),
                                          sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(5.0)),
                                          Block.OUTLINE,
                                          Fluid.NONE,
                                          sourceentity
                                       )
                                    )
                                    .getBlockPos()
                                    .getZ()
                              )
                           )
                        )
                     );
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                           SoundSource.HOSTILE,
                           3.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                           SoundSource.HOSTILE,
                           3.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 2, 30, false, false));
                  }
               }
            );
         }
      }
   }
}
