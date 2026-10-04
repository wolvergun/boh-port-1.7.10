package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.KrampusEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class KrampusOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!entity.getPersistentData().getBoolean("lines_michael")
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 8.0, 8.0, 8.0), e -> true).isEmpty()) {
            entity.getPersistentData().putBoolean("lines_michael", true);
         }

         if (!entity.getPersistentData().getBoolean("throlgular") && entity.getPersistentData().getBoolean("lines_michael")) {
            if (entity instanceof KrampusEntity) {
               ((KrampusEntity)entity).setAnimation("scare");
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.prepare_ram")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.prepare_ram")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("throlgular", true);
         }

         if (world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true).isEmpty()) {
            entity.getPersistentData().putBoolean("lines_michael", false);
            entity.getPersistentData().putBoolean("throlgular", false);
         }

         if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()
            && entity.getPersistentData().getBoolean("lines_michael")) {
            if (Math.random() < 0.1) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Items.COAL));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else if (Math.random() < 0.1) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Items.CHARCOAL));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else if (Math.random() < 0.1 && world instanceof ServerLevel _level) {
               ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Items.BONE));
               entityToSpawn.setPickUpDelay(10);
               _level.addFreshEntity(entityToSpawn);
            }

            entity.getPersistentData().putDouble("run_krampus", entity.getPersistentData().getDouble("run_krampus") + 1.0);
         }

         if (entity.getPersistentData().getDouble("run_krampus") > 600.0) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (entity instanceof KrampusEntity) {
               ((KrampusEntity)entity).setAnimation("leave");
            }

            BohMod.queueServerWork(92, () -> entity.getPersistentData().putBoolean("drop_item", true));
         }

         if (entity.getPersistentData().getBoolean("drop_item")) {
            entity.getPersistentData().putBoolean("drop_item", false);
            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            if (Math.random() < 0.7) {
               for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 4); index0++) {
                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack(Items.DIAMOND));
                     entityToSpawn.setPickUpDelay(10);
                     _level.addFreshEntity(entityToSpawn);
                  }
               }
            } else if (Math.random() < 0.2) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack((ItemLike)BohModItems.THE_SLASHER.get()));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else if (Math.random() < 0.2) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack((ItemLike)BohModItems.KILLER_KNIFE.get()));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else if (Math.random() < 0.2) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack((ItemLike)BohModItems.CRUCIFIX.get()));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else if (Math.random() < 0.2) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack((ItemLike)BohModItems.SCHIZOSLEDGE.get()));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else if (Math.random() < 0.2) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack((ItemLike)BohModItems.MASSACRE_AXE.get()));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            } else {
               for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 1, 16); index1++) {
                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 10.0, z, new ItemStack((ItemLike)BohModItems.SPINEL.get()));
                     entityToSpawn.setPickUpDelay(10);
                     _level.addFreshEntity(entityToSpawn);
                  }
               }
            }

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
                     "/summon firework_rocket ~ ~10 ~ {Life:0,FireworksItem:{id:firework_rocket,tag:{Fireworks:{Explosions:[{Type:2,Flicker:1b,Colors:[I;16383998]}]}},Count:1}}"
                  );
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.ram_impact")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.goat.screaming.ram_impact")),
                     SoundSource.NEUTRAL,
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
