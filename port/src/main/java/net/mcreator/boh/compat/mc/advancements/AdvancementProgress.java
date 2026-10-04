package net.mcreator.boh.compat.mc.advancements;

import java.util.Collections;

import net.mcreator.boh.compat.advancement.Advancements;
import net.minecraft.entity.player.EntityPlayerMP;

/** Progress of one advancement: a single implicit criterion ("grant"). */
public final class AdvancementProgress {

    private final EntityPlayerMP player;
    private final Advancement advancement;

    public AdvancementProgress(EntityPlayerMP player, Advancement advancement) {
        this.player = player;
        this.advancement = advancement;
    }

    public boolean isDone() {
        return Advancements.isDone(player, advancement);
    }

    public boolean hasProgress() {
        return isDone();
    }

    public Iterable<String> getRemainingCriteria() {
        return isDone() ? Collections.<String>emptyList() : Collections.singletonList("grant");
    }

    public Iterable<String> getCompletedCriteria() {
        return isDone() ? Collections.singletonList("grant") : Collections.<String>emptyList();
    }
}
