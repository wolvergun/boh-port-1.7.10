package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class SoulStealerKillProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
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

   private static void execute(
      @Nullable Event event, final LevelAccessor world, final double x, final double y, final double z, Entity entity, Entity sourceentity
   ) {
      if (entity != null && sourceentity != null) {
         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == BohModItems.SOUL_STEALER.get()
            && Math.random() < 0.5) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _level) {
               _level.sendParticles(ParticleTypes.SOUL, x, y + entity.getBbHeight() / 2.0F, z, 10, 0.2, 0.2, 0.2, 0.01);
            }

            if (Math.random() < 0.25) {
               (new Object() {
                  void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.EXOTIC_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }

                     int tick2 = ticks;
                     BohMod.queueServerWork(tick2, () -> {
                        if (timedlooptotal > timedloopiterator + 1) {
                           this.timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
                        }
                     });
                  }
               }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            } else if (Math.random() < 0.5) {
               (new Object() {
                  void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.DEMONIC_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }

                     int tick2 = ticks;
                     BohMod.queueServerWork(tick2, () -> {
                        if (timedlooptotal > timedloopiterator + 1) {
                           this.timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
                        }
                     });
                  }
               }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            } else if (Math.random() < 0.75) {
               (new Object() {
                  void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.KILLERS_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }

                     int tick2 = ticks;
                     BohMod.queueServerWork(tick2, () -> {
                        if (timedlooptotal > timedloopiterator + 1) {
                           this.timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
                        }
                     });
                  }
               }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            } else {
               (new Object() {
                  void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.MONSTROUS_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }

                     int tick2 = ticks;
                     BohMod.queueServerWork(tick2, () -> {
                        if (timedlooptotal > timedloopiterator + 1) {
                           this.timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
                        }
                     });
                  }
               }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            }
         }
      }
   }
}
