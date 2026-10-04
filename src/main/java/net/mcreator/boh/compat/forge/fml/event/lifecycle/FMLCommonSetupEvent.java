package net.mcreator.boh.compat.forge.fml.event.lifecycle;

import cpw.mods.fml.common.eventhandler.Event;

public class FMLCommonSetupEvent extends Event {
    public void enqueueWork(Runnable r) {
        r.run();
    }
}
