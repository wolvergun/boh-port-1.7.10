package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class TeleportToBoilerProcedure {

    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                ResourceKey<World> destinationType = net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:boiler_room_dimension"));
                if (M.dimension(M.level(_player)) == destinationType) {
                    return;
                }
                WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                if (nextLevel != null) {
                    M.connection(_player).send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                    M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                    M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));
                    for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                        M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                    }
                    M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                }
            }
            if (entity instanceof EntityPlayer _player) {
                ItemStack _stktoremove = itemstack;
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }
        }
    }
}
