package net.mcreator.boh.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.network.KillscreenWFButtonMessage;
import net.mcreator.boh.world.inventory.KillscreenWFMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class KillscreenWFScreen extends AbstractContainerScreen<KillscreenWFMenu> {
   private static final HashMap<String, Object> guistate = KillscreenWFMenu.guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   Button button_empty;
   Button button_empty1;

   public KillscreenWFScreen(KillscreenWFMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.world = container.world;
      this.x = container.x;
      this.y = container.y;
      this.z = container.z;
      this.entity = container.entity;
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(guiGraphics);
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   public boolean keyPressed(int key, int b, int c) {
      if (key == 256) {
         this.minecraft.player.closeContainer();
         return true;
      } else {
         return super.keyPressed(key, b, c);
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      guiGraphics.drawString(this.font, Component.translatable("gui.boh.killscreen_wf.label_would_you_like_to_kill_white_fac"), -3, 61, -1, false);
      guiGraphics.drawString(this.font, Component.translatable("gui.boh.killscreen_wf.label_this_is_permanent"), 42, 106, -3407872, false);
   }

   public void init() {
      super.init();
      this.button_empty = Button.builder(Component.translatable("gui.boh.killscreen_wf.button_empty"), e -> {
         BohMod.PACKET_HANDLER.sendToServer(new KillscreenWFButtonMessage(0, this.x, this.y, this.z));
         KillscreenWFButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
      }).bounds(this.leftPos + 42, this.topPos + 79, 25, 20).build();
      guistate.put("button:button_empty", this.button_empty);
      this.addRenderableWidget(this.button_empty);
      this.button_empty1 = Button.builder(Component.translatable("gui.boh.killscreen_wf.button_empty1"), e -> {
         BohMod.PACKET_HANDLER.sendToServer(new KillscreenWFButtonMessage(1, this.x, this.y, this.z));
         KillscreenWFButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z);
      }).bounds(this.leftPos + 108, this.topPos + 81, 25, 20).build();
      guistate.put("button:button_empty1", this.button_empty1);
      this.addRenderableWidget(this.button_empty1);
   }
}
