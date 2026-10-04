package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PartyPopperRightclickedOnBlockProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity) {
                M.swing(_entity, InteractionHand.MAIN_HAND, true);
            }

            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "/execute as @s run particle boh:confetti ^ ^1.1 ^.5 0.0 0.0 0.0 0.1 20 force"
                );
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.blast")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.blast")),
                        SoundSource.PLAYERS,
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
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(itemstack) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }
        }
    }
}
