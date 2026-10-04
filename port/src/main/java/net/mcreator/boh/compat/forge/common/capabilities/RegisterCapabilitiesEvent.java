package net.mcreator.boh.compat.forge.common.capabilities;

import cpw.mods.fml.common.eventhandler.Event;

/** Forge RegisterCapabilitiesEvent (registration is implicit here). */
public class RegisterCapabilitiesEvent extends Event {

    public <T> void register(Class<T> type) {}
}
