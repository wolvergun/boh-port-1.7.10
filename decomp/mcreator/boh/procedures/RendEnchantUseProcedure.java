package net.mcreator.boh.procedures;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.RendProjectileEntity;
import net.mcreator.boh.init.BohModEnchantments;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(Dist.CLIENT)
public class RendEnchantUseProcedure {
   @SubscribeEvent
   public static void onLeftClick(LeftClickEmpty event) {
      BohMod.PACKET_HANDLER.sendToServer(new RendEnchantUseProcedure.RendEnchantUseMessage());
      execute(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (EnchantmentHelper.getItemEnchantmentLevel(
                  (Enchantment)BohModEnchantments.REND.get(), entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY
               )
               != 0
            && !(
               entity instanceof Player _plrCldCheck3
                  && _plrCldCheck3.getCooldowns().isOnCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem())
            )) {
            if (entity instanceof Player _player) {
               _player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 40);
            }

            Entity _shootFrom = entity;
            Level projectileLevel = _shootFrom.level();
            if (!projectileLevel.isClientSide()) {
               Projectile _entityToSpawn = (new Object() {
                     public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                        AbstractArrow entityToSpawn = new RendProjectileEntity(
                           (EntityType<? extends RendProjectileEntity>)BohModEntities.REND_PROJECTILE.get(), level
                        );
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);
                        entityToSpawn.setPierceLevel(piercing);
                        return entityToSpawn;
                     }
                  })
                  .getArrow(projectileLevel, entity, 0.0F, 0, (byte)10);
               _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
               _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 5.0F, 0.1F);
               projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rend_slash")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rend_slash")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
                  );
               }
            }

            entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC)), 2.5F);
            ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
            if (_ist.hurt(3, RandomSource.create(), null)) {
               _ist.shrink(1);
               _ist.setDamageValue(0);
            }
         }
      }
   }

   @EventBusSubscriber(bus = Bus.MOD)
   public static class RendEnchantUseMessage {
      public RendEnchantUseMessage() {
      }

      public RendEnchantUseMessage(FriendlyByteBuf buffer) {
      }

      public static void buffer(RendEnchantUseProcedure.RendEnchantUseMessage message, FriendlyByteBuf buffer) {
      }

      public static void handler(RendEnchantUseProcedure.RendEnchantUseMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(
            () -> {
               if (context.getSender().level().hasChunkAt(context.getSender().blockPosition())) {
                  RendEnchantUseProcedure.execute(
                     context.getSender().level(),
                     context.getSender().getX(),
                     context.getSender().getY(),
                     context.getSender().getZ(),
                     context.getSender()
                  );
               }
            }
         );
         context.setPacketHandled(true);
      }

      @SubscribeEvent
      public static void registerMessage(FMLCommonSetupEvent event) {
         BohMod.addNetworkMessage(
            RendEnchantUseProcedure.RendEnchantUseMessage.class,
            RendEnchantUseProcedure.RendEnchantUseMessage::buffer,
            RendEnchantUseProcedure.RendEnchantUseMessage::new,
            RendEnchantUseProcedure.RendEnchantUseMessage::handler
         );
      }
   }
}
