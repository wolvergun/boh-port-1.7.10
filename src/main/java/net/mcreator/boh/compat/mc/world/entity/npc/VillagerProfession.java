package net.mcreator.boh.compat.mc.world.entity.npc;

import java.util.function.Predicate;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;

public class VillagerProfession {
    private final String name;
    private final SoundEvent workSound;
    private int legacyId = -1;

    public VillagerProfession(
        String name, Predicate<?> heldJobSite, Predicate<?> acquirableJobSite, Object requestedItems, Object secondaryPoi, SoundEvent workSound
    ) {
        this.name = name;
        this.workSound = workSound;
    }

    public String name() {
        return this.name;
    }

    public SoundEvent workSound() {
        return this.workSound;
    }

    public int legacyId() {
        return this.legacyId;
    }

    public void setLegacyId(int id) {
        this.legacyId = id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
