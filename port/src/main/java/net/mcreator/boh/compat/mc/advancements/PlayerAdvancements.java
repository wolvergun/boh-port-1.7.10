package net.mcreator.boh.compat.mc.advancements;

import net.mcreator.boh.compat.advancement.Advancements;
import net.minecraft.entity.player.EntityPlayerMP;

/** 1.20 PlayerAdvancements over 1.7.10 achievement stats. */
public final class PlayerAdvancements {

    private final EntityPlayerMP player;

    public PlayerAdvancements(EntityPlayerMP player) {
        this.player = player;
    }

    public AdvancementProgress getOrStartProgress(Advancement a) {
        return new AdvancementProgress(player, a);
    }

    public boolean award(Advancement a, String criterion) {
        if (a == null || Advancements.isDone(player, a)) return false;
        Advancements.grant(player, a);
        return true;
    }

    public boolean revoke(Advancement a, String criterion) {
        Advancements.revoke(player, a);
        return true;
    }
}
