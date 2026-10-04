package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.item.SirenphoneItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SirenphoneRightclickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            BohMod.queueServerWork(
                4,
                () -> {
                    if (!M.isClientSide(world) && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenphone")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sirenphone")),
                                SoundSource.PLAYERS,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }
                }
            );
            if (M.getItem(itemstack) instanceof SirenphoneItem) {
                M.putString(M.getOrCreateTag(itemstack), "geckoAnim", "shoot");
            }

            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(itemstack), 20);
            }
        }
    }
}
