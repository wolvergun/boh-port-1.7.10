package net.mcreator.boh.compat.forge.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class ForgeEventFactory {
    private ForgeEventFactory() {
    }

    public static boolean onAnimalTame(EntityLivingBase animal, EntityPlayer tamer) {
        return false;
    }

    public static boolean getMobGriefingEvent(World w, Entity e) {
        return w.getGameRules().getGameRuleBooleanValue("mobGriefing");
    }

    public static boolean onEntityDestroyBlock(EntityLivingBase e, Object pos, Object state) {
        return true;
    }
}
