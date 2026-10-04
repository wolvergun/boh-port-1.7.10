package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class JaneTheKillerThisEntityKillsAnotherOneProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (entity instanceof JeffTheKillerEntity) {
                M.kill(sourceentity);
                if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.HATREDS_END.get()));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }
            }

            BohMod.queueServerWork(
                2,
                () -> {
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
                            "/kill @e[limit=2,distance=0..2,type=item,nbt={Item:{id:\"boh:killer_knife\"}}]"
                        );
                    }
                }
            );
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(8.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if ((
                        !(entityiterator instanceof EntityPlayerMP _plr5)
                            || !(M.level(_plr5) instanceof WorldServer)
                            || !M.isDone(
                                M.getOrStartProgress(
                                    M.getAdvancements(_plr5),
                                    M.getAdvancement(M.getAdvancements(M.server(_plr5)), new ResourceLocation("boh:requiemfor_two_killers"))
                                )
                            )
                    )
                    && entityiterator instanceof EntityPlayerMP _player) {
                    Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:requiemfor_two_killers"));
                    AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                    if (!M.isDone(_ap)) {
                        for (String criteria : M.getRemainingCriteria(_ap)) {
                            M.award(M.getAdvancements(_player), _adv, criteria);
                        }
                    }
                }
            }
        }
    }
}
