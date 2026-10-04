package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class BreakWithShearsProcedure {
   @SubscribeEvent
   public static void onLeftClickBlock(LeftClickBlock event) {
      execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.SHEARS) {
            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.BLACK_WALNUT_LEAVES.get()) {
               BlockPos _pos = BlockPos.containing(x, y, z);
               Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
               world.destroyBlock(_pos, false);
               if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
                  ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
                  if (_ist.hurt(1, RandomSource.create(), null)) {
                     _ist.shrink(1);
                     _ist.setDamageValue(0);
                  }

                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModBlocks.BLACK_WALNUT_LEAVES.get()));
                     entityToSpawn.setPickUpDelay(10);
                     _level.addFreshEntity(entityToSpawn);
                  }
               }
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.DOGWOOD_LEAVES.get()) {
               BlockPos _pos = BlockPos.containing(x, y, z);
               Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
               world.destroyBlock(_pos, false);
               if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
                  ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
                  if (_ist.hurt(1, RandomSource.create(), null)) {
                     _ist.shrink(1);
                     _ist.setDamageValue(0);
                  }

                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModBlocks.DOGWOOD_LEAVES.get()));
                     entityToSpawn.setPickUpDelay(10);
                     _level.addFreshEntity(entityToSpawn);
                  }
               }
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.SASSAFRAS_LEAVES.get()) {
               BlockPos _pos = BlockPos.containing(x, y, z);
               Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
               world.destroyBlock(_pos, false);
               if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
                  ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
                  if (_ist.hurt(1, RandomSource.create(), null)) {
                     _ist.shrink(1);
                     _ist.setDamageValue(0);
                  }

                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModBlocks.SASSAFRAS_LEAVES.get()));
                     entityToSpawn.setPickUpDelay(10);
                     _level.addFreshEntity(entityToSpawn);
                  }
               }
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == BohModBlocks.SINISTREE_LEAVES.get()) {
               BlockPos _pos = BlockPos.containing(x, y, z);
               Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
               world.destroyBlock(_pos, false);
               if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
                  ItemStack _ist = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
                  if (_ist.hurt(1, RandomSource.create(), null)) {
                     _ist.shrink(1);
                     _ist.setDamageValue(0);
                  }

                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BohModBlocks.SINISTREE_LEAVES.get()));
                     entityToSpawn.setPickUpDelay(10);
                     _level.addFreshEntity(entityToSpawn);
                  }
               }
            }
         }
      }
   }
}
