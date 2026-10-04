package net.mcreator.boh.block.listener;

import net.mcreator.boh.block.renderer.JarOWispTileRenderer;
import net.mcreator.boh.block.renderer.PokerNightTileRenderer;
import net.mcreator.boh.init.BohModBlockEntities;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.forge.client.event.RegisterRenderers;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class ClientListener {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerRenderers(RegisterRenderers event) {
        M.registerBlockEntityRenderer(event, (BlockEntityType) BohModBlockEntities.JAR_O_WISP.get(), context -> new JarOWispTileRenderer());
        M.registerBlockEntityRenderer(event, (BlockEntityType) BohModBlockEntities.POKER_NIGHT.get(), context -> new PokerNightTileRenderer());
    }
}
