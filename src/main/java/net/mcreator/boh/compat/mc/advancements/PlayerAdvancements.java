package net.mcreator.boh.compat.mc.advancements;

import net.mcreator.boh.compat.advancement.Advancements;
import net.minecraft.entity.player.EntityPlayerMP;

public final class PlayerAdvancements {
    private final EntityPlayerMP player;

    public PlayerAdvancements(EntityPlayerMP player) {
        this.player = player;
    }

    public AdvancementProgress getOrStartProgress(Advancement a) {
        return new AdvancementProgress(this.player, a);
    }

    public boolean award(Advancement a, String criterion) {
        if (a != null && !Advancements.isDone(this.player, a)) {
            Advancements.grant(this.player, a);
            return true;
        } else {
            return false;
        }
    }

    public boolean revoke(Advancement a, String criterion) {
        Advancements.revoke(this.player, a);
        return true;
    }
}
