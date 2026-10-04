package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class JasonVoorheesOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.002 && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityLivingBase) {
                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 120, 0, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 120, 1, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 120, 254, false, false));
                }

                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jason_kikiki")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:jason_kikiki")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                Entity _ent = entity instanceof EntityLiving _mobEntx ? M.getTarget(_mobEntx) : null;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(
                        M.getCommands(M.getServer(_ent)),
                        new CommandSourceStack(
                            CommandSource.NULL,
                            M.position(_ent),
                            M.getRotationVector(_ent),
                            M.level(_ent) instanceof WorldServer ? (WorldServer)M.level(_ent) : null,
                            4,
                            M.getString(M.getName(_ent)),
                            M.getDisplayName(_ent),
                            M.getServer(M.level(_ent)),
                            _ent
                        ),
                        "/spreadplayers ~ ~ 1 30 false @e[type=boh:jason_voorhees,limit=1,sort=nearest]"
                    );
                }
            }

            if (M.isInWater(entity)) {
                if (Math.random() < 0.7 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                        "/particle minecraft:bubble_pop ~ ~.15 ~ 0.2 .5 0.2 0 20 force"
                    );
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.INVISIBILITY, 120, 0, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SPEED, 120, 4, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.CONDUIT_POWER, 120, 0, false, false));
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.DOLPHINS_GRACE, 120, 0, false, false));
                }
            }

            if (entity instanceof EntityLiving _mob
                && M.isAggressive(_mob)
                && !(entity instanceof EntityLivingBase _livEnt15 && M.hasEffect(_livEnt15, MobEffects.SATURATION))
                && Math.random() < 0.02
                && Math.random() < 0.02
                && entity instanceof EntityLivingBase _entity
                && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.SATURATION, 30, 0, false, false));
            }
        }
    }
}
