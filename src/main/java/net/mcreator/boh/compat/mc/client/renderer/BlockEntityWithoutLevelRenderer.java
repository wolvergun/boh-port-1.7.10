package net.mcreator.boh.compat.mc.client.renderer;

import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;

public class BlockEntityWithoutLevelRenderer {
    public BlockEntityWithoutLevelRenderer() {
    }

    public BlockEntityWithoutLevelRenderer(Object dispatcher, Object modelSet) {
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
    }

    public void onResourceManagerReload(Object manager) {
    }
}
