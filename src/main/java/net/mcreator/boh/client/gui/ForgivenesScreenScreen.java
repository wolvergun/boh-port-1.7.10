package net.mcreator.boh.client.gui;

import java.util.HashMap;
import java.util.Objects;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.components.Button;
import net.mcreator.boh.compat.mc.client.gui.components.ImageButton;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.AbstractContainerScreen;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.network.ForgivenesScreenButtonMessage;
import net.mcreator.boh.procedures.ReturnNoWFProcedure;
import net.mcreator.boh.procedures.ReturnYesWFProcedure;
import net.mcreator.boh.world.inventory.ForgivenesScreenMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ForgivenesScreenScreen extends AbstractContainerScreen<ForgivenesScreenMenu> {
    private static final HashMap<String, Object> guistate = ForgivenesScreenMenu.guistate;
    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private final EntityPlayer entity;
    Button button_forgive_me;
    ImageButton imagebutton_txtdocument;
    ImageButton imagebutton_txtdocument_yes;
    private static final ResourceLocation texture = new ResourceLocation("boh:textures/screens/forgivenes_screen.png");

    public ForgivenesScreenScreen(ForgivenesScreenMenu container, InventoryPlayer inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 442;
        this.imageHeight = 168;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        M.renderBackground(this, guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        M.renderTooltip(this, guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        M.blit(guiGraphics, texture, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            M.closeContainer(M.player(this.minecraft));
            return true;
        } else {
            return super.keyPressed(key, b, c);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void init() {
        super.init();
        this.button_forgive_me = M.bounds(Button.builder(Component.translatable("gui.boh.forgivenes_screen.button_forgive_me"), e -> {
            if (ReturnNoWFProcedure.execute(this.world)) {
                M.sendToServer(BohMod.PACKET_HANDLER, new ForgivenesScreenButtonMessage(0, this.x, this.y, this.z));
                ForgivenesScreenButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
            }
        }), this.leftPos + 40, this.topPos + 35, 82, 20).build(builder -> new Button(builder) {
            {
                Objects.requireNonNull(ForgivenesScreenScreen.this);
            }

            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                this.visible = ReturnNoWFProcedure.execute(ForgivenesScreenScreen.this.world);
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        });
        guistate.put("button:button_forgive_me", this.button_forgive_me);
        M.addRenderableWidget(this, this.button_forgive_me);
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
            {
                Objects.requireNonNull(ForgivenesScreenScreen.this);
            }

            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                this.visible = ReturnNoWFProcedure.execute(ForgivenesScreenScreen.this.world);
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        };
        guistate.put("button:imagebutton_txtdocument", this.imagebutton_txtdocument);
        M.addRenderableWidget(this, this.imagebutton_txtdocument);
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
            {
                Objects.requireNonNull(ForgivenesScreenScreen.this);
            }

            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                this.visible = ReturnYesWFProcedure.execute(ForgivenesScreenScreen.this.world);
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        };
        guistate.put("button:imagebutton_txtdocument_yes", this.imagebutton_txtdocument_yes);
        M.addRenderableWidget(this, this.imagebutton_txtdocument_yes);
    }
}
