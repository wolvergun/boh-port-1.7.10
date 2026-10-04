package net.mcreator.boh.compat.mc.client.particle;

import java.util.Random;

import net.minecraft.util.IIcon;

/** 1.20 SpriteSet: the icons of one particle type (stitched into the items atlas). */
public interface SpriteSet {

    IIcon get(int age, int lifetime);

    IIcon get(Random random);
}
