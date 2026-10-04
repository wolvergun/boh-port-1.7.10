package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Specimen9HeadRightclickedOnBlockProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(
                    BohModEntities.SPECIMEN_9_BOSS.get(), _level, BlockPos.containing(0.5 + x, 1.0 + y, 0.5 + z), MobSpawnType.MOB_SUMMONED
                );
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }

            if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr))) && entity instanceof EntityPlayer _player) {
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(itemstack) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }
        }
    }
}
