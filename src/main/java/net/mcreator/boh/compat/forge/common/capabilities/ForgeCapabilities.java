package net.mcreator.boh.compat.forge.common.capabilities;

import net.mcreator.boh.compat.forge.items.IItemHandler;

public final class ForgeCapabilities {
    public static final Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.named("forge:item_handler");

    private ForgeCapabilities() {
    }
}
