package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class JeffTheKillerOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                M.putDouble(M.getPersistentData(entity), "radio_static", M.getDouble(M.getPersistentData(entity), "radio_static") + 1.0);
                if (M.getDouble(M.getPersistentData(entity), "radio_static") == 360.0) {
                    M.putDouble(M.getPersistentData(entity), "radio_static", M.getDouble(M.getPersistentData(entity), "radio_static") + 1.0);
                    if (Math.random() < 0.3 && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_laugh")), SoundSource.AMBIENT, 1.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jeff_laugh")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        }
                    }
                }
            }
        }
    }
}
