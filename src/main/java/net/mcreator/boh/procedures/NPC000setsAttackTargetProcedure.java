package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.NPC000Entity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NPC000setsAttackTargetProcedure {
    @SubscribeEvent
    public void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        execute(null, world, x, y, z, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null
            && sourceentity instanceof NPC000Entity
            && !(sourceentity instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, MobEffects.LUCK))) {
            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.LUCK, 1000, 0, false, false));
            }

            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 0, false, false));
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:npc_000_roar")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:npc_000_roar")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                    );
                }
            }
        }
    }
}
