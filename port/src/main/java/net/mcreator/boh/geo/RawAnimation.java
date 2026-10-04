package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Builder describing a sequence of animation stages, compared by value like GeckoLib's. */
public final class RawAnimation {

    private final List<Stage> animationList;

    private RawAnimation() {
        this(new ArrayList<>());
    }

    public RawAnimation(List<Stage> animationList) {
        this.animationList = animationList;
    }

    public static RawAnimation begin() {
        return new RawAnimation();
    }

    public RawAnimation thenPlay(String animationName) {
        return then(animationName, Animation.LoopType.DEFAULT);
    }

    public RawAnimation thenLoop(String animationName) {
        return then(animationName, Animation.LoopType.LOOP);
    }

    public RawAnimation thenWait(int ticks) {
        animationList.add(new Stage(Animation.WAIT, Animation.LoopType.PLAY_ONCE, ticks));
        return this;
    }

    public RawAnimation thenPlayAndHold(String animation) {
        return then(animation, Animation.LoopType.HOLD_ON_LAST_FRAME);
    }

    public RawAnimation thenPlayXTimes(String animationName, int playCount) {
        for (int i = 0; i < playCount; i++) {
            then(animationName, i == playCount - 1 ? Animation.LoopType.DEFAULT : Animation.LoopType.PLAY_ONCE);
        }
        return this;
    }

    public RawAnimation then(String animationName, Animation.LoopType loopType) {
        animationList.add(new Stage(animationName, loopType, 0));
        return this;
    }

    public List<Stage> getAnimationStages() {
        return animationList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RawAnimation)) return false;
        return animationList.equals(((RawAnimation) obj).animationList);
    }

    @Override
    public int hashCode() {
        return animationList.hashCode();
    }

    public static final class Stage {

        final String animationName;
        final Animation.LoopType loopType;
        final int additionalTicks;

        Stage(String animationName, Animation.LoopType loopType, int additionalTicks) {
            this.animationName = animationName;
            this.loopType = loopType;
            this.additionalTicks = additionalTicks;
        }

        public String animationName() {
            return animationName;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Stage)) return false;
            Stage s = (Stage) obj;
            return Objects.equals(animationName, s.animationName) && loopType == s.loopType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(animationName, loopType);
        }
    }
}
