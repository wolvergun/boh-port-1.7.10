package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;

public class GoalSelector {
    private final EntityAITasks tasks;

    public GoalSelector(EntityAITasks tasks) {
        this.tasks = tasks;
    }

    public EntityAITasks tasks() {
        return this.tasks;
    }

    public void addGoal(int priority, EntityAIBase goal) {
        this.tasks.addTask(priority, goal);
    }

    public void removeGoal(EntityAIBase goal) {
        this.tasks.removeTask(goal);
    }

    public void removeAllGoals(Predicate<EntityAIBase> filter) {
        for (EntityAITaskEntry e : new ArrayList(this.tasks.taskEntries)) {
            if (filter.test(e.action)) {
                this.tasks.removeTask(e.action);
            }
        }
    }

    public void removeAllGoals() {
        this.removeAllGoals(g -> true);
    }

    public Stream<EntityAIBase> getRunningGoals() {
        List<EntityAIBase> out = new ArrayList<>();

        for (Object o : this.tasks.executingTaskEntries) {
            out.add(((EntityAITaskEntry)o).action);
        }

        return out.stream();
    }

    public Set<EntityAIBase> getAvailableGoals() {
        Set<EntityAIBase> out = new LinkedHashSet<>();

        for (Object o : this.tasks.taskEntries) {
            out.add(((EntityAITaskEntry)o).action);
        }

        return out;
    }

    public void tick() {
    }
}
