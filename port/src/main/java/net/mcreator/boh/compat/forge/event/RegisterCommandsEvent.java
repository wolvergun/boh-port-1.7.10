package net.mcreator.boh.compat.forge.event;

import net.mcreator.boh.compat.mc.commands.Commands;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 RegisterCommandsEvent: commands are collected in Commands.REGISTERED and exposed as 1.7.10 commands. */
public class RegisterCommandsEvent extends Event {

    public Commands getDispatcher() {
        return Commands.INSTANCE;
    }
}
