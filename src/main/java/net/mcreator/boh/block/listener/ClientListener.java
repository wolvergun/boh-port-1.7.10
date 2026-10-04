package net.mcreator.boh.block.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.block.renderer.JarOWispTileRenderer;
import net.mcreator.boh.block.renderer.PokerNightTileRenderer;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.client.event.RegisterRenderers;
import net.mcreator.boh.init.BohModBlockEntities;

public class ClientListener {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerRenderers(RegisterRenderers event) {
        M.registerBlockEntityRenderer(event, BohModBlockEntities.JAR_O_WISP.get(), context -> new JarOWispTileRenderer());
        M.registerBlockEntityRenderer(event, BohModBlockEntities.POKER_NIGHT.get(), context -> new PokerNightTileRenderer());
    }
}
