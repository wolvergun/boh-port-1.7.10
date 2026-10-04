package net.mcreator.boh.compat.forge.fml.event.lifecycle;

import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 FMLCommonSetupEvent, posted on the mod bus during init. */
public class FMLCommonSetupEvent extends Event {

    public void enqueueWork(Runnable r) {
        r.run();
    }
}
