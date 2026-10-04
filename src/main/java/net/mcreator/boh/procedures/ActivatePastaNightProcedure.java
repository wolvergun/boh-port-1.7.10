package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.HypnoEntity;
import net.mcreator.boh.entity.MXEntity;
import net.mcreator.boh.entity.SonicExeEntity;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ActivatePastaNightProcedure {
    @SubscribeEvent
    public void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(
            event,
            M.level(M.getEntity(event)),
            M.getX(M.getEntity(event)),
            M.getY(M.getEntity(event)),
            M.getZ(M.getEntity(event)),
            M.getOriginalTarget(event),
            M.getEntity(event)
        );
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && sourceentity instanceof HypnoEntity
            && !M.isEmpty(M.getEntitiesOfClass(world, MXEntity.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true))
            && !M.isEmpty(M.getEntitiesOfClass(world, SonicExeEntity.class, AABB.ofSize(new Vec3(x, y, z), 10.0, 10.0, 10.0), e -> true))
            && !(
                entity instanceof EntityPlayerMP _plr3
                    && M.level(_plr3) instanceof WorldServer
                    && M.isDone(
                        M.getOrStartProgress(
                            M.getAdvancements(_plr3), M.getAdvancement(M.getAdvancements(M.server(_plr3)), new ResourceLocation("boh:pasta_night"))
                        )
                    )
            )) {
            if (entity instanceof EntityPlayerMP _player) {
                Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:pasta_night"));
                AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                if (!M.isDone(_ap)) {
                    for (String criteria : M.getRemainingCriteria(_ap)) {
                        M.award(M.getAdvancements(_player), _adv, criteria);
                    }
                }
            }

            if (!M.isClientSide(M.level(sourceentity))) {
                M.discard(sourceentity);
            }

            if (entity instanceof EntityPlayer _playerx) {
                ItemStack _setstack = M.copy(M.new_ItemStack(BohModBlocks.POKER_NIGHT.get()));
                M.setCount(_setstack, 1);
                ItemHandlerHelper.giveItemToPlayer(_playerx, _setstack);
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiterator instanceof MXEntity && !M.isClientSide(M.level(entityiterator))) {
                    M.discard(entityiterator);
                }
            }

            Vec3 _center_r1 = new Vec3(x, y, z);

            for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r1, _center_r1).inflate(5.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r1)))
                .toList()) {
                if (entityiteratorx instanceof SonicExeEntity && !M.isClientSide(M.level(entityiteratorx))) {
                    M.discard(entityiteratorx);
                }
            }
        }
    }
}
