package net.mcreator.boh.compat.mc.client.renderer.entity;

/** 1.20 EntityRendererProvider: Context -> renderer. */
@FunctionalInterface
public interface EntityRendererProvider<T> {

    Object create(Context context);
}
