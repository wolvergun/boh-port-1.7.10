package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;

public final class KeyframeStack {

    final List<Keyframe> x;
    final List<Keyframe> y;
    final List<Keyframe> z;

    public KeyframeStack() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public KeyframeStack(List<Keyframe> x, List<Keyframe> y, List<Keyframe> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getLastKeyframeTime() {
        return Math.max(sum(x), Math.max(sum(y), sum(z)));
    }

    private static double sum(List<Keyframe> frames) {
        double t = 0;
        for (Keyframe k : frames) t += k.length;
        return t;
    }
}
