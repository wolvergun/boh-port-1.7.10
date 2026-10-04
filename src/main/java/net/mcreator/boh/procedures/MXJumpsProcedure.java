package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.MXEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class MXJumpsProcedure {
    @SubscribeEvent
    public void onEntityJump(LivingJumpEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null && entity instanceof MXEntity) {
            if (world instanceof World && M.isClientSide(world)) {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:mx_jump")), SoundSource.HOSTILE, 1.0F, 0.2F, false
                );
            }

            if (entity instanceof MXEntity) {
                ((MXEntity)entity).setAnimation("jump");
            }
        }
    }
}
