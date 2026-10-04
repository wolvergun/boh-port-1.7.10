package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BreakWithShearsProcedure {
    @SubscribeEvent
    public void onLeftClickBlock(LeftClickBlock event) {
        execute(event, M.getLevel(event), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null && M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == Items.SHEARS) {
            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.BLACK_WALNUT_LEAVES.get()) {
                BlockPos _pos = BlockPos.containing(x, y, z);
                M.dropResources(M.getBlockState(world, _pos), world, BlockPos.containing(x, y, z), null);
                M.destroyBlock(world, _pos, false);
                if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr)))) {
                    ItemStack _ist = entity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY;
                    if (M.hurt(_ist, 1, RandomSource.create(), null)) {
                        M.shrink(_ist, 1);
                        M.setDamageValue(_ist, 0);
                    }

                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.BLACK_WALNUT_LEAVES.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                }
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.DOGWOOD_LEAVES.get()) {
                BlockPos _pos = BlockPos.containing(x, y, z);
                M.dropResources(M.getBlockState(world, _pos), world, BlockPos.containing(x, y, z), null);
                M.destroyBlock(world, _pos, false);
                if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr)))) {
                    ItemStack _istx = entity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY;
                    if (M.hurt(_istx, 1, RandomSource.create(), null)) {
                        M.shrink(_istx, 1);
                        M.setDamageValue(_istx, 0);
                    }

                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.DOGWOOD_LEAVES.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                }
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SASSAFRAS_LEAVES.get()) {
                BlockPos _pos = BlockPos.containing(x, y, z);
                M.dropResources(M.getBlockState(world, _pos), world, BlockPos.containing(x, y, z), null);
                M.destroyBlock(world, _pos, false);
                if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr)))) {
                    ItemStack _istxx = entity instanceof EntityLivingBase _livEntxxx ? M.getMainHandItem(_livEntxxx) : M.EMPTY;
                    if (M.hurt(_istxx, 1, RandomSource.create(), null)) {
                        M.shrink(_istxx, 1);
                        M.setDamageValue(_istxx, 0);
                    }

                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.SASSAFRAS_LEAVES.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                }
            }

            if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y, z))) == BohModBlocks.SINISTREE_LEAVES.get()) {
                BlockPos _pos = BlockPos.containing(x, y, z);
                M.dropResources(M.getBlockState(world, _pos), world, BlockPos.containing(x, y, z), null);
                M.destroyBlock(world, _pos, false);
                if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr)))) {
                    ItemStack _istxxx = entity instanceof EntityLivingBase _livEntxxxx ? M.getMainHandItem(_livEntxxxx) : M.EMPTY;
                    if (M.hurt(_istxxx, 1, RandomSource.create(), null)) {
                        M.shrink(_istxxx, 1);
                        M.setDamageValue(_istxxx, 0);
                    }

                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.SINISTREE_LEAVES.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                }
            }
        }
    }
}
