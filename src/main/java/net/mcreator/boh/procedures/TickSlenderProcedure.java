package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.entity.SotirisEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TickSlenderProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity) {
        if (entity != null) {
            if (!M.isEmpty(
                    M.getEntitiesOfClass(
                        world, SlenderManEntity.class, AABB.ofSize(new Vec3(M.getX(entity), M.getY(entity), M.getZ(entity)), 30.0, 30.0, 30.0), e -> true
                    )
                )
                && world instanceof WorldServer _level) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(_level)),
                    M.withSuppressedOutput(
                        new CommandSourceStack(
                            CommandSource.NULL,
                            new Vec3(M.getXSpawn(M.getLevelData(world)), M.getYSpawn(M.getLevelData(world)), M.getZSpawn(M.getLevelData(world))),
                            Vec2.ZERO,
                            _level,
                            4,
                            "",
                            Component.literal(""),
                            M.getServer(_level),
                            null
                        )
                    ),
                    "function boh:tick"
                );
            }

            if (!M.isEmpty(
                    M.getEntitiesOfClass(
                        world, SotirisEntity.class, AABB.ofSize(new Vec3(M.getX(entity), M.getY(entity), M.getZ(entity)), 30.0, 30.0, 30.0), e -> true
                    )
                )
                && world instanceof WorldServer _level) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(_level)),
                    M.withSuppressedOutput(
                        new CommandSourceStack(
                            CommandSource.NULL,
                            new Vec3(M.getXSpawn(M.getLevelData(world)), M.getYSpawn(M.getLevelData(world)), M.getZSpawn(M.getLevelData(world))),
                            Vec2.ZERO,
                            _level,
                            4,
                            "",
                            Component.literal(""),
                            M.getServer(_level),
                            null
                        )
                    ),
                    "function boh:ticksot"
                );
            }
        }
    }
}
