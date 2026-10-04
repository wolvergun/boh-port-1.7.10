package net.mcreator.boh.compat.world;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Scoreboard saving crashes on scores whose objective is null. Other mods create those by asking for a score of an
 * objective that doesn't exist (e.g. Fisk's Superheroes reads its FiskTag XP scores outside of a FiskTag match), so the
 * overworld's scoreboard save data is replaced by one that drops such scores before writing.
 */
public class SafeScoreboardSave extends ScoreboardSaveData {

    private static Field scoresField;
    private final Scoreboard board;

    public SafeScoreboardSave(Scoreboard board) {
        super("scoreboard");
        this.board = board;
        func_96499_a(board);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        purge(board);
        super.writeToNBT(tag);
    }

    @SuppressWarnings("rawtypes")
    public static void purge(Scoreboard sb) {
        try {
            if (scoresField == null) {
                scoresField = Scoreboard.class.getDeclaredField("field_96544_c");
                scoresField.setAccessible(true);
            }
            Map outer = (Map) scoresField.get(sb);
            for (Object inner : outer.values()) if (inner instanceof Map) ((Map) inner).remove(null);
        } catch (Exception e) {
            net.mcreator.boh.BohMod.LOGGER.debug("scoreboard purge failed", e);
        }
    }

    public static void install() {
        MinecraftForge.EVENT_BUS.register(new SafeScoreboardSave.Hook());
    }

    public static final class Hook {

        @SubscribeEvent
        public void onWorldLoad(WorldEvent.Load e) {
            if (!(e.world instanceof WorldServer) || e.world.provider.dimensionId != 0) return;
            Scoreboard sb = e.world.getScoreboard();
            if (!(sb instanceof ServerScoreboard)) return;
            SafeScoreboardSave safe = new SafeScoreboardSave(sb);
            ((ServerScoreboard) sb).func_96547_a(safe);
            e.world.mapStorage.setData("scoreboard", safe);
        }
    }
}
