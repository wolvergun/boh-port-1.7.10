package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SlenderInfluenceProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.getX(M.player(event)), M.getY(M.player(event)), M.getZ(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && entity instanceof EntityLivingBase _livEnt0
            && M.hasEffect(_livEnt0, BohModMobEffects.SLENDER_INFLUENCE_EFFECT.get())
            && M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 40.0, 40.0, 40.0), e -> true))
            && Math.random() < 0.03) {
            if (Math.random() < 0.3) {
                if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                        "/execute at @p rotated ~ 1 run spreadplayers ~ ~ 5 15 false @e[type=boh:slender_man,limit=1]"
                    );
                }
            } else if (Math.random() < 0.1
                && (
                        entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                            ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                            : 0
                    )
                    == 5
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
                    "/execute at @p rotated ~ 1 run spreadplayers ~ ~ 5 15 false @e[type=boh:slender_man,limit=1]"
                );
            }
        }
    }
}
