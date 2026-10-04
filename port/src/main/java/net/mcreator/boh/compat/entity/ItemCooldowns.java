package net.mcreator.boh.compat.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

/** 1.20 per-player item cooldowns (1.7.10 has none). Tracked separately on each side. */
public final class ItemCooldowns {

    private static final Map<EntityPlayer, ItemCooldowns> BY_PLAYER = new WeakHashMap<>();

    private final EntityPlayer player;
    private final Map<Item, long[]> cooldowns = new HashMap<>();

    private ItemCooldowns(EntityPlayer player) {
        this.player = player;
    }

    public static synchronized ItemCooldowns of(EntityPlayer p) {
        return BY_PLAYER.computeIfAbsent(p, ItemCooldowns::new);
    }

    private long now() {
        return player.worldObj.getTotalWorldTime();
    }

    public void addCooldown(Item item, int ticks) {
        cooldowns.put(item, new long[] { now(), now() + ticks });
        if (!player.worldObj.isRemote && player instanceof net.minecraft.entity.player.EntityPlayerMP)
            net.mcreator.boh.compat.net.CompatNetwork.sendCooldown((net.minecraft.entity.player.EntityPlayerMP) player, item, ticks);
    }

    public boolean isOnCooldown(Item item) {
        long[] c = cooldowns.get(item);
        return c != null && now() < c[1];
    }

    /** 0..1 remaining fraction, for the hotbar overlay. */
    public float getCooldownPercent(Item item, float partial) {
        long[] c = cooldowns.get(item);
        if (c == null) return 0;
        float total = c[1] - c[0];
        float left = c[1] - (now() + partial);
        return total <= 0 ? 0 : Math.max(0, Math.min(1, left / total));
    }

    public void removeCooldown(Item item) {
        cooldowns.remove(item);
    }
}
