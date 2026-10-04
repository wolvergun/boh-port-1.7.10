package net.mcreator.boh.geo;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Animation {
    static final String WAIT = "internal.wait";
    private final String name;
    private final double length;
    private final Animation.LoopType loopType;
    private final BoneAnimation[] boneAnimations;

    public Animation(String name, double length, Animation.LoopType loopType, BoneAnimation[] boneAnimations) {
        this.name = name;
        this.length = length;
        this.loopType = loopType;
        this.boneAnimations = boneAnimations;
    }

    static Animation generateWaitAnimation(double length) {
        return new Animation("internal.wait", length, Animation.LoopType.PLAY_ONCE, new BoneAnimation[0]);
    }

    public String name() {
        return this.name;
    }

    public double length() {
        return this.length;
    }

    public Animation.LoopType loopType() {
        return this.loopType;
    }

    public BoneAnimation[] boneAnimations() {
        return this.boneAnimations;
    }

    @FunctionalInterface
    public interface LoopType {
        Map<String, Animation.LoopType> LOOP_TYPES = new ConcurrentHashMap<>(4);
        Animation.LoopType DEFAULT = (animatable, controller, current) -> current.loopType().shouldPlayAgain(animatable, controller, current);
        Animation.LoopType PLAY_ONCE = register("play_once", register("false", (animatable, controller, current) -> false));
        Animation.LoopType HOLD_ON_LAST_FRAME = register("hold_on_last_frame", (animatable, controller, current) -> {
            controller.animationState = AnimationController.State.PAUSED;
            return true;
        });
        Animation.LoopType LOOP = register("loop", register("true", (animatable, controller, current) -> true));

        boolean shouldPlayAgain(GeoAnimatable var1, AnimationController<?> var2, Animation var3);

        static Animation.LoopType fromJson(JsonElement json) {
            if (json != null && json.isJsonPrimitive()) {
                JsonPrimitive p = json.getAsJsonPrimitive();
                if (p.isBoolean()) {
                    return p.getAsBoolean() ? LOOP : PLAY_ONCE;
                } else {
                    return p.isString() ? LOOP_TYPES.getOrDefault(p.getAsString(), PLAY_ONCE) : PLAY_ONCE;
                }
            } else {
                return PLAY_ONCE;
            }
        }

        static Animation.LoopType register(String name, Animation.LoopType type) {
            LOOP_TYPES.put(name, type);
            return type;
        }
    }
}
