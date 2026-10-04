package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class KillMobsBOHProcedure {
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

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (sourceentity instanceof Player) {
            if (Math.random() < 0.7) {
               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_exotic")))) {
                  for (int index0 = 0; index0 < (int)Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index0++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.EXOTIC_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }

               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_killers")))) {
                  for (int index1 = 0; index1 < (int)Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index1++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.KILLERS_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }

               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_monstrous")))) {
                  for (int index2 = 0; index2 < (int)Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index2++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.MONSTROUS_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }

               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_demons")))) {
                  for (int index3 = 0; index3 < (int)Mth.nextDouble(RandomSource.create(), 2.0, 6.0); index3++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.DEMONIC_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }
            } else if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MOB_LOOTING, sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
                  != 0
               && Math.random() < 0.85) {
               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_exotic")))) {
                  for (int index4 = 0; index4 < (int)Mth.nextDouble(RandomSource.create(), 3.0, 6.0); index4++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.EXOTIC_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }

               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_killers")))) {
                  for (int index5 = 0; index5 < (int)Mth.nextDouble(RandomSource.create(), 3.0, 7.0); index5++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.KILLERS_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }

               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_montrous")))) {
                  for (int index6 = 0; index6 < (int)Mth.nextDouble(RandomSource.create(), 3.0, 7.0); index6++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.MONSTROUS_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }

               if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:boh_demons")))) {
                  for (int index7 = 0; index7 < (int)Mth.nextDouble(RandomSource.create(), 3.0, 7.0); index7++) {
                     if (world instanceof ServerLevel _level) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModItems.DEMONIC_SOUL.get()));
                        entityToSpawn.setPickUpDelay(10);
                        _level.addFreshEntity(entityToSpawn);
                     }
                  }
               }
            }
         }
      }
   }
}
