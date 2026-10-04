package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RawAnimation {
    private final List<RawAnimation.Stage> animationList;

    private RawAnimation() {
        this(new ArrayList<>());
    }

    public RawAnimation(List<RawAnimation.Stage> animationList) {
        this.animationList = animationList;
    }

    public static RawAnimation begin() {
        return new RawAnimation();
    }

    public RawAnimation thenPlay(String animationName) {
        return this.then(animationName, Animation.LoopType.DEFAULT);
    }

    public RawAnimation thenLoop(String animationName) {
        return this.then(animationName, Animation.LoopType.LOOP);
    }

    public RawAnimation thenWait(int ticks) {
        this.animationList.add(new RawAnimation.Stage("internal.wait", Animation.LoopType.PLAY_ONCE, ticks));
        return this;
    }

    public RawAnimation thenPlayAndHold(String animation) {
        return this.then(animation, Animation.LoopType.HOLD_ON_LAST_FRAME);
    }

    public RawAnimation thenPlayXTimes(String animationName, int playCount) {
        for (int i = 0; i < playCount; i++) {
            this.then(animationName, i == playCount - 1 ? Animation.LoopType.DEFAULT : Animation.LoopType.PLAY_ONCE);
        }

        return this;
    }

    public RawAnimation then(String animationName, Animation.LoopType loopType) {
        this.animationList.add(new RawAnimation.Stage(animationName, loopType, 0));
        return this;
    }

    public List<RawAnimation.Stage> getAnimationStages() {
        return this.animationList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return !(obj instanceof RawAnimation) ? false : this.animationList.equals(((RawAnimation)obj).animationList);
        }
    }

    @Override
    public int hashCode() {
        return this.animationList.hashCode();
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
            return this.animationName;
        }

        @Override
        public boolean equals(Object obj) {
            return !(obj instanceof RawAnimation.Stage s) ? false : Objects.equals(this.animationName, s.animationName) && this.loopType == s.loopType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.animationName, this.loopType);
        }
    }
}
