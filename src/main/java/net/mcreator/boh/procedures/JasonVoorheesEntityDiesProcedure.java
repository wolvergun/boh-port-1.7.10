package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class JasonVoorheesEntityDiesProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (world instanceof WorldServer _level) {
            Entity entityToSpawn = M.spawn(BohModEntities.JASON_MASK.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                M.setYRot(entityToSpawn, (float)Mth.nextDouble(RandomSource.create(), 1.0, 360.0));
                M.setYBodyRot(entityToSpawn, (float)Mth.nextDouble(RandomSource.create(), 1.0, 360.0));
                M.setYHeadRot(entityToSpawn, (float)Mth.nextDouble(RandomSource.create(), 1.0, 360.0));
                M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
            }
        }

        if (Math.random() < 0.25 && world instanceof WorldServer _levelx) {
            EntityItem entityToSpawn = M.new_EntityItem(_levelx, x, y, z, M.new_ItemStack(BohModItems.MACHETE.get()));
            M.setPickUpDelay(entityToSpawn, 10);
            M.addFreshEntity(_levelx, entityToSpawn);
        }
    }
}
