package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.FlatwoodsMonsterEntity;
import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class GiveAchievementBigtOPProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(
                event,
                M.level(M.getEntity(event)),
                M.getX(M.getEntity(event)),
                M.getY(M.getEntity(event)),
                M.getZ(M.getEntity(event)),
                M.getEntity(event),
                M.getEntity(M.getSource(event))
            );
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (entity instanceof FlatwoodsMonsterEntity
                && !M.isEmpty(M.getEntitiesOfClass(world, MothmanEntity.class, AABB.ofSize(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true))) {
                if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.BIG_TOP_BURGER.get()));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }

                if (!(
                        sourceentity instanceof EntityPlayerMP _plr3
                            && M.level(_plr3) instanceof WorldServer
                            && M.isDone(
                                M.getOrStartProgress(
                                    M.getAdvancements(_plr3),
                                    M.getAdvancement(M.getAdvancements(M.server(_plr3)), new ResourceLocation("boh:big_top_burger_achievement"))
                                )
                            )
                    )
                    && sourceentity instanceof EntityPlayerMP _player) {
                    Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:big_top_burger_achievement"));
                    AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                    if (!M.isDone(_ap)) {
                        for (String criteria : M.getRemainingCriteria(_ap)) {
                            M.award(M.getAdvancements(_player), _adv, criteria);
                        }
                    }
                }
            }

            if (entity instanceof MothmanEntity
                && !M.isEmpty(M.getEntitiesOfClass(world, FlatwoodsMonsterEntity.class, AABB.ofSize(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true))) {
                if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.BIG_TOP_BURGER.get()));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }

                if (!(
                        sourceentity instanceof EntityPlayerMP _plr8
                            && M.level(_plr8) instanceof WorldServer
                            && M.isDone(
                                M.getOrStartProgress(
                                    M.getAdvancements(_plr8),
                                    M.getAdvancement(M.getAdvancements(M.server(_plr8)), new ResourceLocation("boh:big_top_burger_achievement"))
                                )
                            )
                    )
                    && sourceentity instanceof EntityPlayerMP _playerx) {
                    Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_playerx)), new ResourceLocation("boh:big_top_burger_achievement"));
                    AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_playerx), _adv);
                    if (!M.isDone(_ap)) {
                        for (String criteria : M.getRemainingCriteria(_ap)) {
                            M.award(M.getAdvancements(_playerx), _adv, criteria);
                        }
                    }
                }
            }
        }
    }
}
