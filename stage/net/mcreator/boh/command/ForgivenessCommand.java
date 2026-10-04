package net.mcreator.boh.command;

import net.mcreator.boh.procedures.GuiOpenForgiveProcedure;
import net.mcreator.boh.compat.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.mcreator.boh.compat.forge.event.RegisterCommandsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class ForgivenessCommand {

    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent event) {
        M.getDispatcher(event).register((LiteralArgumentBuilder) M.executes(((LiteralArgumentBuilder) M.requires(Commands.literal("forgiveness"), s -> M.hasPermission(s, 4))), arguments -> {
            World world = M.getUnsidedLevel(((CommandSourceStack) M.getSource(arguments)));
            double x = M.getPosition(((CommandSourceStack) M.getSource(arguments))).x();
            double y = M.getPosition(((CommandSourceStack) M.getSource(arguments))).y();
            double z = M.getPosition(((CommandSourceStack) M.getSource(arguments))).z();
            Entity entity = M.getEntity(((CommandSourceStack) M.getSource(arguments)));
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
