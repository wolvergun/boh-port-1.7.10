package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.entity.SawRunnerEntity;
import net.mcreator.boh.entity.SeedEaterEntity;
import net.mcreator.boh.entity.SpringtrapEntity;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class DisableShieldProcedure {

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
        if (entity != null) {
            if (entity instanceof SawRunnerEntity && entity instanceof PyramidHeadEntity && entity instanceof WhitefaceEntity && entity instanceof SpringtrapEntity && entity instanceof SeedEaterEntity && entity instanceof PatrickBatemanEntity) {
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/kill @e[type=item,nbt={Item:{id:\"minecraft:wooden_axe\"}}]");
                }
            }
        }
    }
}
