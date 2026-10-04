package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.entity.ScissormanEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class AttackScissormanProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().level(),
            event.getEntity().getX(),
            event.getEntity().getY(),
            event.getEntity().getZ(),
            event.getSource().getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      execute(null, world, x, y, z, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      if (sourceentity != null) {
         if (sourceentity instanceof ScissormanEntity) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sheep.shear")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sheep.shear")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            Vec3 _center = new Vec3(
               sourceentity.level()
                  .clip(
                     new ClipContext(
                        sourceentity.getEyePosition(1.0F),
                        sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(2.0)),
                        Block.OUTLINE,
                        Fluid.NONE,
                        sourceentity
                     )
                  )
                  .getBlockPos()
                  .getX(),
               sourceentity.level()
                  .clip(
                     new ClipContext(
                        sourceentity.getEyePosition(1.0F),
                        sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(2.0)),
                        Block.OUTLINE,
                        Fluid.NONE,
                        sourceentity
                     )
                  )
                  .getBlockPos()
                  .getY(),
               sourceentity.level()
                  .clip(
                     new ClipContext(
                        sourceentity.getEyePosition(1.0F),
                        sourceentity.getEyePosition(1.0F).add(sourceentity.getViewVector(1.0F).scale(2.0)),
                        Block.OUTLINE,
                        Fluid.NONE,
                        sourceentity
                     )
                  )
                  .getBlockPos()
                  .getZ()
            );

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (!(entityiterator instanceof ScissormanEntity)) {
                  entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 15.0F);
                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt")),
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt")),
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }
            }
         }
      }
   }
}
