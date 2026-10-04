package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.VitaMimicEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class VitaMimicAttackProcedure {

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
            if (sourceentity instanceof VitaMimicEntity) {
                Entity _ent = sourceentity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                }
                Entity _ent_r62 = sourceentity;
                if (!M.isClientSide(M.level(_ent_r62)) && M.getServer(_ent_r62) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r62)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r62), M.getRotationVector(_ent_r62), M.level(_ent_r62) instanceof WorldServer ? (WorldServer) M.level(_ent_r62) : null, 4, M.getString(M.getName(_ent_r62)), M.getDisplayName(_ent_r62), M.getServer(M.level(_ent_r62)), _ent_r62), "/playsound minecraft:entity.player.attack.strong hostile @a");
                }
                if (!(entity instanceof EntityLivingBase _livEnt3 && M.isBlocking(_livEnt3)) && Math.random() < 0.35 && entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                }
            }
        }
    }
}
