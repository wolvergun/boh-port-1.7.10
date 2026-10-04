package net.mcreator.boh.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.network.ForgivenesScreenButtonMessage;
import net.mcreator.boh.procedures.ReturnNoWFProcedure;
import net.mcreator.boh.procedures.ReturnYesWFProcedure;
import net.mcreator.boh.world.inventory.ForgivenesScreenMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ForgivenesScreenScreen extends AbstractContainerScreen<ForgivenesScreenMenu> {
   private static final HashMap<String, Object> guistate = ForgivenesScreenMenu.guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   Button button_forgive_me;
   ImageButton imagebutton_txtdocument;
   ImageButton imagebutton_txtdocument_yes;
   private static final ResourceLocation texture = new ResourceLocation("boh:textures/screens/forgivenes_screen.png");

   public ForgivenesScreenScreen(ForgivenesScreenMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.world = container.world;
      this.x = container.x;
      this.y = container.y;
      this.z = container.z;
      this.entity = container.entity;
      this.imageWidth = 442;
      this.imageHeight = 168;
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
      this.button_forgive_me = Button.builder(Component.translatable("gui.boh.forgivenes_screen.button_forgive_me"), e -> {
         if (ReturnNoWFProcedure.execute(this.world)) {
            BohMod.PACKET_HANDLER.sendToServer(new ForgivenesScreenButtonMessage(0, this.x, this.y, this.z));
            ForgivenesScreenButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
         }
      }).bounds(this.leftPos + 40, this.topPos + 35, 82, 20).build(builder -> new Button(builder) {
         public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
            this.visible = ReturnNoWFProcedure.execute(ForgivenesScreenScreen.this.world);
            super.renderWidget(guiGraphics, gx, gy, ticks);
         }
      });
      guistate.put("button:button_forgive_me", this.button_forgive_me);
      this.addRenderableWidget(this.button_forgive_me);
      this.imagebutton_txtdocument = new ImageButton(
         this.leftPos + 184,
         this.topPos + -37,
         253,
         253,
         0,
         0,
         253,
         new ResourceLocation("boh:textures/screens/atlas/imagebutton_txtdocument.png"),
         253,
         506,
         e -> {}
      ) {
         public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
            this.visible = ReturnNoWFProcedure.execute(ForgivenesScreenScreen.this.world);
            super.renderWidget(guiGraphics, gx, gy, ticks);
         }
      };
      guistate.put("button:imagebutton_txtdocument", this.imagebutton_txtdocument);
      this.addRenderableWidget(this.imagebutton_txtdocument);
      this.imagebutton_txtdocument_yes = new ImageButton(
         this.leftPos + 184,
         this.topPos + -37,
         253,
         253,
         0,
         0,
         253,
         new ResourceLocation("boh:textures/screens/atlas/imagebutton_txtdocument_yes.png"),
         253,
         506,
         e -> {}
      ) {
         public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
            this.visible = ReturnYesWFProcedure.execute(ForgivenesScreenScreen.this.world);
            super.renderWidget(guiGraphics, gx, gy, ticks);
         }
      };
      guistate.put("button:imagebutton_txtdocument_yes", this.imagebutton_txtdocument_yes);
      this.addRenderableWidget(this.imagebutton_txtdocument_yes);
   }
}
