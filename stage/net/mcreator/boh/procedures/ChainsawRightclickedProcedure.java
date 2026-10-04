package net.mcreator.boh.procedures;

import net.mcreator.boh.item.ChainsawItem;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class ChainsawRightclickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (M.getDouble(M.getOrCreateTag(itemstack), "rev") < 3.0 && !(entity instanceof EntityPlayer _plrCldCheck3 && M.isOnCooldown(M.getCooldowns(_plrCldCheck3), M.getItem(itemstack)))) {
                M.putDouble(M.getOrCreateTag(itemstack), "rev", M.getDouble(M.getOrCreateTag(itemstack), "rev") + 1.0);
                if (M.getItem(itemstack) instanceof ChainsawItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "rev");
                }
                if (entity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 10);
                }
            }
            if (!(entity instanceof EntityPlayer _plrCldCheck13 && M.isOnCooldown(M.getCooldowns(_plrCldCheck13), M.getItem(itemstack))) && M.getDouble(M.getOrCreateTag(itemstack), "rev") == 3.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_4")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_4")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_loop")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }
                if (M.getItem(itemstack) instanceof ChainsawItem) {
                    M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "active");
                }
            }
            if (M.getDouble(M.getOrCreateTag(itemstack), "rev") == 0.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_1")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_1")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "rev") == 1.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_2")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_2")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                    }
                }
            } else if (M.getDouble(M.getOrCreateTag(itemstack), "rev") == 2.0 && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_3")), SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_rev_3")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
