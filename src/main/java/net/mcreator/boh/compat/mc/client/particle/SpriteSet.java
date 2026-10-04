package net.mcreator.boh.compat.mc.client.particle;

import java.util.Random;
import net.minecraft.util.IIcon;

public interface SpriteSet {
    IIcon get(int var1, int var2);

    IIcon get(Random var1);
}
