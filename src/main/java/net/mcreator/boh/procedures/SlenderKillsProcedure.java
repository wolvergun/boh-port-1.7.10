package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class SlenderKillsProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && !M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true))
            && entity instanceof EntityLivingBase _livEnt1
            && M.hasEffect(_livEnt1, BohModMobEffects.SLENDER_INFLUENCE_EFFECT.get())
            && world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:slender_kill")),
                    SoundSource.HOSTILE,
                    1.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:slender_kill")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                );
            }
        }
    }
}
