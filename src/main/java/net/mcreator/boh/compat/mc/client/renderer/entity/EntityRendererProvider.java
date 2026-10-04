package net.mcreator.boh.compat.mc.client.renderer.entity;

@FunctionalInterface
public interface EntityRendererProvider<T> {
    Object create(Context var1);
}
