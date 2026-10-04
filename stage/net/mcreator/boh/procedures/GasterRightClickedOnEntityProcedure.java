package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.GasterEntity;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class GasterRightClickedOnEntityProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.getBoolean(M.getPersistentData(entity), "rightclick")) {
                M.putBoolean(M.getPersistentData(entity), "rightclick", true);
                if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(Items.EGG));
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.addFreshEntity(_level, entityToSpawn);
                }
                if (entity instanceof GasterEntity) {
                    ((GasterEntity) entity).setAnimation("disapear");
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/stopsound @a player boh:gaster_ambience");
                }
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_disapear")), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_disapear")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
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
}
