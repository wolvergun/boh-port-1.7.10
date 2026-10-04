package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.entity.VampireBatEntity;
import net.mcreator.boh.entity.VampireEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class DealDamageWoodItemProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && (entity instanceof VampireBatEntity || entity instanceof VampireEntity)
            && M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == Items.WOODEN_SWORD) {
            M.hurt(
                entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 20.0F
            );
        }
    }
}
