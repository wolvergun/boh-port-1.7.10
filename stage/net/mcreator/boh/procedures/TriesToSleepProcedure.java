package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class TriesToSleepProcedure {

    @SubscribeEvent
    public void onPlayerInBed(PlayerSleepInBedEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getEntity(event));
    }

    public static void execute(World world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, (Potion) BohModMobEffects.COGNITO_HAZART.get())) {
                if (Math.random() < 0.9) {
                    M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("boh:lock_in")))), 50.0F);
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/team leave @s[team=cognito]");
                    }
                } else {
                    M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("boh:locked_in")))), 50.0F);
                    Entity _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/team leave @s[team=cognito]");
                    }
                }
            }
        }
    }
}
