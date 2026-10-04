package net.mcreator.boh.compat.mc.world.entity.ai.village.poi;

import java.util.Set;

public class PoiType {
    private final Set<?> states;
    private final int maxTickets;
    private final int validRange;

    public PoiType(Set<?> states, int maxTickets, int validRange) {
        this.states = states;
        this.maxTickets = maxTickets;
        this.validRange = validRange;
    }

    public Set<?> states() {
        return this.states;
    }
}
