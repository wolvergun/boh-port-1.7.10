package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.Specimen9BossEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class Specimen9BossEntityIsHurtProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().level(),
            event.getEntity().getX(),
            event.getEntity().getY(),
            event.getEntity().getZ(),
            event.getSource(),
            event.getEntity(),
            event.getSource().getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
      execute(null, world, x, y, z, damagesource, entity, sourceentity);
   }

   private static void execute(
      @Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity
   ) {
      if (damagesource != null && entity != null && sourceentity != null) {
         if (!world.isClientSide() && entity instanceof Specimen9BossEntity) {
            if (sourceentity instanceof Player && entity instanceof Specimen9BossEntity) {
               ((Specimen9BossEntity)entity).setAnimation("hurt");
            }

            if (!entity.isShiftKeyDown() && damagesource.is(DamageTypes.FIREBALL) && !entity.isInvulnerable()) {
               entity.setShiftKeyDown(true);
               Entity _ent = entity;
               _ent.teleportTo(x, y - 6.0, z);
               if (_ent instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.connection.teleport(x, y - 6.0, z, _ent.getYRot(), _ent.getXRot());
               }

               BohMod.queueServerWork(100, () -> {
                  Entity _entx = entity;
                  _entx.teleportTo(x, y + 0.0, z);
                  if (_entx instanceof ServerPlayer _serverPlayerx) {
                     _serverPlayerx.connection.teleport(x, y + 0.0, z, _entx.getYRot(), _entx.getXRot());
                  }

                  entity.setShiftKeyDown(false);
               });
            }
         }
      }
   }
}
