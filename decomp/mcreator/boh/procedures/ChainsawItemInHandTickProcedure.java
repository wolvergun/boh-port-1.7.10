package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.item.ChainsawItem;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class ChainsawItemInHandTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (entity.isSprinting()) {
            if (Math.random() < 0.5) {
               ItemStack _ist = itemstack;
               if (_ist.hurt(1, RandomSource.create(), null)) {
                  _ist.shrink(1);
                  _ist.setDamageValue(0);
               }
            }

            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
               if (Math.random() < 0.2) {
                  Entity _ent = entity;
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
                           "/execute as @s run particle minecraft:smoke ^-.2 ^1.1 ^.5 0.0 0.0 0.0 0.01 1 force"
                        );
                  }
               }
            } else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()
               && Math.random() < 0.2) {
               Entity _ent = entity;
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
                        "/execute as @s run particle minecraft:smoke ^-.2 ^1.1 ^.5 0.0 0.0 0.0 0.01 1 force"
                     );
               }
            }

            itemstack.getOrCreateTag().putDouble("timer_sound", itemstack.getOrCreateTag().getDouble("timer_sound") + 1.0);
            if (itemstack.getOrCreateTag().getDouble("timer_sound") == 14.0) {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               itemstack.getOrCreateTag().putDouble("timer_sound", 0.0);
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 1, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 2, false, false));
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1.5), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() != itemstack.getItem()) {
                  entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), entity), 2.5F);
                  if (Math.random() < 0.2) {
                     Entity _ent = entity;
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
                              "effect give @s kurolib:bleeding 0 6"
                           );
                     }
                  }
               }
            }

            if (itemstack.getItem() instanceof ChainsawItem) {
               itemstack.getOrCreateTag().putString("geckoAnim", "active");
            }
         } else if (itemstack.getItem() instanceof ChainsawItem) {
            itemstack.getOrCreateTag().putString("geckoAnim", "idle");
         }
      }
   }
}
