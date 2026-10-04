package net.mcreator.boh.compat.command;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/** Execution state of a 1.20-syntax command: who (@s), where, which way and which anchor. */
public final class CommandContext {

    public final World world;
    public final Entity executor;
    public final Vec3 pos;
    public final float yaw, pitch;
    public final boolean eyes;
    public final MinecraftServer server;

    public CommandContext(World world, Entity executor, Vec3 pos, float yaw, float pitch, boolean eyes) {
        this.world = world;
        this.executor = executor;
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.eyes = eyes;
        this.server = MinecraftServer.getServer();
    }

    public CommandContext as(Entity e) {
        return new CommandContext(world, e, pos, yaw, pitch, eyes);
    }

    public CommandContext at(Entity e) {
        return new CommandContext(e.worldObj, executor, new Vec3(e.posX, e.boundingBox.minY, e.posZ), e.rotationYaw, e.rotationPitch, eyes);
    }

    public CommandContext positioned(Vec3 p) {
        return new CommandContext(world, executor, p, yaw, pitch, false);
    }

    public CommandContext rotated(float y, float p) {
        return new CommandContext(world, executor, pos, y, p, eyes);
    }

    public CommandContext anchored(boolean eyes) {
        return new CommandContext(world, executor, pos, yaw, pitch, eyes);
    }

    /** Anchor point: feet position, or the executor's eye height when anchored eyes. */
    public Vec3 anchorPos() {
        if (!eyes || executor == null) return pos;
        return new Vec3(pos.x, pos.y + net.mcreator.boh.compat.M.getEyeHeight(executor), pos.z);
    }
}
