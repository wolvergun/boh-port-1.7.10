package net.mcreator.boh.compat.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import net.mcreator.boh.compat.net.CompatNetwork;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

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
        return this.player.worldObj.getTotalWorldTime();
    }

    public void addCooldown(Item item, int ticks) {
        this.cooldowns.put(item, new long[]{this.now(), this.now() + ticks});
        if (!this.player.worldObj.isRemote && this.player instanceof EntityPlayerMP) {
            CompatNetwork.sendCooldown((EntityPlayerMP)this.player, item, ticks);
        }
    }

    public boolean isOnCooldown(Item item) {
        long[] c = this.cooldowns.get(item);
        return c != null && this.now() < c[1];
    }

    public float getCooldownPercent(Item item, float partial) {
        long[] c = this.cooldowns.get(item);
        if (c == null) {
            return 0.0F;
        } else {
            float total = (float)(c[1] - c[0]);
            float left = (float)c[1] - ((float)this.now() + partial);
            return total <= 0.0F ? 0.0F : Math.max(0.0F, Math.min(1.0F, left / total));
        }
    }

    public void removeCooldown(Item item) {
        this.cooldowns.remove(item);
    }
}
