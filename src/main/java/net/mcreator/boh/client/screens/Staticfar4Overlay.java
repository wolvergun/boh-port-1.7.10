package net.mcreator.boh.client.screens;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.client.event.Pre;
import net.mcreator.boh.compat.mc.client.renderer.GameRenderer;
import net.mcreator.boh.compat.mojang.blaze3d.platform.DestFactor;
import net.mcreator.boh.compat.mojang.blaze3d.platform.SourceFactor;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.procedures.Staticfar4DisplayOverlayIngameProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Staticfar4Overlay {
    @SubscribeEvent(
        priority = EventPriority.NORMAL
    )
    public void eventHandler(Pre event) {
        int w = M.getGuiScaledWidth(M.getWindow(event));
        int h = M.getGuiScaledHeight(M.getWindow(event));
        World world = null;
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        EntityPlayer entity = M.player(Minecraft.getMinecraft());
        if (entity != null) {
            world = M.level(entity);
            x = M.getX(entity);
            y = M.getY(entity);
            z = M.getZ(entity);
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (Staticfar4DisplayOverlayIngameProcedure.execute(entity)) {
            M.blit(M.getGuiGraphics(event), new ResourceLocation("boh:textures/screens/static_far_4.png"), 0, 0, 0.0F, 0.0F, w, h, w, h);
        }

        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
