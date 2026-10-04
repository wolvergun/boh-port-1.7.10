package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.LightHeadEntity;
import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.level.ClipContext;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.ClipBlock;
import net.mcreator.boh.compat.mc.world.level.ClipFluid;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class AttackSirenheadProcedure {

    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof LightHeadEntity || sourceentity instanceof SirenHeadEntity) {
                BohMod.queueServerWork(6, () -> {
                    for (int index0 = 0; index0 < 5; index0++) {
                        M.levelEvent(world, 2001, BlockPos.containing(Mth.nextDouble(RandomSource.create(), -1.0, 1.0) + M.getX(M.getBlockPos(M.clip(M.level(sourceentity), new ClipContext(M.getEyePosition(sourceentity, 1.0F), M.getEyePosition(sourceentity, 1.0F).add(M.getViewVector(sourceentity, 1.0F).scale(5.0)), ClipBlock.OUTLINE, ClipFluid.NONE, sourceentity)))), y, Mth.nextDouble(RandomSource.create(), -1.0, 1.0) + M.getZ(M.getBlockPos(M.clip(M.level(sourceentity), new ClipContext(M.getEyePosition(sourceentity, 1.0F), M.getEyePosition(sourceentity, 1.0F).add(M.getViewVector(sourceentity, 1.0F).scale(5.0)), ClipBlock.OUTLINE, ClipFluid.NONE, sourceentity))))), M.blockStateId(M.getBlockState(world, BlockPos.containing(M.getX(M.getBlockPos(M.clip(M.level(sourceentity), new ClipContext(M.getEyePosition(sourceentity, 1.0F), M.getEyePosition(sourceentity, 1.0F).add(M.getViewVector(sourceentity, 1.0F).scale(5.0)), ClipBlock.OUTLINE, ClipFluid.NONE, sourceentity)))), y - 1.0, M.getZ(M.getBlockPos(M.clip(M.level(sourceentity), new ClipContext(M.getEyePosition(sourceentity, 1.0F), M.getEyePosition(sourceentity, 1.0F).add(M.getViewVector(sourceentity, 1.0F).scale(5.0)), ClipBlock.OUTLINE, ClipFluid.NONE, sourceentity))))))));
                    }
                    if (world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.HOSTILE, 3.0F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.HOSTILE, 3.0F, 1.0F, false);
                        }
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.LEVITATION, 2, 30, false, false));
                    }
                });
            }
        }
    }
}
