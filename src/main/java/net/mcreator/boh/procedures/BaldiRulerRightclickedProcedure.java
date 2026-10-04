package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BaldiRulerRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 30);
            }

            if (world instanceof World && M.isClientSide(world)) {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:baldi_ruler")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                );
            }

            M.putBoolean(M.getOrCreateTag(itemstack), "ruler_clap", true);
            if (entity instanceof EntityLivingBase _entity) {
                M.swing(_entity, InteractionHand.MAIN_HAND, true);
            }
        }
    }
}
