package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ThrownItemRenderer extends Render {
    public ThrownItemRenderer(Context context) {
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
        if (entity instanceof ItemSupplier) {
            ItemStack s = ((ItemSupplier)entity).getItem();
            if (s != null && s.getItem() != null) {
                IIcon icon = s.getItem().getIconIndex(s);
                if (icon != null) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)x, (float)y, (float)z);
                    GL11.glEnable(32826);
                    GL11.glScalef(0.5F, 0.5F, 0.5F);
                    this.bindEntityTexture(entity);
                    GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                    Tessellator t = Tessellator.instance;
                    t.startDrawingQuads();
                    t.setNormal(0.0F, 1.0F, 0.0F);
                    t.addVertexWithUV(-0.5, -0.25, 0.0, icon.getMinU(), icon.getMaxV());
                    t.addVertexWithUV(0.5, -0.25, 0.0, icon.getMaxU(), icon.getMaxV());
                    t.addVertexWithUV(0.5, 0.75, 0.0, icon.getMaxU(), icon.getMinV());
                    t.addVertexWithUV(-0.5, 0.75, 0.0, icon.getMinU(), icon.getMinV());
                    t.draw();
                    GL11.glDisable(32826);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.locationItemsTexture;
    }
}
