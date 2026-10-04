package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
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

public class EquipAGWOBProcedure {
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
            if (!M.getBoolean(M.getPersistentData(entity), "agwob_sound")
                && (
                    M.getItem(entity instanceof EntityLivingBase _livEntx ? M.getOffhandItem(_livEntx) : M.EMPTY) == BohModItems.GUN_WITH_ONE_BULLET.get()
                        || M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)
                            == BohModItems.GUN_WITH_ONE_BULLET.get()
                )) {
                if (M.isClientSide(world) && Math.random() < 0.05 && world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:agwob_equip")),
                            SoundSource.AMBIENT,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:agwob_equip")),
                            SoundSource.AMBIENT,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "agwob_sound", true);
            }

            if (M.getItem(entity instanceof EntityLivingBase _livEntx ? M.getOffhandItem(_livEntx) : M.EMPTY) != BohModItems.GUN_WITH_ONE_BULLET.get()
                && M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) != BohModItems.GUN_WITH_ONE_BULLET.get()) {
                M.putBoolean(M.getPersistentData(entity), "agwob_sound", false);
            }
        }
    }
}
