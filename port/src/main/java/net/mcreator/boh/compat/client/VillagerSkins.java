package net.mcreator.boh.compat.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.common.registry.VillagerRegistry;

/**
 * Villager skins for the mod's professions. 1.20 profession textures are only the clothes, drawn over the villager
 * body; 1.7.10 wants one full texture, so the overlay is composited onto the game's own villager.png at load time
 * (same 64x64 UV layout) instead of shipping Mojang's texture.
 */
public final class VillagerSkins {

    private static final ResourceLocation BASE = new ResourceLocation("textures/entity/villager/villager.png");

    private VillagerSkins() {}

    public static void register() {
        for (VillagerProfession p : Registration.PROFESSIONS) {
            String path = p.name().substring(p.name().indexOf(':') + 1);
            ResourceLocation overlay = new ResourceLocation("boh", "textures/entity/villager/profession/" + path + ".png");
            ResourceLocation skin = new ResourceLocation("boh", "dynamic/villager_" + path);
            Minecraft.getMinecraft().getTextureManager().loadTexture(skin, new Composite(overlay));
            VillagerRegistry.instance().registerVillagerSkin(p.legacyId(), skin);
        }
    }

    private static final class Composite extends AbstractTexture {

        private final ResourceLocation overlay;

        Composite(ResourceLocation overlay) {
            this.overlay = overlay;
        }

        @Override
        public void loadTexture(IResourceManager rm) throws IOException {
            BufferedImage base = read(rm, BASE);
            BufferedImage over;
            try {
                over = read(rm, overlay);
            } catch (IOException e) {
                BohMod.LOGGER.warn("Missing villager profession texture {}", overlay);
                over = null;
            }
            BufferedImage out = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = out.createGraphics();
            g.drawImage(base, 0, 0, null);
            if (over != null) g.drawImage(over, 0, 0, base.getWidth(), base.getHeight(), null);
            g.dispose();
            deleteGlTexture();
            TextureUtil.uploadTextureImageAllocate(getGlTextureId(), out, false, false);
        }

        private static BufferedImage read(IResourceManager rm, ResourceLocation loc) throws IOException {
            try (InputStream in = rm.getResource(loc).getInputStream()) {
                return ImageIO.read(in);
            }
        }
    }
}
