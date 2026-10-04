package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.init.BohModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class JumpAboveBaseplateBlockProcedure {
    @SubscribeEvent
    public void onEntityJump(LivingJumpEvent event) {
        execute(event, M.level(M.getEntity(event)), M.getX(M.getEntity(event)), M.getY(M.getEntity(event)), M.getZ(M.getEntity(event)));
    }

    public static void execute(World world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z) {
        if (M.getBlock(M.getBlockState(world, BlockPos.containing(x, y - 1.0, z))) == BohModBlocks.STUD_PART.get() && world instanceof World) {
            if (!M.isClientSide(world)) {
                M.playSound(
                    world,
                    null,
                    BlockPos.containing(x, y, z),
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:baseplate_jump")),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
                );
            } else {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:baseplate_jump")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                );
            }
        }
    }
}
