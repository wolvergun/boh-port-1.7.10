package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.EntityWellEntity;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class DestroyTapeProcedure {

    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)));
    }

    public static void execute(World world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z) {
        boolean found = false;
        double sx = 0.0;
        double sy = 0.0;
        double sz = 0.0;
        Vec3 _center = new Vec3(x, y, z);
        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(1.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
            if (M.getItem((entityiterator instanceof EntityItem _itemEnt ? M.getItem(_itemEnt) : M.EMPTY)) == BohModItems.VHS_TAPE.get() && !M.isEmpty(M.getEntitiesOfClass(world, EntityWellEntity.class, AABB.ofSize(new Vec3(x, y, z), 3.0, 3.0, 3.0), e -> true))) {
                if (!M.isClientSide(M.level(entityiterator))) {
                    M.discard(entityiterator);
                }
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/kill @e[type=boh:sadako]");
                }
                BohMod.queueServerWork(40, () -> {
                    if (world instanceof WorldServer _levelx) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_levelx)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _levelx, 4, "", Component.literal(""), M.getServer(_levelx), null)), "/effect clear @a boh:sadako_effect");
                    }
                });
            }
        }
    }
}
