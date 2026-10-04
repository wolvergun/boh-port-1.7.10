package net.mcreator.boh.block.display;

import java.util.function.Consumer;
import net.mcreator.boh.block.renderer.JarOWispDisplayItemRenderer;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.item.BohBlockItem;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.RawAnimation;
import net.mcreator.boh.geo.AnimatableManager.ControllerRegistrar;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.GeckoLibUtil;

public class JarOWispDisplayItem extends BohBlockItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public JarOWispDisplayItem(Block block, Properties settings) {
        super(block, settings);
    }

    private PlayState predicate(AnimationState event) {
        event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
        return PlayState.CONTINUE;
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {

            private final BlockEntityWithoutLevelRenderer renderer = new JarOWispDisplayItemRenderer();

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    public void registerControllers(ControllerRegistrar data) {
        data.add(new AnimationController[] { new AnimationController(this, "controller", 0, this::predicate) });
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
