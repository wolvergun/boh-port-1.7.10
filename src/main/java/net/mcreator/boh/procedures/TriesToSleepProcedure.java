package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class TriesToSleepProcedure {
    @SubscribeEvent
    public void onPlayerInBed(PlayerSleepInBedEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, BohModMobEffects.COGNITO_HAZART.get())) {
            if (Math.random() < 0.9) {
                M.hurt(
                    entity,
                    M.new_DamageSource(
                        M.getHolderOrThrow(
                            M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE),
                            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("boh:lock_in"))
                        )
                    ),
                    50.0F
                );
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
                        "/team leave @s[team=cognito]"
                    );
                }
            } else {
                M.hurt(
                    entity,
                    M.new_DamageSource(
                        M.getHolderOrThrow(
                            M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE),
                            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("boh:locked_in"))
                        )
                    ),
                    50.0F
                );
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
                        "/team leave @s[team=cognito]"
                    );
                }
            }
        }
    }
}
