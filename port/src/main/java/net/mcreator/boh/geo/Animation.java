package net.mcreator.boh.geo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/** A baked animation: length in ticks, loop behaviour and per-bone keyframes. */
public final class Animation {

    static final String WAIT = "internal.wait";

    private final String name;
    private final double length;
    private final LoopType loopType;
    private final BoneAnimation[] boneAnimations;

    public Animation(String name, double length, LoopType loopType, BoneAnimation[] boneAnimations) {
        this.name = name;
        this.length = length;
        this.loopType = loopType;
        this.boneAnimations = boneAnimations;
    }

    static Animation generateWaitAnimation(double length) {
        return new Animation(WAIT, length, LoopType.PLAY_ONCE, new BoneAnimation[0]);
    }

    public String name() {
        return name;
    }

    public double length() {
        return length;
    }

    public LoopType loopType() {
        return loopType;
    }

    public BoneAnimation[] boneAnimations() {
        return boneAnimations;
    }

    @FunctionalInterface
    public interface LoopType {

        Map<String, LoopType> LOOP_TYPES = new ConcurrentHashMap<>(4);

        LoopType DEFAULT = (animatable, controller, current) -> current.loopType()
            .shouldPlayAgain(animatable, controller, current);
        LoopType PLAY_ONCE = register("play_once", register("false", (animatable, controller, current) -> false));
        LoopType HOLD_ON_LAST_FRAME = register("hold_on_last_frame", (animatable, controller, current) -> {
            controller.animationState = AnimationController.State.PAUSED;
            return true;
        });
        LoopType LOOP = register("loop", register("true", (animatable, controller, current) -> true));

        boolean shouldPlayAgain(GeoAnimatable animatable, AnimationController<?> controller, Animation current);

        static LoopType fromJson(JsonElement json) {
            if (json == null || !json.isJsonPrimitive()) return PLAY_ONCE;
            JsonPrimitive p = json.getAsJsonPrimitive();
            if (p.isBoolean()) return p.getAsBoolean() ? LOOP : PLAY_ONCE;
            if (p.isString()) return LOOP_TYPES.getOrDefault(p.getAsString(), PLAY_ONCE);
            return PLAY_ONCE;
        }

        static LoopType register(String name, LoopType type) {
            LOOP_TYPES.put(name, type);
            return type;
        }
    }
}
