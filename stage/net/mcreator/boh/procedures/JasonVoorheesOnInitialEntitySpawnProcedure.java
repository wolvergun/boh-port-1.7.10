package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.JasonVoorheesEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class JasonVoorheesOnInitialEntitySpawnProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(5, () -> {
                if (Math.random() < 0.25) {
                    if (entity instanceof JasonVoorheesEntity animatable) {
                        animatable.setTexture("jason_4");
                    }
                    if (entity instanceof JasonVoorheesEntity _datEntSetI) {
                        M.set(M.getEntityData(_datEntSetI), JasonVoorheesEntity.DATA_variant, 1);
                    }
                } else if (Math.random() < 0.05) {
                    if (entity instanceof JasonVoorheesEntity animatable) {
                        animatable.setTexture("jason_nes");
                    }
                    if (entity instanceof JasonVoorheesEntity _datEntSetI) {
                        M.set(M.getEntityData(_datEntSetI), JasonVoorheesEntity.DATA_variant, 2);
                    }
                } else if (entity instanceof JasonVoorheesEntity _datEntSetI) {
                    M.set(M.getEntityData(_datEntSetI), JasonVoorheesEntity.DATA_variant, 0);
                }
            });
        }
    }
}
