package net.mcreator.boh.command;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.RegisterCommandsEvent;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.procedures.GuiOpenForgiveProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class ForgivenessCommand {
    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent event) {
        M.getDispatcher(event).register(M.executes(M.requires(Commands.literal("forgiveness"), s -> M.hasPermission(s, 4)), arguments -> {
            World world = M.getUnsidedLevel(M.getSource(arguments));
            double x = M.getPosition(M.getSource(arguments)).x();
            double y = M.getPosition(M.getSource(arguments)).y();
            double z = M.getPosition(M.getSource(arguments)).z();
            Entity entity = M.getEntity(M.getSource(arguments));
            if (entity == null && world instanceof WorldServer _servLevel) {
                entity = FakePlayerFactory.getMinecraft(_servLevel);
            }

            Direction direction = Direction.DOWN;
            if (entity != null) {
                direction = M.getDirection(entity);
            }

            GuiOpenForgiveProcedure.execute(world, x, y, z, entity);
            return 0;
        }));
    }
}
