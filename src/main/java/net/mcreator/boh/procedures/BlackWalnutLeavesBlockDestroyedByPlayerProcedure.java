package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlackWalnutLeavesBlockDestroyedByPlayerProcedure {
    public static void execute(final World world, final double x, final double y, final double z, Entity entity) {
        if (entity != null && M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != Items.SHEARS) {
            if (Math.random() < 0.05 && world instanceof WorldServer _level) {
                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.BLACK_WALLNUT_SAPPLING.get()));
                M.setPickUpDelay(entityToSpawn, 10);
                M.addFreshEntity(_level, entityToSpawn);
            }

            if (Math.random() < 0.1) {
                (new Object() {
                    void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.WALNUT.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }

                        BohMod.queueServerWork(ticks, () -> {
                            if (timedlooptotal > timedloopiterator + 1) {
                                this.timedLoop(timedloopiterator + 1, timedlooptotal, ticks);
                            }
                        });
                    }
                }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 4.0), 1);
            }
        }
    }
}
