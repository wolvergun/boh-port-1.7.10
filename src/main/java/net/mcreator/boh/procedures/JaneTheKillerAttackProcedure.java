package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.entity.JaneTheKillerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class JaneTheKillerAttackProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && sourceentity instanceof JaneTheKillerEntity) {
            if (!M.isClientSide(M.level(sourceentity)) && M.getServer(sourceentity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(sourceentity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(sourceentity),
                        M.getRotationVector(sourceentity),
                        M.level(sourceentity) instanceof WorldServer ? (WorldServer)M.level(sourceentity) : null,
                        4,
                        M.getString(M.getName(sourceentity)),
                        M.getDisplayName(sourceentity),
                        M.getServer(M.level(sourceentity)),
                        sourceentity
                    ),
                    "/particle minecraft:sweep_attack ~ ~1.2 ~"
                );
            }

            if (!M.isClientSide(M.level(sourceentity)) && M.getServer(sourceentity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(sourceentity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(sourceentity),
                        M.getRotationVector(sourceentity),
                        M.level(sourceentity) instanceof WorldServer ? (WorldServer)M.level(sourceentity) : null,
                        4,
                        M.getString(M.getName(sourceentity)),
                        M.getDisplayName(sourceentity),
                        M.getServer(M.level(sourceentity)),
                        sourceentity
                    ),
                    "/playsound minecraft:entity.player.attack.strong hostile @a"
                );
            }

            if (Math.random() < 0.6 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                M.performPrefixedCommand(
                    M.getCommands(M.getServer(entity)),
                    new CommandSourceStack(
                        CommandSource.NULL,
                        M.position(entity),
                        M.getRotationVector(entity),
                        M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                        4,
                        M.getString(M.getName(entity)),
                        M.getDisplayName(entity),
                        M.getServer(M.level(entity)),
                        entity
                    ),
                    "effect give @s kurolib:bleeding 1 10"
                );
            }
        }
    }
}
