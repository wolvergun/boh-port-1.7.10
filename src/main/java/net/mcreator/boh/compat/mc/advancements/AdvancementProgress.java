package net.mcreator.boh.compat.mc.advancements;

import java.util.Collections;
import net.mcreator.boh.compat.advancement.Advancements;
import net.minecraft.entity.player.EntityPlayerMP;

public final class AdvancementProgress {
    private final EntityPlayerMP player;
    private final Advancement advancement;

    public AdvancementProgress(EntityPlayerMP player, Advancement advancement) {
        this.player = player;
        this.advancement = advancement;
    }

    public boolean isDone() {
        return Advancements.isDone(this.player, this.advancement);
    }

    public boolean hasProgress() {
        return this.isDone();
    }

    public Iterable<String> getRemainingCriteria() {
        return this.isDone() ? Collections.emptyList() : Collections.singletonList("grant");
    }

    public Iterable<String> getCompletedCriteria() {
        return this.isDone() ? Collections.singletonList("grant") : Collections.emptyList();
    }
}
