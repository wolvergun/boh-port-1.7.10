package net.mcreator.boh.compat.command;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public final class CommandContext {
    public final World world;
    public final Entity executor;
    public final Vec3 pos;
    public final float yaw;
    public final float pitch;
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
        return new CommandContext(this.world, e, this.pos, this.yaw, this.pitch, this.eyes);
    }

    public CommandContext at(Entity e) {
        return new CommandContext(
            e.worldObj,
            this.executor,
            new Vec3(e.posX, e.boundingBox.minY, e.posZ),
            e.rotationYaw,
            e.rotationPitch,
            this.eyes
        );
    }

    public CommandContext positioned(Vec3 p) {
        return new CommandContext(this.world, this.executor, p, this.yaw, this.pitch, false);
    }

    public CommandContext rotated(float y, float p) {
        return new CommandContext(this.world, this.executor, this.pos, y, p, this.eyes);
    }

    public CommandContext anchored(boolean eyes) {
        return new CommandContext(this.world, this.executor, this.pos, this.yaw, this.pitch, eyes);
    }

    public Vec3 anchorPos() {
        return this.eyes && this.executor != null ? new Vec3(this.pos.x, this.pos.y + M.getEyeHeight(this.executor), this.pos.z) : this.pos;
    }
}
