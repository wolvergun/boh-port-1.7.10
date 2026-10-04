package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Renders a 1.20 BlockEntityWithoutLevelRenderer item in 1.7.10: the transforms 1.7.10 applied before calling us are
 * undone to reach the frame 1.20 renders items in (hand / screen / ground), then the model's display transform for
 * the matching ItemDisplayContext is applied.
 */
public final class ItemRendererBridge implements IItemRenderer {

    private final BlockEntityWithoutLevelRenderer renderer;
    private final ResourceLocation id;

    public ItemRendererBridge(Item item, BlockEntityWithoutLevelRenderer renderer) {
        this.renderer = renderer;
        String n = Item.itemRegistry.getNameForObject(item);
        this.id = new ResourceLocation(n == null ? "boh:unknown" : n);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        PoseStack pose = new PoseStack();
        ItemDisplayContext ctx;
        int light;
        Entity holder = data.length > 1 && data[1] instanceof Entity ? (Entity) data[1] : null;
        float pt = net.mcreator.boh.geo.RenderUtils.partialTick;
        switch (type) {
            case INVENTORY:
                ctx = ItemDisplayContext.GUI;
                light = LightTexture.FULL_BRIGHT;
                pose.translate(8, 8, 100);
                pose.scale(16, -16, 16);
                break;
            case EQUIPPED:
                ctx = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                light = holder != null ? holder.getBrightnessForRender(pt) : LightTexture.FULL_BRIGHT;
                undoEquipped(pose);
                undoThirdPersonBranch(pose, stack);
                pose.translate(0.0625F, -0.4375F, -0.0625F);
                // 1.20 ItemInHandLayer.renderArmWithItem
                pose.mulPose(new Quaternionf().rotationX((float) Math.toRadians(-90)));
                pose.mulPose(new Quaternionf().rotationY((float) Math.toRadians(180)));
                pose.translate(1 / 16F, 0.125F, -0.625F);
                break;
            case EQUIPPED_FIRST_PERSON:
                ctx = ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
                light = holder != null ? holder.getBrightnessForRender(pt) : LightTexture.FULL_BRIGHT;
                undoEquipped(pose);
                pose.scale(2.5F, 2.5F, 2.5F);
                pose.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-45)));
                break;
            case ENTITY:
            default:
                ctx = ItemDisplayContext.GROUND;
                light = holder != null ? holder.getBrightnessForRender(pt) : LightTexture.FULL_BRIGHT;
                pose.translate(0, -0.25F, 0);
                break;
        }
        String key = ctx == ItemDisplayContext.GUI ? "gui" : ctx == ItemDisplayContext.GROUND ? "ground"
            : ctx == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ? "firstperson_righthand" : "thirdperson_righthand";
        ItemTransforms.get(id, key).apply(false, pose);
        pose.translate(-0.5F, -0.5F, -0.5F);

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_CULL_FACE);
        if (type == ItemRenderType.INVENTORY) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableGUIStandardItemLighting();
        }
        BufferSource buffers = new BufferSource();
        try {
            renderer.renderByItem(stack, ctx, pose, buffers, light, OverlayTexture.NO_OVERLAY);
        } finally {
            buffers.endBatch();
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(net.minecraft.client.renderer.texture.TextureMap.locationItemsTexture);
        }
    }

    /** Inverse of ForgeHooksClient.renderEquippedItem (non-block path). */
    private static void undoEquipped(PoseStack pose) {
        pose.translate(0.9375F, 0.0625F, 0.0F);
        pose.mulPose(new Quaternionf().rotationZ((float) Math.toRadians(-335)));
        pose.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-50)));
        pose.scale(1 / 1.5F, 1 / 1.5F, 1 / 1.5F);
        pose.translate(0.0F, 0.3F, 0.0F);
    }

    /** Inverse of RenderBiped/RenderPlayer's held-item transform for handheld (full 3D) or flat items. */
    private static void undoThirdPersonBranch(PoseStack pose, ItemStack stack) {
        if (stack.getItem().isFull3D()) {
            pose.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-45)));
            pose.mulPose(new Quaternionf().rotationX((float) Math.toRadians(100)));
            pose.scale(1 / 0.625F, -1 / 0.625F, 1 / 0.625F);
            pose.translate(0.0F, -0.1875F, 0.0F);
        } else {
            pose.mulPose(new Quaternionf().rotationZ((float) Math.toRadians(-20)));
            pose.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90)));
            pose.mulPose(new Quaternionf().rotationZ((float) Math.toRadians(-60)));
            pose.scale(1 / 0.375F, 1 / 0.375F, 1 / 0.375F);
            pose.translate(-0.25F, -0.1875F, 0.1875F);
        }
    }

    static boolean isLiving(Entity e) {
        return e instanceof EntityLivingBase;
    }
}
