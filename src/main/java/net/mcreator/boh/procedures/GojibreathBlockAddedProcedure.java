package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GojibreathBlockAddedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
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
