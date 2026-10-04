package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.SotirisEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

public class SotirisOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        if (entity != null) {
            if (!(entity instanceof EntityLivingBase _livEnt0 && M.hasEffect(_livEnt0, MobEffects.MOVEMENT_SLOWDOWN))
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer
                && Math.random() < 0.01) {
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
                        "/spreadplayers ~ ~ 10 5 false @e[type=boh:sotiris,limit=1,sort=nearest]"
                    );
                }

                M.putDouble(M.getPersistentData(entity), "idle_switch", Mth.nextInt(RandomSource.create(), 0, 5));
                if (M.getDouble(M.getPersistentData(entity), "idle_switch") == 0.0) {
                    if (entity instanceof SotirisEntity) {
                        ((SotirisEntity)entity).setAnimation("idle");
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "idle_switch") == 1.0) {
                    if (entity instanceof SotirisEntity) {
                        ((SotirisEntity)entity).setAnimation("facepalm");
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "idle_switch") == 2.0) {
                    if (entity instanceof SotirisEntity) {
                        ((SotirisEntity)entity).setAnimation("grab");
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "idle_switch") == 3.0) {
                    if (entity instanceof SotirisEntity) {
                        ((SotirisEntity)entity).setAnimation("twist");
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "idle_switch") == 4.0) {
                    if (entity instanceof SotirisEntity) {
                        ((SotirisEntity)entity).setAnimation("point");
                    }
                } else if (M.getDouble(M.getPersistentData(entity), "idle_switch") == 5.0 && entity instanceof SotirisEntity) {
                    ((SotirisEntity)entity).setAnimation("balls");
                }
            }

            if (!M.getBoolean(M.getPersistentData(entity), "put_eye") && M.getBoolean(M.getPersistentData(entity), "first_kill")) {
                if (entity instanceof SotirisEntity) {
                    ((SotirisEntity)entity).setAnimation("sotiri");
                }

                if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 10, 254, false, false));
                }

                M.putDouble(M.getPersistentData(entity), "put_eye_timer", M.getDouble(M.getPersistentData(entity), "put_eye_timer") + 1.0);
                if (M.getDouble(M.getPersistentData(entity), "put_eye_timer") == 190.0) {
                    M.putBoolean(M.getPersistentData(entity), "put_eye", true);
                }
            }

            if (!M.getBoolean(M.getPersistentData(entity), "first_kill") && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_RESISTANCE, 10, 254, false, false));
            }
        }
    }
}
