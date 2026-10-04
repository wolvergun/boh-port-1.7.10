package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.mcreator.boh.geo.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public final class ItemRendererBridge implements IItemRenderer {
    private final BlockEntityWithoutLevelRenderer renderer;
    private final ResourceLocation id;

    public ItemRendererBridge(Item item, BlockEntityWithoutLevelRenderer renderer) {
        this.renderer = renderer;
        String n = Item.itemRegistry.getNameForObject(item);
        this.id = new ResourceLocation(n == null ? "boh:unknown" : n);
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
    }

    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        PoseStack pose = new PoseStack();
        Entity holder = data.length > 1 && data[1] instanceof Entity ? (Entity)data[1] : null;
        float pt = RenderUtils.partialTick;
        ItemDisplayContext ctx;
        int light;
        switch (type) {
            case INVENTORY:
                ctx = ItemDisplayContext.GUI;
                light = 15728880;
                pose.translate(8.0F, 8.0F, 100.0F);
                pose.scale(16.0F, -16.0F, 16.0F);
                break;
            case EQUIPPED:
                ctx = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                light = holder != null ? holder.getBrightnessForRender(pt) : 15728880;
                undoEquipped(pose);
                undoThirdPersonBranch(pose, stack);
                pose.translate(0.0625F, -0.4375F, -0.0625F);
                pose.mulPose(new Quaternionf().rotationX((float)Math.toRadians(-90.0)));
                pose.mulPose(new Quaternionf().rotationY((float)Math.toRadians(180.0)));
                pose.translate(0.0625F, 0.125F, -0.625F);
                break;
            case EQUIPPED_FIRST_PERSON:
                ctx = ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
                light = holder != null ? holder.getBrightnessForRender(pt) : 15728880;
                undoEquipped(pose);
                pose.scale(2.5F, 2.5F, 2.5F);
                pose.mulPose(new Quaternionf().rotationY((float)Math.toRadians(-45.0)));
                break;
            case ENTITY:
            default:
                ctx = ItemDisplayContext.GROUND;
                light = holder != null ? holder.getBrightnessForRender(pt) : 15728880;
                pose.translate(0.0F, -0.25F, 0.0F);
        }

        String key = ctx == ItemDisplayContext.GUI
            ? "gui"
            : (
                ctx == ItemDisplayContext.GROUND
                    ? "ground"
                    : (ctx == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ? "firstperson_righthand" : "thirdperson_righthand")
            );
        ItemTransforms.get(this.id, key).apply(false, pose);
        pose.translate(-0.5F, -0.5F, -0.5F);
        GL11.glPushMatrix();
        GL11.glPushAttrib(24896);
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        if (type == ItemRenderType.INVENTORY) {
            GL11.glEnable(2929);
            RenderHelper.enableGUIStandardItemLighting();
        }

        BufferSource buffers = new BufferSource();

        try {
            this.renderer.renderByItem(stack, ctx, pose, buffers, light, OverlayTexture.NO_OVERLAY);
        } finally {
            buffers.endBatch();
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
        }
    }

    private static void undoEquipped(PoseStack pose) {
        pose.translate(0.9375F, 0.0625F, 0.0F);
        pose.mulPose(new Quaternionf().rotationZ((float)Math.toRadians(-335.0)));
        pose.mulPose(new Quaternionf().rotationY((float)Math.toRadians(-50.0)));
        pose.scale(0.6666667F, 0.6666667F, 0.6666667F);
        pose.translate(0.0F, 0.3F, 0.0F);
    }

    private static void undoThirdPersonBranch(PoseStack pose, ItemStack stack) {
        if (stack.getItem().isFull3D()) {
            pose.mulPose(new Quaternionf().rotationY((float)Math.toRadians(-45.0)));
            pose.mulPose(new Quaternionf().rotationX((float)Math.toRadians(100.0)));
            pose.scale(1.6F, -1.6F, 1.6F);
            pose.translate(0.0F, -0.1875F, 0.0F);
        } else {
            pose.mulPose(new Quaternionf().rotationZ((float)Math.toRadians(-20.0)));
            pose.mulPose(new Quaternionf().rotationX((float)Math.toRadians(90.0)));
            pose.mulPose(new Quaternionf().rotationZ((float)Math.toRadians(-60.0)));
            pose.scale(2.6666667F, 2.6666667F, 2.6666667F);
            pose.translate(-0.25F, -0.1875F, 0.1875F);
        }
    }

    static boolean isLiving(Entity e) {
        return e instanceof EntityLivingBase;
    }
}
