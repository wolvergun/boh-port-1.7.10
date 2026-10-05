package net.mcreator.boh.compat.world;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.entity.BohPainting;
import net.mcreator.boh.compat.mc.world.entity.decoration.PaintingVariant;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 1.20 places a random painting among every registered variant that fits the wall, the mod's included. Here a vanilla
 * painting placement becomes one of the mod's paintings with the same odds: (fitting mod variants) / (fitting vanilla
 * arts + fitting mod variants). Otherwise vanilla places its own.
 */
public final class Paintings {

    private Paintings() {}

    public static void install() {
        MinecraftForge.EVENT_BUS.register(new Paintings());
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent e) {
        if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || e.world.isRemote || e.face < 2 || e.face > 5) return;
        ItemStack held = e.entityPlayer.getCurrentEquippedItem();
        if (held == null || held.getItem() != Items.painting || Registration.PAINTINGS.isEmpty()) return;
        if (!e.entityPlayer.canPlayerEdit(e.x, e.y, e.z, e.face, held)) return;
        World w = e.world;
        int dir = Direction.facingToDirection[e.face];

        int vanilla = 0;
        EntityPainting probe = new EntityPainting(w, e.x, e.y, e.z, dir);
        for (EntityPainting.EnumArt art : EntityPainting.EnumArt.values()) {
            probe.art = art;
            probe.setDirection(dir);
            if (probe.onValidSurface()) vanilla++;
        }
        List<BohPainting> mod = new ArrayList<>();
        for (PaintingVariant v : Registration.PAINTINGS) {
            BohPainting p = new BohPainting(w, e.x, e.y, e.z, dir, v);
            if (p.onValidSurface()) mod.add(p);
        }
        if (mod.isEmpty() || w.rand.nextInt(vanilla + mod.size()) < vanilla) return;

        w.spawnEntityInWorld(mod.get(w.rand.nextInt(mod.size())));
        if (!e.entityPlayer.capabilities.isCreativeMode && --held.stackSize <= 0)
            e.entityPlayer.inventory.setInventorySlotContents(e.entityPlayer.inventory.currentItem, null);
        e.setCanceled(true);
    }
}
