package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.FresnoNightcrawlerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class FresnoNightwalkerOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double confuse_timer = 0.0;
         if (world instanceof Level _lvl0 && _lvl0.isDay() && entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, false, false));
         }

         if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true).isEmpty()
            && entity instanceof FresnoNightcrawlerEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  FresnoNightcrawlerEntity.DATA_fresno_confused,
                  (
                        entity instanceof FresnoNightcrawlerEntity _datEntI
                           ? (Integer)_datEntI.getEntityData().get(FresnoNightcrawlerEntity.DATA_fresno_confused)
                           : 0
                     )
                     + 1
               );
         }

         if ((entity instanceof FresnoNightcrawlerEntity _datEntI ? (Integer)_datEntI.getEntityData().get(FresnoNightcrawlerEntity.DATA_fresno_confused) : 0)
            == 100) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:fresno_happy")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     0.8F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:fresno_happy")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     0.8F,
                     false
                  );
               }
            }

            if (entity instanceof FresnoNightcrawlerEntity) {
               ((FresnoNightcrawlerEntity)entity).setAnimation("confused");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
            }
         }

         if (world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true).isEmpty()
            && entity instanceof FresnoNightcrawlerEntity _datEntSetI) {
            _datEntSetI.getEntityData().set(FresnoNightcrawlerEntity.DATA_fresno_confused, 0);
         }
      }
   }
}
