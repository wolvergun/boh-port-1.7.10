package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.TrimmingEntity;
import net.mcreator.boh.entity.VitaMimicEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class KillVitaEntitiesProcedure {

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, final World world, final double x, final double y, final double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof VitaMimicEntity || entity instanceof TrimmingEntity) {
                (new Object() {

                    void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModBlocks.VITA_CRAWL.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }
                        int tick2 = ticks;
                        BohMod.queueServerWork(tick2, () -> {
                            if (timedlooptotal > timedloopiterator + 1) {
                                this.timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
                            }
                        });
                    }
                }).timedLoop(0, (int) Mth.nextDouble(RandomSource.create(), 1.0, 4.0), 1);
            }
        }
    }
}
