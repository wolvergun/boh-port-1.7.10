package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.player.RightClickBlock;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class XenomorphBloodBlockAddedProcedure {
    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event) {
        if (M.getHand(event) == M.getUsedItemHand(M.getEntity(event))) {
            execute(event, M.getLevel(event), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getEntity(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && M.isEmptyBlock(world, BlockPos.containing(x, y + 1.0, z))
            && (
                M.getItem(entity instanceof EntityLivingBase _livEntxxx ? M.getMainHandItem(_livEntxxx) : M.EMPTY)
                        == M.asItem(BohModBlocks.XENOMORPH_BLOOD.get())
                    || M.getItem(entity instanceof EntityLivingBase _livEntxx ? M.getOffhandItem(_livEntxx) : M.EMPTY)
                        == M.asItem(BohModBlocks.XENOMORPH_BLOOD.get())
                    || M.getItem(entity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY)
                        == M.asItem(BohModBlocks.GOJIBREATH.get())
                    || M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getOffhandItem(_livEnt) : M.EMPTY) == M.asItem(BohModBlocks.GOJIBREATH.get())
            )) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava")),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava")),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (!(new Object() {
                        public boolean checkGamemode(Entity _ent) {
                            if (_ent instanceof EntityPlayerMP _serverPlayer) {
                                return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                            } else {
                                return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _playerx
                                    ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_playerx))) != null
                                        && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_playerx))))
                                            == GameType.CREATIVE
                                    : false;
                            }
                        }
                    })
                    .checkGamemode(entity)
                && entity instanceof EntityPlayer _player) {
                ItemStack _setstack = M.copy(M.new_ItemStack(Items.BUCKET));
                M.setCount(_setstack, 1);
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
        }
    }
}
