package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.entity.GasterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GasterRightClickedOnEntityProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null && !M.getBoolean(M.getPersistentData(entity), "rightclick")) {
            M.putBoolean(M.getPersistentData(entity), "rightclick", true);
            if (world instanceof WorldServer _level) {
                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.EGG));
                M.setPickUpDelay(entityToSpawn, 10);
                M.addFreshEntity(_level, entityToSpawn);
            }

            if (entity instanceof GasterEntity) {
                ((GasterEntity)entity).setAnimation("disapear");
            }

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
                    "/stopsound @a player boh:gaster_ambience"
                );
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_disapear")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_disapear")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            BohMod.queueServerWork(14, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
            });
        }
    }
}
