package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BigDaddyThisEntityKillsAnotherOneProcedure {
    public static void execute(World world, double x, double y, double z) {
        Vec3 _center = new Vec3(x, y, z);

        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
            .toList()) {
            if (entityiterator instanceof LittleSisterEntity && world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(M.getX(entityiterator), M.getY(entityiterator), M.getZ(entityiterator)),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:little_sister_bd_kills")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        M.getX(entityiterator),
                        M.getY(entityiterator),
                        M.getZ(entityiterator),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:little_sister_bd_kills")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }
        }
    }
}
