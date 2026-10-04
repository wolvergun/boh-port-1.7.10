package net.mcreator.boh.compat.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.boh.BohMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;

public class SafeScoreboardSave extends ScoreboardSaveData {
    private static Field scoresField;
    private final Scoreboard board;

    public SafeScoreboardSave(Scoreboard board) {
        super("scoreboard");
        this.board = board;
        this.func_96499_a(board);
    }

    public void writeToNBT(NBTTagCompound tag) {
        purge(this.board);
        super.writeToNBT(tag);
    }

    public static void purge(Scoreboard sb) {
        try {
            if (scoresField == null) {
                scoresField = Scoreboard.class.getDeclaredField("field_96544_c");
                scoresField.setAccessible(true);
            }

            Map outer = (Map)scoresField.get(sb);

            for (Object inner : outer.values()) {
                if (inner instanceof Map) {
                    ((Map)inner).remove(null);
                }
            }
        } catch (Exception var4) {
            BohMod.LOGGER.debug("scoreboard purge failed", var4);
        }
    }

    public static void install() {
        MinecraftForge.EVENT_BUS.register(new SafeScoreboardSave.Hook());
    }

    public static final class Hook {
        @SubscribeEvent
        public void onWorldLoad(Load e) {
            if (e.world instanceof WorldServer && e.world.provider.dimensionId == 0) {
                Scoreboard sb = e.world.getScoreboard();
                if (sb instanceof ServerScoreboard) {
                    SafeScoreboardSave safe = new SafeScoreboardSave(sb);
                    ((ServerScoreboard)sb).func_96547_a(safe);
                    e.world.mapStorage.setData("scoreboard", safe);
                }
            }
        }
    }
}
