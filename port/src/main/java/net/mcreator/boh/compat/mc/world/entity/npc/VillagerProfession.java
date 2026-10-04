package net.mcreator.boh.compat.mc.world.entity.npc;

import java.util.function.Predicate;

import net.mcreator.boh.compat.mc.sounds.SoundEvent;

/** 1.20 VillagerProfession; registered as a 1.7.10 VillagerRegistry profession id. */
public class VillagerProfession {

    private final String name;
    private final SoundEvent workSound;
    private int legacyId = -1;

    public VillagerProfession(String name, Predicate<?> heldJobSite, Predicate<?> acquirableJobSite, Object requestedItems,
        Object secondaryPoi, SoundEvent workSound) {
        this.name = name;
        this.workSound = workSound;
    }

    public String name() {
        return name;
    }

    public SoundEvent workSound() {
        return workSound;
    }

    public int legacyId() {
        return legacyId;
    }

    public void setLegacyId(int id) {
        legacyId = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
