package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.item.ChainsawItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ChainsawItemInInventoryTickProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.getDouble(M.getOrCreateTag(itemstack), "rev") == 3.0) {
                M.putDouble(M.getOrCreateTag(itemstack), "rev_timer", M.getDouble(M.getOrCreateTag(itemstack), "rev_timer") + 1.0);
                M.putDouble(M.getOrCreateTag(itemstack), "timer_sound", M.getDouble(M.getOrCreateTag(itemstack), "timer_sound") + 1.0);
            }

            if (M.getDouble(M.getOrCreateTag(itemstack), "rev_timer") == 200.0) {
                if (entity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 100);
                }

                M.putDouble(M.getOrCreateTag(itemstack), "rev", 0.0);
                M.putDouble(M.getOrCreateTag(itemstack), "rev_timer", 0.0);
                if (M.getItem(itemstack) instanceof ChainsawItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "idle");
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_stall")),
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
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_stall")),
                            SoundSource.PLAYERS,
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
