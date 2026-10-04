package net.mcreator.boh.compat.mc.client.gui.screens.inventory;

import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.components.Button;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class AbstractContainerScreen<T extends AbstractContainerMenu> extends GuiContainer {
    protected final T menu;
    protected final InventoryPlayer playerInventory;
    protected final Component title;
    protected int imageWidth = 176;
    protected int imageHeight = 166;
    protected int leftPos;
    protected int topPos;
    protected int titleLabelX = 8;
    protected int titleLabelY = 6;
    protected int inventoryLabelX = 8;
    protected int inventoryLabelY;
    protected FontRenderer font;
    protected Minecraft minecraft;
    private final GuiGraphics graphics = new GuiGraphics();

    public AbstractContainerScreen(T menu, InventoryPlayer inv, Component title) {
        super(menu);
        this.menu = menu;
        this.playerInventory = inv;
        this.title = title;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public T getMenu() {
        return this.menu;
    }

    public Component getTitle() {
        return this.title;
    }

    protected void init() {
    }

    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    protected abstract void renderBg(GuiGraphics var1, float var2, int var3, int var4);

    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    public void renderBackground(GuiGraphics g) {
        this.drawDefaultBackground();
    }

    public void renderTooltip(GuiGraphics g, int mouseX, int mouseY) {
    }

    public boolean keyPressed(int key, int scanCode, int modifiers) {
        int lwjgl = key == 256 ? 1 : scanCode;
        super.keyTyped('\u0000', lwjgl);
        return true;
    }

    public <W extends GuiButton> W addRenderableWidget(W w) {
        w.id = this.buttonList.size();
        this.buttonList.add(w);
        return w;
    }

    public <W> W addWidget(W w) {
        if (w instanceof GuiButton) {
            this.addRenderableWidget((W)((GuiButton)w));
        }

        return w;
    }

    public void onClose() {
        if (this.mc.thePlayer != null) {
            this.mc.thePlayer.closeScreen();
        }
    }

    public void containerTick() {
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void initGui() {
        this.xSize = this.imageWidth;
        this.ySize = this.imageHeight;
        super.initGui();
        this.buttonList.clear();
        this.leftPos = this.guiLeft;
        this.topPos = this.guiTop;
        this.font = this.fontRendererObj;
        this.minecraft = this.mc;
        this.init();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        this.render(this.graphics, mouseX, mouseY, partialTick);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        this.renderBg(this.graphics, partialTick, mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.renderLabels(this.graphics, mouseX, mouseY);
    }

    protected void keyTyped(char c, int key) {
        int glfw = key == 1 ? 256 : key;
        this.keyPressed(glfw, key, 0);
    }

    protected void actionPerformed(GuiButton b) {
        if (b instanceof Button) {
            ((Button)b).onPress();
        }
    }

    public void updateScreen() {
        super.updateScreen();
        this.containerTick();
    }

    public boolean doesGuiPauseGame() {
        return this.isPauseScreen();
    }
}
