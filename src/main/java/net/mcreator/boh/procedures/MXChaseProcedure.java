package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.MXEntity;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MXChaseProcedure {
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
            if (M.getCapability(entity, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables()).chase_mx) {
                M.putDouble(M.getPersistentData(entity), "timer_chase_exe", M.getDouble(M.getPersistentData(entity), "timer_chase_exe") + 1.0);
                if (!M.getBoolean(M.getPersistentData(entity), "loop")
                    && !M.isEmpty(M.getEntitiesOfClass(world, MXEntity.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true))) {
                    M.putBoolean(M.getPersistentData(entity), "loop", true);
                    if (M.isClientSide(world) && world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chase_mx")),
                                SoundSource.MUSIC,
                                0.5F,
                                1.0F
                            );
                        } else {
                            M.playLocalSound(
                                world,
                                x,
                                y,
                                z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chase_mx")),
                                SoundSource.MUSIC,
                                0.5F,
                                1.0F,
                                false
                            );
                        }
                    }
                }

                if (M.getBoolean(M.getPersistentData(entity), "loop") && M.getDouble(M.getPersistentData(entity), "timer_chase_exe") == 1140.0) {
                    M.putDouble(M.getPersistentData(entity), "timer_chase_exe", 0.0);
                    M.putBoolean(M.getPersistentData(entity), "loop", false);
                }
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, MXEntity.class, AABB.ofSize(new Vec3(x, y, z), 55.0, 55.0, 55.0), e -> true))) {
                boolean _setval = false;
                M.getCapability(entity, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.chase_mx = _setval;
                    capability.syncPlayerVariables(entity);
                });
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(
                        M.getCommands(M.getServer(_level)),
                        M.withSuppressedOutput(
                            new CommandSourceStack(
                                CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null
                            )
                        ),
                        "/stopsound @a music boh:chase_mx"
                    );
                }
            }
        }
    }
}
