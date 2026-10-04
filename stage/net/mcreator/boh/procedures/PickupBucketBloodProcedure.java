package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.client.Minecraft;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.forge.event.entity.player.RightClickBlock;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class PickupBucketBloodProcedure {

    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event) {
        if (M.getHand(event) == M.getUsedItemHand(M.getEntity(event))) {
            execute(event, M.getLevel(event), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getBlockState(M.getLevel(event), M.getPos(event)), M.getEntity(event));
        }
    }

    public static void execute(World world, double x, double y, double z, BlockState blockstate, Entity entity) {
        execute(null, world, x, y, z, blockstate, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, BlockState blockstate, Entity entity) {
        if (entity != null) {
            if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.BUCKET && M.getBlock(blockstate) == BohModBlocks.XENOMORPH_BLOOD.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
                if (entity instanceof EntityPlayer _player) {
                    ItemStack _setstack = M.copy(M.new_ItemStack(BohModBlocks.XENOMORPH_BLOOD.get()));
                    M.setCount(_setstack, 1);
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                }
                if (!(new Object() {

                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.CREATIVE : false;
                        }
                    }
                }).checkGamemode(entity) && entity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove = M.new_ItemStack(Items.BUCKET);
                    M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
                }
                M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(Blocks.AIR), 3);
            }
            if (M.getItem((entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.BUCKET && M.getBlock(blockstate) == BohModBlocks.GOJIBREATH.get()) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
                if (entity instanceof EntityPlayer _player) {
                    ItemStack _setstack = M.copy(M.new_ItemStack(BohModBlocks.GOJIBREATH.get()));
                    M.setCount(_setstack, 1);
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                }
                if (!(new Object() {

                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.CREATIVE : false;
                        }
                    }
                }).checkGamemode(entity) && entity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove = M.new_ItemStack(Items.BUCKET);
                    M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
                }
                M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(Blocks.AIR), 3);
            }
        }
    }
}
