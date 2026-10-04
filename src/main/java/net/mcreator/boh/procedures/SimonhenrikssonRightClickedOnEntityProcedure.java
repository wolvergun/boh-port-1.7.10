package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SimonhenrikssonRightClickedOnEntityProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && (
                M.getItem(sourceentity instanceof EntityLivingBase _livEntxxx ? M.getMainHandItem(_livEntxxx) : M.EMPTY) == Items.BOOK
                    || M.getItem(sourceentity instanceof EntityLivingBase _livEntxx ? M.getOffhandItem(_livEntxx) : M.EMPTY) == Items.BOOK
                    || M.getItem(sourceentity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY) == Items.WRITABLE_BOOK
                    || M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getOffhandItem(_livEnt) : M.EMPTY) == Items.WRITABLE_BOOK
            )) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.book.page_turn")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.book.page_turn")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }

            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(BohModEntities.BOOK_SIMON.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setYRot(entityToSpawn, M.getYRot(entity));
                    M.setYBodyRot(entityToSpawn, M.getYRot(entity));
                    M.setYHeadRot(entityToSpawn, M.getYRot(entity));
                    M.setXRot(entityToSpawn, M.getXRot(entity));
                    M.setDeltaMovement(entityToSpawn, M.getDeltaMovement(entity).x(), M.getDeltaMovement(entity).y(), M.getDeltaMovement(entity).z());
                }
            }

            if (sourceentity instanceof EntityPlayer _player) {
                ItemStack _stktoremove = M.new_ItemStack(Items.BOOK);
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }
        }
    }
}
