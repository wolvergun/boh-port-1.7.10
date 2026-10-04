package net.mcreator.boh.compat.mc.world.entity.ai.village.poi;

import java.util.Set;

/** 1.20 point-of-interest type (the job-site block of a profession). */
public class PoiType {

    private final Set<?> states;
    private final int maxTickets, validRange;

    public PoiType(Set<?> states, int maxTickets, int validRange) {
        this.states = states;
        this.maxTickets = maxTickets;
        this.validRange = validRange;
    }

    public Set<?> states() {
        return states;
    }
}
