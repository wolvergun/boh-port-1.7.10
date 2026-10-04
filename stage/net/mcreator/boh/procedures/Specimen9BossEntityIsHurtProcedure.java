package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.Specimen9BossEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class Specimen9BossEntityIsHurtProcedure {

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)), M.getSource(event), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, damagesource, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
        if (damagesource != null && entity != null && sourceentity != null) {
            if (!M.isClientSide(world) && entity instanceof Specimen9BossEntity) {
                if (sourceentity instanceof EntityPlayer && entity instanceof Specimen9BossEntity) {
                    ((Specimen9BossEntity) entity).setAnimation("hurt");
                }
                if (!M.isShiftKeyDown(entity) && M.is(damagesource, DamageTypes.FIREBALL) && !M.isInvulnerable(entity)) {
                    M.setShiftKeyDown(entity, true);
                    Entity _ent = entity;
                    M.teleportTo(_ent, x, y - 6.0, z);
                    if (_ent instanceof EntityPlayerMP _serverPlayer) {
                        M.teleport(M.connection(_serverPlayer), x, y - 6.0, z, M.getYRot(_ent), M.getXRot(_ent));
                    }
                    BohMod.queueServerWork(100, () -> {
                        Entity _entx = entity;
                        M.teleportTo(_entx, x, y + 0.0, z);
                        if (_entx instanceof EntityPlayerMP _serverPlayerx) {
                            M.teleport(M.connection(_serverPlayerx), x, y + 0.0, z, M.getYRot(_entx), M.getXRot(_entx));
                        }
                        M.setShiftKeyDown(entity, false);
                    });
                }
            }
        }
    }
}
