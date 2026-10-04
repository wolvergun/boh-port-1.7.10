package net.mcreator.boh.compat.forge.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.mcreator.boh.compat.mc.commands.Commands;

public class RegisterCommandsEvent extends Event {
    public Commands getDispatcher() {
        return Commands.INSTANCE;
    }
}
