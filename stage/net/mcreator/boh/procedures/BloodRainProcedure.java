package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.init.BohModParticleTypes;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BloodRainProcedure {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.getX(M.player(event)), M.getY(M.player(event)), M.getZ(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, (Potion) BohModMobEffects.COGNITO_HAZART.get()) && M.getBoolean(M.getPersistentData(entity), "blood_rain") && M.isClientSide(world)) {
                M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -20, 20), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -20, 20), 0.0, 0.0, 0.0);
                M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -20, 20), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -20, 20), 0.0, 0.0, 0.0);
                M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -20, 20), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -20, 20), 0.0, 0.0, 0.0);
                M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -20, 20), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -20, 20), 0.0, 0.0, 0.0);
                M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -20, 20), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -20, 20), 0.0, 0.0, 0.0);
                M.addParticle(world, (SimpleParticleType) BohModParticleTypes.BLOOD_FALL.get(), M.getX(entity) + Mth.nextInt(RandomSource.create(), -20, 20), M.getY(entity) + 25.0, M.getZ(entity) + Mth.nextInt(RandomSource.create(), -20, 20), 0.0, 0.0, 0.0);
                if (Math.random() < 0.33 && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")), SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("weather.rain")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
            }
        }
    }
}
