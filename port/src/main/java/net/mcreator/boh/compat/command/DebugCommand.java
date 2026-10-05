package net.mcreator.boh.compat.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;

/**
 * /bohdebug [effect <name> [seconds] | dim <dimension> | inspect [radius]] - shows the player's mod effects and overlay
 * flags, applies a mod effect, teleports between dimensions, or dumps the server-side state of the nearest mob.
 */
public class DebugCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "bohdebug";
    }

    @Override
    public String getCommandUsage(ICommandSender s) {
        return "/bohdebug [effect <boh effect name> [seconds] | dim <dimension> | inspect [radius] [name]]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender s, String[] args) {
        EntityPlayerMP p = getCommandSenderAsPlayer(s);
        if (args.length >= 2 && args[0].equals("dim")) {
            net.mcreator.boh.compat.mc.resources.ResourceKey<net.minecraft.world.World> key = net.mcreator.boh.compat.world.Dimensions
                .dimensionKey(new net.minecraft.util.ResourceLocation(args[1].contains(":") ? args[1] : "boh:" + args[1]));
            int id = net.mcreator.boh.compat.world.Dimensions.id(key);
            if (id == 0 && !args[1].endsWith("overworld")) {
                s.addChatMessage(new ChatComponentText("Unknown dimension " + args[1] + " (level_0, baseplate_dimension, boiler_room_dimension, gaster_dimension, overworld)"));
                return;
            }
            net.mcreator.boh.compat.world.Dimensions.transferPlayer(p, id, p.posX, id == 0 ? 100 : p.posY, p.posZ, p.rotationYaw, p.rotationPitch);
            s.addChatMessage(new ChatComponentText("Sent to " + key.location() + " (dimension id " + id + ")"));
            return;
        }
        if (args.length >= 1 && args[0].equals("inspect")) {
            // /bohdebug inspect [radius] [name filter], e.g. /bohdebug inspect lifeform
            String filter = null;
            double radius = 16;
            for (int i = 1; i < args.length; i++) {
                if (args[i].matches("[0-9.]+")) radius = parseDoubleBounded(s, args[i], 1, 128);
                else filter = args[i].toLowerCase();
            }
            inspect(s, p, radius, filter);
            return;
        }
        if (args.length >= 2 && args[0].equals("effect")) {
            Potion pot = net.mcreator.boh.compat.forge.registries.ForgeRegistries.MOB_EFFECTS
                .getValue(new net.minecraft.util.ResourceLocation(args[1].contains(":") ? args[1] : "boh:" + args[1]));
            if (pot == null) {
                s.addChatMessage(new ChatComponentText("Unknown effect " + args[1]));
                return;
            }
            int secs = args.length >= 3 ? parseInt(s, args[2]) : 15;
            p.addPotionEffect(new PotionEffect(pot.id, secs * 20, 0));
            s.addChatMessage(new ChatComponentText("Applied " + pot.getName() + " (id " + pot.id + ") for " + secs + "s"));
            return;
        }
        s.addChatMessage(new ChatComponentText("Active effects:"));
        for (Object o : p.getActivePotionEffects()) {
            PotionEffect e = (PotionEffect) o;
            Potion pot = Potion.potionTypes[e.getPotionID()];
            s.addChatMessage(new ChatComponentText("  " + (pot == null ? "?" : pot.getName()) + " id=" + e.getPotionID() + " t=" + e.getDuration()));
        }
        net.minecraft.nbt.NBTTagCompound d = p.getEntityData();
        s.addChatMessage(new ChatComponentText("exe_static=" + d.getDouble("exe_static") + " exe_apparison=" + d.getDouble("exe_apparison")
            + " (server side; overlays use the client copy)"));
    }

    /** Server-side state of the nearest non-player living entity, for chasing mobs that look dead or never fight. */
    private static void inspect(ICommandSender s, EntityPlayerMP p, double radius, String filter) {
        EntityLivingBase best = null;
        double bestD = Double.MAX_VALUE;
        for (Object o : p.worldObj.getEntitiesWithinAABBExcludingEntity(p, p.boundingBox.expand(radius, radius, radius))) {
            if (o instanceof EntityLivingBase && !(o instanceof EntityPlayer)
                && (filter == null || o.getClass().getSimpleName().toLowerCase().contains(filter))) {
                double d = p.getDistanceSqToEntity((Entity) o);
                if (d < bestD) {
                    bestD = d;
                    best = (EntityLivingBase) o;
                }
            }
        }
        // also scan the world's entity list: a mob missing from its chunk's list would not show up in the box search above
        java.util.List<EntityLivingBase> all = new java.util.ArrayList<>();
        for (Object o : p.worldObj.loadedEntityList) {
            if (o instanceof EntityLivingBase && !(o instanceof EntityPlayer) && p.getDistanceToEntity((Entity) o) <= radius * 2) all.add((EntityLivingBase) o);
        }
        all.sort(java.util.Comparator.comparingDouble(e -> p.getDistanceToEntity(e)));
        StringBuilder near = new StringBuilder("[server] mobs within " + radius * 2 + ":");
        for (EntityLivingBase e : all.subList(0, Math.min(8, all.size()))) {
            near.append(String.format(" %s#%d(%.1f)", e.getClass().getSimpleName(), e.getEntityId(), p.getDistanceToEntity(e)));
        }
        msg(s, near.toString());
        if (best == null) {
            s.addChatMessage(new ChatComponentText("No mob within " + radius + " blocks"));
            return;
        }
        EntityLivingBase e = best;
        msg(s, e.getClass().getSimpleName() + " #" + e.getEntityId() + String.format(" at %.1f %.1f %.1f (%.1f blocks)", e.posX, e.posY, e.posZ, Math.sqrt(bestD)));
        msg(s, "health=" + e.getHealth() + "/" + e.getMaxHealth() + " isDead=" + e.isDead + " alive=" + e.isEntityAlive() + " deathTime=" + e.deathTime
            + " hurtTime=" + e.hurtTime + " invulnerable=" + e.isEntityInvulnerable() + " hurtResistantTime=" + e.hurtResistantTime);
        msg(s, String.format("size=%.2fx%.2f box=[%.1f %.1f %.1f -> %.1f %.1f %.1f] onGround=%s riding=%s ridden=%s sleeping=%s", e.width, e.height,
            e.boundingBox.minX, e.boundingBox.minY, e.boundingBox.minZ, e.boundingBox.maxX, e.boundingBox.maxY, e.boundingBox.maxZ, e.onGround,
            name(e.ridingEntity), name(e.riddenByEntity), e instanceof EntityPlayer && ((EntityPlayer) e).isPlayerSleeping()));
        if (e instanceof net.mcreator.boh.compat.entity.BohMob) {
            msg(s, "noAi=" + ((net.mcreator.boh.compat.entity.BohMob) e).isNoAi());
        }
        if (e instanceof EntityLiving) {
            EntityLiving l = (EntityLiving) e;
            msg(s, "attackTarget=" + name(l.getAttackTarget()) + (l instanceof EntityCreature ? " entityToAttack=" + name(((EntityCreature) l).getEntityToAttack()) : "")
                + " path=" + (l.getNavigator().noPath() ? "none" : "yes") + " persistent=" + l.isNoDespawnRequired());
            msg(s, "running goals: " + running(l.tasks) + " | targets: " + running(l.targetTasks));
            EntityLivingBase t = l.getAttackTarget();
            net.minecraft.pathfinding.PathEntity path = t == null ? null : l.getNavigator().getPathToEntityLiving(t);
            msg(s, "navigator=" + l.getNavigator().getClass().getSimpleName() + " searchRange=" + l.getNavigator().getPathSearchRange()
                + (t == null ? "" : String.format(" pathToTarget=%s (target %.1f blocks away)",
                    path == null ? "NONE" : path.getCurrentPathLength() + " points", l.getDistanceToEntity(t))));
        }
    }

    public static String running(EntityAITasks tasks) {
        StringBuilder b = new StringBuilder();
        try {
            java.lang.reflect.Field f;
            try {
                f = EntityAITasks.class.getDeclaredField("executingTaskEntries");
            } catch (NoSuchFieldException ex) {
                f = EntityAITasks.class.getDeclaredField("field_75780_b");
            }
            f.setAccessible(true);
            for (Object o : (java.util.List<?>) f.get(tasks)) {
                EntityAIBase a = ((EntityAITasks.EntityAITaskEntry) o).action;
                b.append(b.length() > 0 ? ", " : "").append(a.getClass().getName().replaceAll(".*[.$]", ""));
            }
        } catch (ReflectiveOperationException ex) {
            return "? (" + ex + ")";
        }
        return b.length() == 0 ? "none" : b.toString();
    }

    private static String name(Entity e) {
        return e == null ? "none" : e.getClass().getSimpleName() + "#" + e.getEntityId();
    }

    private static void msg(ICommandSender s, String text) {
        s.addChatMessage(new ChatComponentText(text));
    }
}
