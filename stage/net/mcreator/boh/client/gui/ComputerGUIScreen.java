package net.mcreator.boh.client.gui;

import java.util.HashMap;
import net.mcreator.boh.procedures.ComputerGUILoadingReturnVisibleProcedure;
import net.mcreator.boh.procedures.ComputerGuiLoadingReturnProcedure;
import net.mcreator.boh.world.inventory.ComputerGUIMenu;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.AbstractContainerScreen;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.util.Mth;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class ComputerGUIScreen extends AbstractContainerScreen<ComputerGUIMenu> {

    private static final HashMap<String, Object> guistate = ComputerGUIMenu.guistate;

    private final World world;

    private final int x;

    private final int y;

    private final int z;

    private final EntityPlayer entity;

    private static final ResourceLocation texture = new ResourceLocation("boh:textures/screens/computer_gui.png");

    public ComputerGUIScreen(ComputerGUIMenu container, InventoryPlayer inventory, Component text) {
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
        M.renderBackground(this, guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        M.renderTooltip(this, guiGraphics, mouseX, mouseY);
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        M.blit(guiGraphics, texture, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        if (ComputerGUILoadingReturnVisibleProcedure.execute(this.world, this.x, this.y, this.z)) {
            M.blit(guiGraphics, new ResourceLocation("boh:textures/screens/computer_gui_load_notch-sheet.png"), this.leftPos + 0, this.topPos + 0, 0.0F, Mth.clamp((int) ComputerGuiLoadingReturnProcedure.execute(this.world, this.x, this.y, this.z) * 166, 0, 3652), 176, 166, 176, 3818);
        }
        RenderSystem.disableBlend();
    }

    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            M.closeContainer(M.player(this.minecraft));
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
