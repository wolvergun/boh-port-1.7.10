package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

/** 1.20 GoalSelector over a 1.7.10 {@link EntityAITasks} list. */
public class GoalSelector {

    private final EntityAITasks tasks;

    public GoalSelector(EntityAITasks tasks) {
        this.tasks = tasks;
    }

    public EntityAITasks tasks() {
        return tasks;
    }

    public void addGoal(int priority, EntityAIBase goal) {
        tasks.addTask(priority, goal);
    }

    public void removeGoal(EntityAIBase goal) {
        tasks.removeTask(goal);
    }

    @SuppressWarnings("unchecked")
    public void removeAllGoals(Predicate<EntityAIBase> filter) {
        List<EntityAITasks.EntityAITaskEntry> copy = new ArrayList<>(tasks.taskEntries);
        for (EntityAITasks.EntityAITaskEntry e : copy) if (filter.test(e.action)) tasks.removeTask(e.action);
    }

    public void removeAllGoals() {
        removeAllGoals(g -> true);
    }

    @SuppressWarnings("unchecked")
    public Stream<EntityAIBase> getRunningGoals() {
        List<EntityAIBase> out = new ArrayList<>();
        for (Object o : tasks.executingTaskEntries) out.add(((EntityAITasks.EntityAITaskEntry) o).action);
        return out.stream();
    }

    @SuppressWarnings("unchecked")
    public java.util.Set<EntityAIBase> getAvailableGoals() {
        java.util.Set<EntityAIBase> out = new java.util.LinkedHashSet<>();
        for (Object o : tasks.taskEntries) out.add(((EntityAITasks.EntityAITaskEntry) o).action);
        return out;
    }

    public void tick() {}
}
