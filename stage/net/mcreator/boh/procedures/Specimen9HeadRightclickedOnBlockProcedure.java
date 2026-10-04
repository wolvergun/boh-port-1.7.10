package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class Specimen9HeadRightclickedOnBlockProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (world instanceof WorldServer _level) {
                Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.SPECIMEN_9_BOSS.get()), _level, BlockPos.containing(0.5 + x, 1.0 + y, 0.5 + z), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                }
            }
            if (!(entity instanceof EntityPlayer _plr && M.instabuild(M.getAbilities(_plr))) && entity instanceof EntityPlayer _player) {
                ItemStack _stktoremove = itemstack;
                M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
            }
        }
    }
}
