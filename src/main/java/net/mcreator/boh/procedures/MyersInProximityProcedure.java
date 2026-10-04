package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;

public class MyersInProximityProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event);
        }
    }

    public static void execute() {
        execute(null);
    }

    private static void execute(@Nullable Event event) {
    }
}
