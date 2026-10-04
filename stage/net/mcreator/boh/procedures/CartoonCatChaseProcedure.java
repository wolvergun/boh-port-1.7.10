package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.CartoonCatEntity;
import net.mcreator.boh.network.BohModVariables;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class CartoonCatChaseProcedure {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.getX(M.player(event)), M.getY(M.player(event)), M.getZ(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (((BohModVariables.PlayerVariables) M.getCapability(entity, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new BohModVariables.PlayerVariables())).chase_cartooncat) {
                M.putDouble(M.getPersistentData(entity), "timer_chase_exe", M.getDouble(M.getPersistentData(entity), "timer_chase_exe") + 1.0);
                if (!M.getBoolean(M.getPersistentData(entity), "loop") && !M.isEmpty(M.getEntitiesOfClass(world, CartoonCatEntity.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true))) {
                    M.putBoolean(M.getPersistentData(entity), "loop", true);
                    if (M.isClientSide(world) && world instanceof World _level) {
                        if (!M.isClientSide(_level)) {
                            M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:cartooncat_chase")), SoundSource.MUSIC, 0.5F, 1.0F);
                        } else {
                            M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:cartooncat_chase")), SoundSource.MUSIC, 0.5F, 1.0F, false);
                        }
                    }
                }
                if (M.getBoolean(M.getPersistentData(entity), "loop") && M.getDouble(M.getPersistentData(entity), "timer_chase_exe") == 1300.0) {
                    M.putDouble(M.getPersistentData(entity), "timer_chase_exe", 0.0);
                    M.putBoolean(M.getPersistentData(entity), "loop", false);
                }
            }
            if (M.isEmpty(M.getEntitiesOfClass(world, CartoonCatEntity.class, AABB.ofSize(new Vec3(x, y, z), 55.0, 55.0, 55.0), e -> true))) {
                boolean _setval = false;
                M.getCapability(entity, BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.chase_cartooncat = _setval;
                    capability.syncPlayerVariables(entity);
                });
                if (world instanceof WorldServer _level) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/stopsound @a music boh:cartooncat_chase");
                }
            }
        }
    }
}
