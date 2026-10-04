package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class PaintedSwordKillProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
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
        if (sourceentity != null
            && M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == BohModItems.PAINTED_SWORD.get()
            && world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:painted_sword_kill")),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world,
                    x,
                    y,
                    z,
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:painted_sword_kill")),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F,
                    false
                );
            }
        }
    }
}
