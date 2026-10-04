package net.mcreator.boh.compat.forge.event.village;

import cpw.mods.fml.common.eventhandler.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;

public class VillagerTradesEvent extends Event {
    private final VillagerProfession type;
    private final Map<Integer, List<Object>> trades = new HashMap<>();

    public VillagerTradesEvent() {
        this(null);
    }

    public VillagerTradesEvent(VillagerProfession type) {
        this.type = type;

        for (int i = 1; i <= 5; i++) {
            this.trades.put(i, new ArrayList<>());
        }
    }

    public VillagerProfession getType() {
        return this.type;
    }

    public Map<Integer, List<Object>> getTrades() {
        return this.trades;
    }
}
