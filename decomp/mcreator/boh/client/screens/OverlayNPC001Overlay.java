package net.mcreator.boh.client.screens;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.procedures.OverlayNPC001DisplayOverlayIngameProcedure;
import net.mcreator.boh.procedures.ReturnNPC10Procedure;
import net.mcreator.boh.procedures.ReturnNPC11Procedure;
import net.mcreator.boh.procedures.ReturnNPC12Procedure;
import net.mcreator.boh.procedures.ReturnNPC13Procedure;
import net.mcreator.boh.procedures.ReturnNPC14Procedure;
import net.mcreator.boh.procedures.ReturnNPC15Procedure;
import net.mcreator.boh.procedures.ReturnNPC16Procedure;
import net.mcreator.boh.procedures.ReturnNPC17Procedure;
import net.mcreator.boh.procedures.ReturnNPC1Procedure;
import net.mcreator.boh.procedures.ReturnNPC2Procedure;
import net.mcreator.boh.procedures.ReturnNPC3Procedure;
import net.mcreator.boh.procedures.ReturnNPC4Procedure;
import net.mcreator.boh.procedures.ReturnNPC5Procedure;
import net.mcreator.boh.procedures.ReturnNPC6Procedure;
import net.mcreator.boh.procedures.ReturnNPC7Procedure;
import net.mcreator.boh.procedures.ReturnNPC8Procedure;
import net.mcreator.boh.procedures.ReturnNPC9Procedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent.Pre;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(Dist.CLIENT)
public class OverlayNPC001Overlay {
   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void eventHandler(Pre event) {
      int w = event.getWindow().getGuiScaledWidth();
      int h = event.getWindow().getGuiScaledHeight();
      Level world = null;
      double x = 0.0;
      double y = 0.0;
      double z = 0.0;
      Player entity = Minecraft.getInstance().player;
      if (entity != null) {
         world = entity.level();
         x = entity.getX();
         y = entity.getY();
         z = entity.getZ();
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (OverlayNPC001DisplayOverlayIngameProcedure.execute(entity)) {
         event.getGuiGraphics().blit(new ResourceLocation("boh:textures/screens/npc_000_base_overlay.png"), 0, 0, 0.0F, 0.0F, w, h, w, h);
         if (ReturnNPC1Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -465, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC2Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -461, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC3Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -457, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC4Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -453, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC5Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -449, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC6Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -445, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC7Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -441, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC8Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -437, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC9Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -433, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC10Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -429, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC11Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -425, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC12Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -425, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC13Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -421, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC14Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -481, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC15Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -349, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC16Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -473, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }

         if (ReturnNPC17Procedure.execute(entity)) {
            event.getGuiGraphics()
               .blit(new ResourceLocation("boh:textures/screens/strip_npc_000.png"), w / 2 + -469, h / 2 + -26, 0.0F, 0.0F, 1028, 64, 1028, 64);
         }
      }

      RenderSystem.depthMask(true);
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
