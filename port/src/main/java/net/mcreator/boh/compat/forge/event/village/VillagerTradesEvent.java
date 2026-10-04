package net.mcreator.boh.compat.forge.event.village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import cpw.mods.fml.common.eventhandler.Event;

/** Forge VillagerTradesEvent: trades per level (1-5) for one profession. */
public class VillagerTradesEvent extends Event {

    private final VillagerProfession type;
    private final Map<Integer, List<Object>> trades = new HashMap<>();

    public VillagerTradesEvent() {
        this(null);
    }

    public VillagerTradesEvent(VillagerProfession type) {
        this.type = type;
        for (int i = 1; i <= 5; i++) trades.put(i, new ArrayList<>());
    }

    public VillagerProfession getType() {
        return type;
    }

    public Map<Integer, List<Object>> getTrades() {
        return trades;
    }
}
