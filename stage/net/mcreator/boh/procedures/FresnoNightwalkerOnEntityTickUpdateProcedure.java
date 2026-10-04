package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.FresnoNightcrawlerEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class FresnoNightwalkerOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            double confuse_timer = 0.0;
            if (world instanceof World _lvl0 && M.isDay(_lvl0) && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 10, 0, false, false));
            }
            if (!M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true)) && entity instanceof FresnoNightcrawlerEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), FresnoNightcrawlerEntity.DATA_fresno_confused, (entity instanceof FresnoNightcrawlerEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(FresnoNightcrawlerEntity.DATA_fresno_confused) : 0) + 1);
            }
            if ((entity instanceof FresnoNightcrawlerEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(FresnoNightcrawlerEntity.DATA_fresno_confused) : 0) == 100) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:fresno_happy")), SoundSource.NEUTRAL, 1.0F, 0.8F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:fresno_happy")), SoundSource.NEUTRAL, 1.0F, 0.8F, false);
                    }
                }
                if (entity instanceof FresnoNightcrawlerEntity) {
                    ((FresnoNightcrawlerEntity) entity).setAnimation("confused");
                }
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
                }
            }
            if (M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true)) && entity instanceof FresnoNightcrawlerEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), FresnoNightcrawlerEntity.DATA_fresno_confused, 0);
            }
        }
    }
}
