package net.mcreator.boh.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import net.mcreator.boh.procedures.ComputerGUILoadingReturnVisibleProcedure;
import net.mcreator.boh.procedures.ComputerGuiLoadingReturnProcedure;
import net.mcreator.boh.world.inventory.ComputerGUIMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ComputerGUIScreen extends AbstractContainerScreen<ComputerGUIMenu> {
   private static final HashMap<String, Object> guistate = ComputerGUIMenu.guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   private static final ResourceLocation texture = new ResourceLocation("boh:textures/screens/computer_gui.png");

   public ComputerGUIScreen(ComputerGUIMenu container, Inventory inventory, Component text) {
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
      guiGraphics.blit(texture, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
      if (ComputerGUILoadingReturnVisibleProcedure.execute(this.world, this.x, this.y, this.z)) {
         guiGraphics.blit(
            new ResourceLocation("boh:textures/screens/computer_gui_load_notch-sheet.png"),
            this.leftPos + 0,
            this.topPos + 0,
            0.0F,
            Mth.clamp((int)ComputerGuiLoadingReturnProcedure.execute(this.world, this.x, this.y, this.z) * 166, 0, 3652),
            176,
            166,
            176,
            3818
         );
      }

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
   }

   public void init() {
      super.init();
   }
}
