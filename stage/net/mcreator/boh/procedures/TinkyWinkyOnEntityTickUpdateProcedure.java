package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class TinkyWinkyOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (!(entity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.SATURATION)) && Math.random() < 0.02 && Math.random() < 0.02 && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.SATURATION, 30, 0, false, false));
                }
                if (Math.random() < 0.2 && Math.random() < 0.12 && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tinkywinky_chase")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:tinkywinky_chase")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
            }
            if (!(entity instanceof EntityLiving _mob && M.isAggressive(_mob)) && Math.random() < 0.01 && Math.random() < 0.005 && M.isEmptyBlock(world, BlockPos.containing(x, y, z))) {
                M.setBlock(world, BlockPos.containing(x, y, z), M.defaultBlockState(((Block) BohModBlocks.TUBBY_CUSTARD.get())), 3);
            }
        }
    }
}
