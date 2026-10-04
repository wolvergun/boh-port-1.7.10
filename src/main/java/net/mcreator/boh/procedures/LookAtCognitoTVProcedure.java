package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.ClipBlock;
import net.mcreator.boh.compat.mc.world.level.ClipContext;
import net.mcreator.boh.compat.mc.world.level.ClipFluid;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class LookAtCognitoTVProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(M.player(event)), M.player(event));
        }
    }

    public static void execute(World world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity) {
        if (entity != null
            && BohModBlocks.ANALOG_TV_BOILED.get()
                == M.getBlock(
                    M.getBlockState(
                        world,
                        new BlockPos(
                            M.getX(
                                M.getBlockPos(
                                    M.clip(
                                        M.level(entity),
                                        new ClipContext(
                                            M.getEyePosition(entity, 1.0F),
                                            M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(32.0)),
                                            ClipBlock.OUTLINE,
                                            ClipFluid.NONE,
                                            entity
                                        )
                                    )
                                )
                            ),
                            M.getY(
                                M.getBlockPos(
                                    M.clip(
                                        M.level(entity),
                                        new ClipContext(
                                            M.getEyePosition(entity, 1.0F),
                                            M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(32.0)),
                                            ClipBlock.OUTLINE,
                                            ClipFluid.NONE,
                                            entity
                                        )
                                    )
                                )
                            ),
                            M.getZ(
                                M.getBlockPos(
                                    M.clip(
                                        M.level(entity),
                                        new ClipContext(
                                            M.getEyePosition(entity, 1.0F),
                                            M.getEyePosition(entity, 1.0F).add(M.getViewVector(entity, 1.0F).scale(32.0)),
                                            ClipBlock.OUTLINE,
                                            ClipFluid.NONE,
                                            entity
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            && entity instanceof EntityLivingBase _entity
            && !M.isClientSide(M.level(_entity))) {
            M.addEffect(_entity, M.new_PotionEffect(BohModMobEffects.COGNITO_HAZART.get(), 9999, 0, false, false));
        }
    }
}
