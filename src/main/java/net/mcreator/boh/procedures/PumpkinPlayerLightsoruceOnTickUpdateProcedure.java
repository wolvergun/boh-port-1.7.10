package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.PumpkinPlayerEntity;
import net.minecraft.world.World;

public class PumpkinPlayerLightsoruceOnTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (M.isEmpty(M.getEntitiesOfClass(world, PumpkinPlayerEntity.class, AABB.ofSize(new Vec3(x, y, z), 2.0, 2.0, 2.0), e -> true))) {
            M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(Blocks.AIR), 3);
        }
    }
}
