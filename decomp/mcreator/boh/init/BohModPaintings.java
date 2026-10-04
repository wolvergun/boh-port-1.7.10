package net.mcreator.boh.init;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BohModPaintings {
   public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, "boh");
   public static final RegistryObject<PaintingVariant> PAINTING_FLOWEY = REGISTRY.register("painting_flowey", () -> new PaintingVariant(16, 16));
   public static final RegistryObject<PaintingVariant> PAINTING_SIRENHEAD = REGISTRY.register("painting_sirenhead", () -> new PaintingVariant(16, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_LONGHORSE = REGISTRY.register("painting_longhorse", () -> new PaintingVariant(16, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_MOGEKO = REGISTRY.register("painting_mogeko", () -> new PaintingVariant(16, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_SQUIDWARD = REGISTRY.register("painting_squidward", () -> new PaintingVariant(16, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_1X = REGISTRY.register("painting_1x", () -> new PaintingVariant(32, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_GAIYGAS = REGISTRY.register("painting_gaiygas", () -> new PaintingVariant(32, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_DELTARUNE = REGISTRY.register("painting_deltarune", () -> new PaintingVariant(32, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_OFF = REGISTRY.register("painting_off", () -> new PaintingVariant(32, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_WEEGEE = REGISTRY.register("painting_weegee", () -> new PaintingVariant(32, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_PYRAMIDHEAD = REGISTRY.register("painting_pyramidhead", () -> new PaintingVariant(48, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_ENTITY = REGISTRY.register("painting_entity", () -> new PaintingVariant(48, 32));
   public static final RegistryObject<PaintingVariant> PAINTING_YUMMENIKKI = REGISTRY.register("painting_yummenikki", () -> new PaintingVariant(64, 48));
   public static final RegistryObject<PaintingVariant> PAINTING_MR_RACOON = REGISTRY.register("painting_mr_racoon", () -> new PaintingVariant(16, 32));
}
