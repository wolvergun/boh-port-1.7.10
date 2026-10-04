package net.mcreator.boh.compat.mc.world.item;

public interface TooltipFlag {
    TooltipFlag NORMAL = () -> false;
    TooltipFlag ADVANCED = () -> true;

    boolean isAdvanced();
}
