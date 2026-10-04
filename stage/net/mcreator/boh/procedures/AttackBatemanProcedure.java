package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class AttackBatemanProcedure {

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (sourceentity instanceof PatrickBatemanEntity) {
                Entity _ent = sourceentity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                }
                Entity _ent_r3 = sourceentity;
                if (!M.isClientSide(M.level(_ent_r3)) && M.getServer(_ent_r3) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r3)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r3), M.getRotationVector(_ent_r3), M.level(_ent_r3) instanceof WorldServer ? (WorldServer) M.level(_ent_r3) : null, 4, M.getString(M.getName(_ent_r3)), M.getDisplayName(_ent_r3), M.getServer(M.level(_ent_r3)), _ent_r3), "/playsound minecraft:entity.player.attack.strong hostile @a");
                }
                if (!(entity instanceof EntityLivingBase _livEnt3 && M.isBlocking(_livEnt3)) && Math.random() < 0.45) {
                    Entity _entx = entity;
                    if (!M.isClientSide(M.level(_entx)) && M.getServer(_entx) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_entx)), new CommandSourceStack(CommandSource.NULL, M.position(_entx), M.getRotationVector(_entx), M.level(_entx) instanceof WorldServer ? (WorldServer) M.level(_entx) : null, 4, M.getString(M.getName(_entx)), M.getDisplayName(_entx), M.getServer(M.level(_entx)), _entx), "effect give @s kurolib:bleeding 2 10");
                    }
                }
            }
        }
    }
}
