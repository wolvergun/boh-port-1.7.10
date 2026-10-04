package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class JeffthekillerAttackProcedure {

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof JeffTheKillerEntity) {
                Entity _ent = sourceentity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                }
                Entity _ent_r42 = sourceentity;
                if (!M.isClientSide(M.level(_ent_r42)) && M.getServer(_ent_r42) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r42)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r42), M.getRotationVector(_ent_r42), M.level(_ent_r42) instanceof WorldServer ? (WorldServer) M.level(_ent_r42) : null, 4, M.getString(M.getName(_ent_r42)), M.getDisplayName(_ent_r42), M.getServer(M.level(_ent_r42)), _ent_r42), "/playsound minecraft:entity.player.attack.strong hostile @a");
                }
                BohMod.queueServerWork(10, () -> {
                    if (!(entity instanceof EntityLivingBase _livEnt3 && M.isBlocking(_livEnt3))) {
                        Entity _entx = sourceentity;
                        if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                        }
                        Entity _entx_r43 = sourceentity;
                        if (!M.isClientSide(M.level(_entx_r43)) && M.getServer(_entx_r43) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r43)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r43), M.getRotationVector(_entx_r43), M.level(_entx_r43) instanceof WorldServer ? (WorldServer) M.level(_entx_r43) : null, 4, M.getString(M.getName(_entx_r43)), M.getDisplayName(_entx_r43), M.getServer(M.level(_entx_r43)), _entx_r43), "/playsound minecraft:entity.player.attack.strong hostile @a");
                        }
                        M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 6.0F);
                        if (Math.random() < 0.6) {
                            _entx_r43 = entity;
                            if (!M.isClientSide(M.level(_entx_r43)) && M.getServer(_entx_r43) != null) {
                                M.performPrefixedCommand(M.getCommands(M.getServer(_entx_r43)), new CommandSourceStack(CommandSource.NULL, M.position(_entx_r43), M.getRotationVector(_entx_r43), M.level(_entx_r43) instanceof WorldServer ? (WorldServer) M.level(_entx_r43) : null, 4, M.getString(M.getName(_entx_r43)), M.getDisplayName(_entx_r43), M.getServer(M.level(_entx_r43)), _entx_r43), "effect give @s kurolib:bleeding 1 10");
                            }
                        }
                    }
                });
            }
        }
    }
}
