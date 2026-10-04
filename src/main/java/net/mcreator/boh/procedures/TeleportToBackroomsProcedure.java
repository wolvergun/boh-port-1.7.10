package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportToBackroomsProcedure {
    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                ResourceKey<World> destinationType = Dimensions.dimensionKey(new ResourceLocation("boh:level_0"));
                if (M.dimension(M.level(_player)) == destinationType) {
                    return;
                }

                WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                if (nextLevel != null) {
                    M.connection(_player).send(new ClientboundGameEventPacket(4, 0.0F));
                    M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                    M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));

                    for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                        M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                    }

                    M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                }
            }

            if (entity instanceof EntityPlayer _player) {
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(itemstack) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }
        }
    }
}
