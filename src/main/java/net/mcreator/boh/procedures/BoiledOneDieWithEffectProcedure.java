package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class BoiledOneDieWithEffectProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event));
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null
            && entity instanceof EntityLivingBase _livEnt0
            && M.hasEffect(_livEnt0, BohModMobEffects.COGNITO_HAZART.get())
            && !M.isClientSide(M.level(entity))
            && M.getServer(entity) != null) {
            M.performPrefixedCommand(
                M.getCommands(M.getServer(entity)),
                new CommandSourceStack(
                    CommandSource.NULL,
                    M.position(entity),
                    M.getRotationVector(entity),
                    M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                    4,
                    M.getString(M.getName(entity)),
                    M.getDisplayName(entity),
                    M.getServer(M.level(entity)),
                    entity
                ),
                "/team leave @s[team=cognito]"
            );
        }
    }
}
