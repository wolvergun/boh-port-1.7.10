package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.BigDaddyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BigDaddySoundProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(
                event,
                M.level(M.getEntity(event)),
                M.getX(M.getEntity(event)),
                M.getY(M.getEntity(event)),
                M.getZ(M.getEntity(event)),
                M.getEntity(M.getSource(event))
            );
        }
    }

    public static void execute(World world, double x, double y, double z, Entity sourceentity) {
        execute(null, world, x, y, z, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity sourceentity) {
        if (sourceentity != null && sourceentity instanceof BigDaddyEntity && world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")),
                    SoundSource.NEUTRAL,
                    1.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:big_daddy_melee")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
                );
            }
        }
    }
}
