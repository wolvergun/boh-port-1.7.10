package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WillowispRightClickedOnEntityProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == Items.GLASS_BOTTLE) {
            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }

            if (sourceentity instanceof EntityPlayer _player) {
                ItemStack _stktoremove = M.new_ItemStack(Items.GLASS_BOTTLE);
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }

            if (sourceentity instanceof EntityPlayer _player) {
                ItemStack _setstack = M.copy(M.new_ItemStack(BohModBlocks.JAR_O_WISP.get()));
                M.setCount(_setstack, 1);
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill_dragonbreath")),
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
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill_dragonbreath")),
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
