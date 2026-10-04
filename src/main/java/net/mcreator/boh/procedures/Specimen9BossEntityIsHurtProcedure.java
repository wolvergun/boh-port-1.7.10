package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.entity.Specimen9BossEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class Specimen9BossEntityIsHurtProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(
                event,
                M.level(M.getEntity(event)),
                M.getX(M.getEntity(event)),
                M.getY(M.getEntity(event)),
                M.getZ(M.getEntity(event)),
                M.getSource(event),
                M.getEntity(event),
                M.getEntity(M.getSource(event))
            );
        }
    }

    public static void execute(World world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, damagesource, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
        if (damagesource != null && entity != null && sourceentity != null && !M.isClientSide(world) && entity instanceof Specimen9BossEntity) {
            if (sourceentity instanceof EntityPlayer && entity instanceof Specimen9BossEntity) {
                ((Specimen9BossEntity)entity).setAnimation("hurt");
            }

            if (!M.isShiftKeyDown(entity) && M.is(damagesource, DamageTypes.FIREBALL) && !M.isInvulnerable(entity)) {
                M.setShiftKeyDown(entity, true);
                M.teleportTo(entity, x, y - 6.0, z);
                if (entity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y - 6.0, z, M.getYRot(entity), M.getXRot(entity));
                }

                BohMod.queueServerWork(100, () -> {
                    M.teleportTo(entity, x, y + 0.0, z);
                    if (entity instanceof EntityPlayerMP _serverPlayerx) {
                        M.teleport(M.connection(_serverPlayerx), x, y + 0.0, z, M.getYRot(entity), M.getXRot(entity));
                    }

                    M.setShiftKeyDown(entity, false);
                });
            }
        }
    }
}
