package net.mcreator.boh.compat.mc.commands;

import java.util.function.Supplier;
import net.mcreator.boh.compat.command.CommandContext;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandSourceStack {
    private final Vec3 position;
    private final Vec2 rotation;
    private final World level;
    private final int permission;
    private final String name;
    private final Component displayName;
    private final MinecraftServer server;
    private final Entity entity;
    private boolean silent;

    public CommandSourceStack(
        CommandSource source,
        Vec3 position,
        Vec2 rotation,
        World level,
        int permission,
        String name,
        Component displayName,
        MinecraftServer server,
        Entity entity
    ) {
        this.position = position;
        this.rotation = rotation;
        this.level = level;
        this.permission = permission;
        this.name = name;
        this.displayName = displayName;
        this.server = server;
        this.entity = entity;
    }

    public CommandSourceStack withSuppressedOutput() {
        this.silent = true;
        return this;
    }

    public CommandContext toContext() {
        World w = this.level != null ? this.level : (this.entity != null ? this.entity.worldObj : null);
        return new CommandContext(
            w, this.entity, this.position, this.rotation == null ? 0.0F : this.rotation.y, this.rotation == null ? 0.0F : this.rotation.x, false
        );
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Vec3 getPosition() {
        return this.position;
    }

    public Vec2 getRotation() {
        return this.rotation;
    }

    public World getLevel() {
        return this.level;
    }

    public World getUnsidedLevel() {
        return this.level;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public String getTextName() {
        return this.name;
    }

    public Component getDisplayName() {
        return this.displayName;
    }

    public boolean hasPermission(int level) {
        return this.permission >= level;
    }

    public void sendSuccess(Supplier<Component> msg, boolean broadcast) {
        if (!this.silent && this.entity instanceof EntityPlayer) {
            ((EntityPlayer)this.entity).addChatComponentMessage(msg.get().toVanilla());
        }
    }

    public void sendFailure(Component msg) {
        if (this.entity instanceof EntityPlayer) {
            ((EntityPlayer)this.entity).addChatComponentMessage(msg.toVanilla());
        }
    }
}
