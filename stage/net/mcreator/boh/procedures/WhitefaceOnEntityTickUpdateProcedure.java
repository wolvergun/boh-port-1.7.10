package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class WhitefaceOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.5, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.5));
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute as @e[type=boh:whiteface,distance=0..1] at @s facing entity @e[type=minecraft:player,tag=whiteface,sort=nearest,limit=1] feet run tp @e[type=boh:whiteface] ^ ^ ^0.1 ~ ~");
                }
            }
            M.setDeltaMovement(entity, new Vec3(M.getLookAngle(entity).x * 0.2, M.getLookAngle(entity).y * 0.9, M.getLookAngle(entity).z * 0.2));
            M.putDouble(M.getPersistentData(entity), "loop", M.getDouble(M.getPersistentData(entity), "loop") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "loop") == 12.0) {
                if ((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:whiteface_ambience")), SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:whiteface_ambience")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "loop", 0.0);
            }
            if (BohModVariables.MapVariables.get(world).Kill_WF == 1.0 && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
        }
    }
}
