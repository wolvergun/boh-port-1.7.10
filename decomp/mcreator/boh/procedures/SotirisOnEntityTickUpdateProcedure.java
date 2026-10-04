package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.SotirisEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class SotirisOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))
            && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player
            && Math.random() < 0.01) {
            Entity _ent = entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;
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
                     "/spreadplayers ~ ~ 10 5 false @e[type=boh:sotiris,limit=1,sort=nearest]"
                  );
            }

            entity.getPersistentData().putDouble("idle_switch", Mth.nextInt(RandomSource.create(), 0, 5));
            if (entity.getPersistentData().getDouble("idle_switch") == 0.0) {
               if (entity instanceof SotirisEntity) {
                  ((SotirisEntity)entity).setAnimation("idle");
               }
            } else if (entity.getPersistentData().getDouble("idle_switch") == 1.0) {
               if (entity instanceof SotirisEntity) {
                  ((SotirisEntity)entity).setAnimation("facepalm");
               }
            } else if (entity.getPersistentData().getDouble("idle_switch") == 2.0) {
               if (entity instanceof SotirisEntity) {
                  ((SotirisEntity)entity).setAnimation("grab");
               }
            } else if (entity.getPersistentData().getDouble("idle_switch") == 3.0) {
               if (entity instanceof SotirisEntity) {
                  ((SotirisEntity)entity).setAnimation("twist");
               }
            } else if (entity.getPersistentData().getDouble("idle_switch") == 4.0) {
               if (entity instanceof SotirisEntity) {
                  ((SotirisEntity)entity).setAnimation("point");
               }
            } else if (entity.getPersistentData().getDouble("idle_switch") == 5.0 && entity instanceof SotirisEntity) {
               ((SotirisEntity)entity).setAnimation("balls");
            }
         }

         if (!entity.getPersistentData().getBoolean("put_eye") && entity.getPersistentData().getBoolean("first_kill")) {
            if (entity instanceof SotirisEntity) {
               ((SotirisEntity)entity).setAnimation("sotiri");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 254, false, false));
            }

            entity.getPersistentData().putDouble("put_eye_timer", entity.getPersistentData().getDouble("put_eye_timer") + 1.0);
            if (entity.getPersistentData().getDouble("put_eye_timer") == 190.0) {
               entity.getPersistentData().putBoolean("put_eye", true);
            }
         }

         if (!entity.getPersistentData().getBoolean("first_kill") && entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 254, false, false));
         }
      }
   }
}
