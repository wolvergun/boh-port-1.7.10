package net.mcreator.boh.compat.forge.fml.event.lifecycle;

import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 FMLClientSetupEvent, posted on the mod bus during client init. */
public class FMLClientSetupEvent extends Event {

    public void enqueueWork(Runnable r) {
        r.run();
    }
}
